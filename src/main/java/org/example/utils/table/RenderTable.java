package org.example.utils.table;

import org.example.model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.postgresql.Driver;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class RenderTable {
    private static final CellStyle ALIGN_CENTER = new CellStyle(CellStyle.HorizontalAlign.CENTER);

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

    public static void tableRender(String[] header_title,List<Product> productList,String useForWhat){
        String option = null;
        int page = 1,n=0;
        do {
            Table t = new Table(header_title.length, BorderStyle.UNICODE_ROUND_BOX,
                    ShownBorders.ALL);
            if (useForWhat.equalsIgnoreCase("msg")) {
                t.setColumnWidth(0, 25, 25);
                t.addCell(header_title[0], ALIGN_CENTER);
                System.out.println(t.render());
                return;
            }

            if (useForWhat.isEmpty()) {
                if (productList.isEmpty()) {
                    t.setColumnWidth(0, 25, 25);
                    t.addCell("No Data", ALIGN_CENTER, 5);
                    System.out.println(t.render());
                    return;
                }
            }

            if (useForWhat.isEmpty()) {
                for (int i = 0; i < header_title.length; i++)
                    t.setColumnWidth(i, 25, 25);
            }


            if (useForWhat.isEmpty())
                t.addCell("Product List", ALIGN_CENTER, 5);

            for (String title : header_title)
                t.addCell(title, ALIGN_CENTER);


            if (!useForWhat.isEmpty()) {
                try {
                    String user_name = "", url = "", password = "";
                    Class.forName("org.postgresql.Driver");
                    Connection connection = DriverManager.getConnection(user_name, url, password);
                    Statement statement = connection.createStatement();
                    String query = "SELECT * FROM \"table_name\"";
                    ResultSet resultSet = statement.executeQuery(query);
                    for (int i = 0; i < header_title.length; i++)
                        t.setColumnWidth(i, 25, 25);
                    while (resultSet.next()) {
                        t.addCell(String.valueOf(resultSet.getInt(1)), ALIGN_CENTER);
                        t.addCell(resultSet.getString(2), ALIGN_CENTER);
                        t.addCell(resultSet.getString(3), ALIGN_CENTER);
                        t.addCell(resultSet.getString(4), ALIGN_CENTER);
                        t.addCell(resultSet.getString(5), ALIGN_CENTER);
                    }

                    System.out.println(t.render());
                    return;

                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (useForWhat.isEmpty())
                productList.stream().distinct().skip(n).limit(3).forEach(product -> {
                    t.addCell(String.valueOf(product.getId()), ALIGN_CENTER);
                    t.addCell(product.getName(), ALIGN_CENTER);
                    t.addCell(String.valueOf(product.getUnit_price()), ALIGN_CENTER);
                    t.addCell(String.valueOf(product.getQty()), ALIGN_CENTER);
                    t.addCell(String.valueOf(product.getImported_date()), ALIGN_CENTER);
                });

            t.addCell("Page : "+ page +"/" + ((productList.size()/3)+1), ALIGN_CENTER, 2);
            t.addCell("Total Records : " + productList.size(), ALIGN_CENTER, 3);
            System.out.println(t.render());
            System.out.println("F) First \t P) Previous \t N) Next \t L) Last \t G) Goto");option=new Scanner(System.in).next();
            switch (option){
                case "F": case "f":
                    n=0;
                    page=1;
                    break;
                case "P": case "p":
                    if(n-3<0){
                        page = 0;
                        n=0;
                    }else n=n-3;
                    break;
                case "N": case "n":
                    n=n+3;
                    page++;
                    break;
                case "L": case "l":
                    n= (productList.size()/3)+1;
                    break;
                case "G": case "g":
                    System.out.print("-> Go to page: ");n=new Scanner(System.in).nextInt();
                    break;
            }
        }while (option != "g" || option != "G");

    }



}
