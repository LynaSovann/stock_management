package org.example.utils.table;

import org.example.model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.postgresql.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static void tableRender(String[] header_title,String main_title,List<Product> productList,String useForWhat,String id){
        Table t = new Table(header_title.length, BorderStyle.UNICODE_ROUND_BOX,
                ShownBorders.ALL);
        if (useForWhat.equalsIgnoreCase("msg")){
            t.setColumnWidth(0,25,25);
            t.addCell(header_title[0],ALIGN_CENTER);
            System.out.println(t.render());
            return;
        }

        if (useForWhat.isEmpty()){
            if (productList.isEmpty()){
                t.setColumnWidth(0,25,25);
                t.addCell("No Data",ALIGN_CENTER,5);
                System.out.println(t.render());
                return;
            }
        }

        if (useForWhat.isEmpty()){
            for (int i = 0;i<header_title.length;i++)
                t.setColumnWidth(i, 25, 25);
        }


        if (!main_title.isEmpty())
            t.addCell(main_title.toUpperCase(), ALIGN_CENTER,5);

        for(String title : header_title)
            t.addCell(title, ALIGN_CENTER);

        if (!useForWhat.isEmpty()){
            try{
                String user_name = "",url="",password="";
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(user_name,url,password);
                Statement statement = connection.createStatement();
//                String query = "SELECT * FROM \"table_name\"";
                String query = getQueryWithID(id);
                ResultSet resultSet = statement.executeQuery(query);
                int rows = statement.executeUpdate(query);
                if (rows==0){
                    tableRender(new String[]{"No Data"},"",new ArrayList<>(),"","");
                }
                for (int i = 0;i<header_title.length;i++)
                    t.setColumnWidth(i, 25, 25);
                while (resultSet.next()){
                    t.addCell(String.valueOf(resultSet.getInt(1)),ALIGN_CENTER);
                    t.addCell(resultSet.getString(2),ALIGN_CENTER);
                    t.addCell(resultSet.getString(3),ALIGN_CENTER);
                    t.addCell(resultSet.getString(4),ALIGN_CENTER);
                    t.addCell(resultSet.getString(5),ALIGN_CENTER);
                }

                System.out.println(t.render());
                return;

            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (useForWhat.isEmpty())
            productList.forEach(product -> {
                t.addCell(String.valueOf(product.getId()), ALIGN_CENTER);
                t.addCell(product.getName(),ALIGN_CENTER);
                t.addCell(String.valueOf(product.getUnit_price()),ALIGN_CENTER);
                t.addCell(String.valueOf(product.getQty()),ALIGN_CENTER);
                t.addCell(String.valueOf(product.getImported_date()),ALIGN_CENTER);
            });

        t.addCell("Page : 1 / 3",ALIGN_CENTER,2);
        t.addCell("Total Records : " + productList.size(),ALIGN_CENTER,3);

        System.out.println(t.render());

    }

    // might be updated in the future with search by name .....
    public static String getQueryWithID(String id){
        return "SELECT * FROM \"TABLE_NAME\" " + (!id.isEmpty() ? "WHERE id="+Integer.parseInt(id) : "" );
    }


}
