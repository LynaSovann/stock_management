package org.example.service.serviceImpl;

import org.example.model.Product;
import org.example.service.ProductService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    List<Product> products = new ArrayList<>();
    private static final String URL = "jdbc:postgresql://localhost:5432/min_pro_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123";
    private static final String CREATE_SAVED_PRODUCT_TB = """
            CREATE TABLE IF NOT EXISTS saved_product_tb (
                id SERIAL PRIMARY KEY,
                name VARCHAR(35) NOT NULL,
                unit_price DOUBLE PRECISION NOT NULL CHECK (unit_price > 0.0),
                qty INT CHECK (qty > 0),
                imported_date TIMESTAMP
            );
            """;
    private static final String GET_ALL_SAVED_PRODUCTS = "SELECT * FROM saved_product_tb;";
    private static final String SAVED_PRODUCT = "INSERT INTO saved_product_tb VALUES (?, ? , ? ,?, ? )";

    @Override
    public void insertUnsavedProduct(Product product) {
        products.add(product);
    }
    @Override
    public List<Product> displayUnsavedProduct() {
        return products;
    }

    @Override
    public void savedProduct() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_SAVED_PRODUCT_TB);

            for(Product pro : products) {
                PreparedStatement preparedStatement = connection.prepareStatement(SAVED_PRODUCT);
                preparedStatement.setInt(1, pro.getId());
                preparedStatement.setString(2, pro.getName());
                preparedStatement.setDouble(3, pro.getUnit_price());
                preparedStatement.setInt(4, pro.getQty());
                preparedStatement.setDate(5, java.sql.Date.valueOf(pro.getImported_date()));
                if(preparedStatement.executeUpdate() == 1) {
                    System.out.println("* New product: "+  pro.getName() + " was inserted successfully *");
                } else {
                    System.out.println("Failed to save product: " + pro.getName() + " to stock");
                }

            }


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
    }


//    @Override
//    public List<Product> getAllUnSavedProducts() {
//        List<Product> products = new ArrayList<>();
//        Connection connection = null;
//        try {
//            Class.forName("org.postgresql.Driver");
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(CREATE_UNSAVED_PRODUCT_TB);
//            ResultSet rs = statement.executeQuery(GEL_ALL_UNSAVED_PRODUCTS);
//            while(rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                double unit_price = rs.getDouble("unit_price");
//                int qty = rs.getInt("qty");
//                Timestamp imported_date = rs.getTimestamp("imported_date");
//                products.add(new Product(id, name, unit_price, qty, imported_date));
//            }
//
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                assert connection != null;
//                connection.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        return products;
//    }

//    @Override
//    public void insertSavedProduct(Product product) {
//        Connection connection = null;
//        try {
//            Class.forName("org.postgresql.Driver");
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Statement statement = connection.createStatement();
////            statement.executeUpdate(CREATE_UNSAVED_PRODUCT_TB);
//            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ONE_UNSAVED_PRODUCT);
//            preparedStatement.setString(1, product.getName());
//            preparedStatement.setDouble(2, product.getUnit_price());
//            preparedStatement.setInt(3, product.getQty());
//            if(preparedStatement.executeUpdate() == 1) {
//                System.out.println("added product " +  product.getName() + " to stock successfully");
//            } else {
//                System.out.println("Failed to add product to stock");
//            }
//
//        } catch(SQLException | ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                assert connection != null;
//                connection.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//    }

    @Override
    public void updateProduct(int id) {

    }

    @Override
    public void deleteProduct(int id) {

    }



}
