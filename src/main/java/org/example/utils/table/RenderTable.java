package org.example.utils.table;

import org.example.model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.postgresql.Driver;

import java.sql.*;
import java.util.List;

public class RenderTable {
    private static final CellStyle ALIGN_CENTER = new CellStyle(CellStyle.HorizontalAlign.CENTER);

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

    public static void tableRender(int numberOfCols,List<Product> productList,String useForWhat){
        int countRows = 0;
        Table t = new Table(numberOfCols, BorderStyle.UNICODE_ROUND_BOX,
                ShownBorders.SURROUND_HEADER_FOOTER_AND_COLUMNS);
        List<String> header_title = List.of(
                "ID","Name","Unit Price","QTY","Imported Date"
        );
        if (useForWhat.isEmpty()){
            t.setColumnWidth(countRows++, 8, 14);
            t.addCell("Product List", ALIGN_CENTER,4);
        }

        if (!useForWhat.isEmpty()){
            try{
                String user_name = "",url="",password="";
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(user_name,url,password);
                Statement statement = connection.createStatement();
                String query = "SELECT * FROM \"table_name\"";
                ResultSet resultSet = statement.executeQuery(query);
                t.setColumnWidth(countRows++, 8, 14);
                header_title.forEach(header->{
                    t.addCell(header, ALIGN_CENTER);
                });
                while (resultSet.next()){
                    t.setColumnWidth(countRows++, 8, 14);
                    t.addCell(String.valueOf(resultSet.getInt(1)), ALIGN_CENTER);
                    t.addCell(resultSet.getString(2), ALIGN_CENTER);
                    t.addCell(resultSet.getString(3), ALIGN_CENTER);
                    t.addCell(resultSet.getString(4), ALIGN_CENTER);
                    t.addCell(resultSet.getString(5), ALIGN_CENTER);
                }

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(t.render());

    }



}
