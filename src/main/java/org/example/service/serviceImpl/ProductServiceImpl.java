package org.example.service.serviceImpl;

import org.example.model.Product;
import org.example.service.ProductService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private static final String URL = "jdbc:postgresql://localhost:5432/min_pro_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123";
    private static final String CREATE_UNSAVED_PRODUCT_TB = """
            CREATE TABLE IF NOT EXISTS unsaved_product_tb (
                id SERIAL PRIMARY KEY,
                name VARCHAR(35) NOT NULL,
                unit_price DOUBLE PRECISION NOT NULL CHECK (unit_price > 0.0),
                qty INT CHECK (qty > 0),
                imported_date TIMESTAMP
            );
            """;
    private static final String CREATE_SAVED_PRODUCT_TB = """
            CREATE TABLE IF NOT EXISTS saved_product_tb (
                id SERIAL PRIMARY KEY,
                name VARCHAR(35) NOT NULL,
                unit_price DOUBLE PRECISION NOT NULL CHECK (unit_price > 0.0),
                qty INT CHECK (qty > 0),
                imported_date TIMESTAMP
            )
            """;

    private static final String GEL_ALL_UNSAVED_PRODUCTS = "SELECT * FROM unsaved_product_tb;";
    private static final String GET_ALL_SAVED_PRODUCTS = "SELECT * FROM saved_product_tb;";
    private static final String INSERT_ONE_UNSAVED_PRODUCT = "INSERT INTO unsaved_product_tb VALUES (default, ? , ? , ? , current_timestamp)";
    public List<Product> getAllUnSavedProducts() {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_UNSAVED_PRODUCT_TB);
            ResultSet rs = statement.executeQuery(GEL_ALL_UNSAVED_PRODUCTS);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double unit_price = rs.getDouble("unit_price");
                int qty = rs.getInt("qty");
                Timestamp imported_date = rs.getTimestamp("imported_date");
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

    public void insertUnsavedProduct(Product product) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_UNSAVED_PRODUCT_TB);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ONE_UNSAVED_PRODUCT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getUnit_price());
            preparedStatement.setInt(3, product.getQty());
//            return preparedStatement.executeUpdate();
            if(preparedStatement.executeUpdate() == 1) {
                System.out.println("added product " +  product.getName() + " to stock successfully");
            } else {
                System.out.println("Failed to add product to stock");
            }

        } catch(SQLException | ClassNotFoundException e) {
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

    public void updateProduct(int id) {

    }

    public void deleteProduct(int id) {

    }
}
