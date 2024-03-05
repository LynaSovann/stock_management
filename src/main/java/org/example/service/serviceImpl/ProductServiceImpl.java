package org.example.service.serviceImpl;

import org.example.model.Product;
import org.example.service.ProductService;

import java.sql.*;

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
            )
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
    public static void getAllProducts() {
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

                Product product = new Product(id, name, unit_price, qty, imported_date);
                System.out.println(product);
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
    }



}
