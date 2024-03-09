package org.example.utils.table;

import org.example.model.Product;
import org.example.service.serviceImpl.ProductServiceImpl;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RenderTable {

    public static int PAGE_SIZE = 5;

    public static final CellStyle ALIGN_CENTER = new CellStyle(CellStyle.HorizontalAlign.CENTER);

    public static String[] header_title = {"ID","Name","Unit Price","QTY","Imported Date"};
    public static void renderMenu() {
        Table table = new Table(3, BorderStyle.UNICODE_ROUND_BOX);
        List<String> menus = List.of(
                "  *) Display", "  W) Write", "  R) Read",
                "  U) Update", "  D) Delete", "  S) Search",
                "  Se) Set Row", "  Sa) Save", "  Un) Unsaved",
                "  Ba) Backup", "  Re) Restore", "  E) Exit"
        );
        int[] colWd = new int[]{20, 20, 20};

        for(int i = 0; i<3; i++) table.setColumnWidth(i, colWd[i], colWd[i]);

        table.addCell("Menus", ALIGN_CENTER, 3);
        menus.forEach(table::addCell);
        System.out.println(table.render());

    }
    public static int page = 5 ;

    public static void tableRender(String[] header_col,String main_title,String userFor,List<Product> products,String query,String id) {
        String option = null;
        int n = 0;
        int pageNumber = 1;

        do {
            int offset = (pageNumber - 1) * PAGE_SIZE;

            Table t = new Table(header_col.length, BorderStyle.UNICODE_ROUND_BOX,
                    ShownBorders.ALL);
            if (!products.isEmpty() || userFor.equalsIgnoreCase("db") || userFor.equalsIgnoreCase("product")) {
                t.addCell(main_title, ALIGN_CENTER, header_title.length);
            }
            if (userFor.equalsIgnoreCase("msg")) {
                t.setColumnWidth(0, 25, 25);
                t.addCell(header_col[0], ALIGN_CENTER);
                System.out.println(t.render());
                return;
            }

            if (userFor.equalsIgnoreCase("product")) {
                if (products.isEmpty()) {
                    t.setColumnWidth(0, 25, 25);
                    t.addCell("No Product Data", ALIGN_CENTER, 5);
                    System.out.println(t.render());
                    return;
                } else {
                    for (int i = 0; i < header_col.length; i++) {
                        t.setColumnWidth(i, 25, 25);
                        t.addCell(header_col[i], ALIGN_CENTER);
                    }

                    products.forEach(product -> {
                        t.addCell(String.valueOf(product.getId()), ALIGN_CENTER);
                        t.addCell(product.getName(), ALIGN_CENTER);
                        t.addCell("$ " + product.getUnit_price(), ALIGN_CENTER);
                        t.addCell(String.valueOf(product.getQty()), ALIGN_CENTER);
                        t.addCell(String.valueOf(product.getImported_date()), ALIGN_CENTER);
                    });
                }

            }

            if (userFor.equalsIgnoreCase("db")) {
                try (Connection connection = DriverManager.getConnection(ProductServiceImpl.URL, ProductServiceImpl.USERNAME, ProductServiceImpl.PASSWORD)) {
                    int count = 0;
                    Class.forName("org.postgresql.Driver");
                    Statement statement = connection.createStatement();
                    String query_db = query + (!id.isEmpty() ? " WHERE id=" + Integer.parseInt(id) : "") + " LIMIT "+ PAGE_SIZE + " OFFSET " + offset;
                    ResultSet resultSet = statement.executeQuery(query_db);
                    if (resultSet.isBeforeFirst())
                        for (int i = 0; i < header_col.length; i++) {
                            t.setColumnWidth(i, 25, 25);
                            t.addCell(header_col[i], ALIGN_CENTER);

                        }
                    while (resultSet.next()) {
                        count++;
                        t.addCell(String.valueOf(resultSet.getInt(1)), ALIGN_CENTER);
                        t.addCell(resultSet.getString(2), ALIGN_CENTER);
                        t.addCell(String.valueOf(resultSet.getDouble(3)), ALIGN_CENTER);
                        t.addCell(String.valueOf(resultSet.getInt(4)), ALIGN_CENTER);
                        t.addCell(String.valueOf(resultSet.getDate(5).toLocalDate()), ALIGN_CENTER);
                    }

                    if (count == 0) {
                        tableRender(new String[]{"No product in database"}, "", "msg", new ArrayList<>(), "", "");
                        return;
                    }
//                    if (count==1){
//                        System.out.println(t.render());
//                        return;
//                    }
//                    t.addCell("Page : " + ((n / page) + 1) + "/" + ((products.size() / page)), ALIGN_CENTER, 2);
//                    t.addCell("Page : " + pageNumber + " / " + ((products.size() / page)), ALIGN_CENTER, 2);
                    t.addCell("Page : " + ((n / page) + 1) + "/" + ((products.size() / page)), ALIGN_CENTER, 2);
                    t.addCell("Total Records : " + count, ALIGN_CENTER, 3);
//                    t.addCell("Total Records : " + count, ALIGN_CENTER, 3);
                    System.out.println(t.render());
//                    return;
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }

            if (userFor.isEmpty()) {
                for (String item : header_col) {
                    t.addCell(item, ALIGN_CENTER);
                }
                System.out.println(t.render());
                return;
            }

            if (!products.isEmpty() && userFor.equalsIgnoreCase("product")) {
                t.addCell("Page : " + ((n / page) + 1) + "/" + ((products.size() / page)), ALIGN_CENTER, 2);
                t.addCell("Total Records : " + products.size(), ALIGN_CENTER, 3);
                System.out.println(t.render());
            }
            if (userFor.equalsIgnoreCase("show"))
                return;
            System.out.println("F) First \t P) Previous \t N) Next \t L) Last \t G) Goto \t B) Back");
            System.out.print("Choose: ");
            option = new Scanner(System.in).next();
            switch (option) {
                case "F":
                case "f":
                    n = 0;
                    break;
                case "P":
                case "p":
                    if (pageNumber > 1) {
                        pageNumber--;
                    }
                    if (n - page < 0) {
                        n = 0;
                    } else n = n - page;
                    break;
                case "N":
                case "n":
                    pageNumber++;
                    n = n + page;
                    if (n + page > products.size()) n = 0;
                    break;
                case "L":
                case "l":
                    n = (products.size() / page) + 1;
                    break;
                case "G":
                case "g":
                    System.out.print("-> Go to page: ");
                    int go = new Scanner(System.in).nextInt();
                    go--;
                    n = (page * go);
                    if (page * go >= products.size()) n = 0;
//                    if (go == 1) go--;
//                    else {
//                        go=n;
//                        go+=page;
//                    }
//                    n= (productList.size() - (productList.size()-go));
                    break;
                case "B":
                case "b":
                    break;
//            }
            }
        } while (!option.equalsIgnoreCase("b")) ;


    }
}
