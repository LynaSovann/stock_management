package org.example.service.serviceImpl;

import org.example.model.Product;
import org.example.service.ProductService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    public static List<Product> products = new ArrayList<>();
    public static List<Product> updatedProducts = new ArrayList<>();
    private static final String URL = "jdbc:postgresql://localhost:5432/min_pro_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123";
    private static final String CREATE_SAVED_PRODUCT_TB = """
            CREATE TABLE IF NOT EXISTS saved_product_tb (
                id SERIAL PRIMARY KEY,
                name VARCHAR(35) NOT NULL,
                unit_price DOUBLE PRECISION NOT NULL CHECK (unit_price > 0.0),
                qty INT CHECK (qty > 0),
                imported_date Date
            );
            """;
    private static final String GET_ALL_SAVED_PRODUCTS = "SELECT * FROM saved_product_tb;";
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
                preparedStatement.setDate(4, java.sql.Date.valueOf(updateProduct.getImported_date()) );
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
        System.out.println("Enter id to delete");
    }

    @Override
    public void searchProduct() {
        System.out.println("This is search method!");
    }

    @Override
    public void setRow() {
        System.out.println("This is setRow method");
    }

    @Override
    public void readOneProduct() {
        System.out.println("Do read one product case here");
    }
}
