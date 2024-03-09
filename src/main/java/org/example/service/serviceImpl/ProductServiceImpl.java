package org.example.service.serviceImpl;

import org.example.model.Product;
import org.example.service.ProductService;
import org.example.utils.table.RenderTable;
import org.example.utils.validation.ValidationInput;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProductServiceImpl implements ProductService {
    public static List<Product> products = new ArrayList<>();
    public static List<Product> updatedProducts = new ArrayList<>();
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "1234";
    private static final String CREATE_SAVED_PRODUCT_TB = """
            CREATE TABLE IF NOT EXISTS saved_product_tb (
                id SERIAL PRIMARY KEY,
                name VARCHAR(35) NOT NULL,
                unit_price DOUBLE PRECISION NOT NULL CHECK (unit_price > 0.0),
                qty INT CHECK (qty > 0),
                imported_date Date
            );
            """;
    public static final String GET_ALL_SAVED_PRODUCTS = "SELECT * FROM saved_product_tb";
    public static final String DELETE_PRODUCT = "DELETE FROM saved_product_tb WHERE id=";
    private static final String SAVED_PRODUCT = "INSERT INTO saved_product_tb VALUES (DEFAULT, ? , ? ,?, NOW() )";
    private static final String UPDATE_PRODUCT = "UPDATE saved_product_tb SET name = ?, unit_price = ?, qty = ?, imported_date = ? where id = ?;";

    @Override
    public void insertUnsavedProduct(Product product) {
        products.add(product);
    }
    @Override
    public List<Product> displayUnsavedProduct() {
        return products;
    }

    @Override
    public List<Product> displayUpdatedProduct() {
        try {
            return updatedProducts;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


//    @Override
//    public List<Product> displayUpdatedProduct() {
//        return updatedProducts;
//    }

    @Override
    public List<Product> savedProduct() {
        List<Product> productList = new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_SAVED_PRODUCT_TB);


            for(Product pro : products) {
                PreparedStatement preparedStatement = connection.prepareStatement(SAVED_PRODUCT);
                preparedStatement.setString(1, pro.getName());
                preparedStatement.setDouble(2, pro.getUnit_price());
                preparedStatement.setInt(3, pro.getQty());
                productList.add(pro);
                preparedStatement.executeUpdate();
            }

            products.clear();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return productList;
    }

    @Override
    public List<Product> displayProduct() {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_SAVED_PRODUCT_TB);
            ResultSet rs = statement.executeQuery(GET_ALL_SAVED_PRODUCTS);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double unit_price = rs.getDouble("unit_price");
                int qty = rs.getInt("qty");
                LocalDate imported_date = rs.getDate("imported_date").toLocalDate();
                products.add(new Product(id, name, unit_price, qty, imported_date));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return products;
    }

    @Override
    public List<Product> saveUpdateProduct() {
        List<Product> savedProducts = new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            for(Product updateProduct : updatedProducts) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT);
                preparedStatement.setString( 1,updateProduct.getName());
                preparedStatement.setDouble(2, updateProduct.getUnit_price());
                preparedStatement.setInt(3, updateProduct.getQty());
                preparedStatement.setDate(4, Date.valueOf(updateProduct.getImported_date()) );
                preparedStatement.setInt(5, updateProduct.getId());
                if(preparedStatement.executeUpdate() == 1) {
                    savedProducts.add(updateProduct);
                }
            }

            updatedProducts.clear();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

        return savedProducts;
    }

    @Override
    public boolean exitProgram() {
        return products.isEmpty() && updatedProducts.isEmpty();
    }

    @Override
    public void insertUpdateProduct(Product product) {
        updatedProducts.add(product);
    }


    @Override
    public void deleteProduct() {
        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);){
            Table t = new Table(RenderTable.header_title.length, BorderStyle.UNICODE_ROUND_BOX,
                    ShownBorders.ALL);
            String deleteId = ValidationInput.validate("=> Enter id to delete : ","Invalid ID","[0-9]+");
            Class.forName("org.postgresql.Driver");
            Statement statement = connection.createStatement();
            String query_db = GET_ALL_SAVED_PRODUCTS + " WHERE id="+Integer.parseInt(deleteId) +";";
            ResultSet resultSet = statement.executeQuery(query_db);
            if (!resultSet.isBeforeFirst()){
                RenderTable.tableRender(new String[]{"Product Not Found"},"","msg",new ArrayList<>(),"","");
                new Scanner(System.in).next();
            }
            else {
                for (int i = 0; i<RenderTable.header_title.length;i++){
                    t.setColumnWidth(i,25,25);
                    t.addCell(RenderTable.header_title[i],RenderTable.ALIGN_CENTER);
                }
                while (resultSet.next()){
                    t.addCell(String.valueOf(resultSet.getInt(1)),RenderTable.ALIGN_CENTER);
                    t.addCell(resultSet.getString(2),RenderTable.ALIGN_CENTER);
                    t.addCell("$ "+resultSet.getDouble(3),RenderTable.ALIGN_CENTER);
                    t.addCell(String.valueOf(resultSet.getInt(4)),RenderTable.ALIGN_CENTER);
                    t.addCell(String.valueOf(resultSet.getDate(5)),RenderTable.ALIGN_CENTER);
                }
                System.out.println(t.render());
                String answer = ValidationInput.validate("=> Do you want to delete ? [y/n] : ","Invalid Choice","[a-zA-Z]+");
                if (answer.equalsIgnoreCase("y")) {
                    statement.executeUpdate(DELETE_PRODUCT + Integer.parseInt(deleteId));
                    RenderTable.tableRender(new String[]{"Product ID "+deleteId+" deleted"},"","msg",new ArrayList<>(),"","");
                    new Scanner(System.in).next();
                }else {
                    RenderTable.tableRender(new String[]{"Cancel Delete"}, "","msg",new ArrayList<>(),"","");
                    new Scanner(System.in).next();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void searchProduct() {
        String product_name = ValidationInput.validate("=> Enter product name : ","Invalid Name","[a-zA-Z]+");
        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);){
            Table t = new Table(RenderTable.header_title.length, BorderStyle.UNICODE_ROUND_BOX,
                    ShownBorders.ALL);
            Class.forName("org.postgresql.Driver");
            String query_db = GET_ALL_SAVED_PRODUCTS + " WHERE name ~* '["+product_name+"]'";
            RenderTable.tableRender(RenderTable.header_title,"Search Product","db",new ArrayList<>(),query_db,"");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setRow() {
        System.out.print("Enter Amount of Row: ");
        RenderTable.page = new Scanner(System.in).nextInt();
        RenderTable.PAGE_SIZE = RenderTable.page;
    }

    @Override
    public void readOneProduct() {
        String viewId = ValidationInput.validate("=>Enter ID to view : ","Invalid ID","[0-9]+");
        RenderTable.tableRender(RenderTable.header_title,"Product Detail","db",new ArrayList<>(),GET_ALL_SAVED_PRODUCTS,viewId);
        new Scanner(System.in).next();
    }

    @Override
    public void backup(){
        String DbName = "postgres", DbPassword = "1234", type;
        File backupFilePath = new File(System.getProperty("user.home") + File.separator + "backup_" + DbName);
        if (!backupFilePath.exists()){
            File dir = backupFilePath;
            dir.mkdir();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        String backupFileName = "backup_" + DbName + "_" + sdf.format(new java.util.Date()) + ".sql";
        List<String> commands = new ArrayList<>();
            commands.add("D:\\Program File\\psql\\16\\bin\\pg_dump.exe");
            commands.add("-h"); //database server host
            commands.add("localhost");
            commands.add("-p"); //database server port number
            commands.add("5432");
            commands.add("-U"); //connect as specified database user
            commands.add("postgres");
            commands.add("-F"); //output file format (custom, directory, tar, plain text (default))
            commands.add("c");
            commands.add("-b"); //include large objects in dump
            commands.add("-v"); //verbose mode
            commands.add("-f"); //output file or directory name
            commands.add(backupFilePath.getAbsolutePath() + File.separator + backupFileName);
            commands.add("-d"); //database name
            commands.add(DbName);
            if (!commands.isEmpty()){
                try {
                    ProcessBuilder pb = new ProcessBuilder(commands);
                    pb.environment().put("PGPASSWORD",DbPassword);

                    Process process = pb.start();

                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))){
                        String line = bufferedReader.readLine();
                        while (line != null){
                            System.err.println(line);
                            line = bufferedReader.readLine();
                        }
                    }

                    process.waitFor();
                    process.destroy();
                    System.out.println("Success " + backupFilePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else
                System.out.println("Error");
    }
}
