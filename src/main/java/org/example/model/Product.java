package org.example.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Product {
    private int id;
    private String name;
    private double unit_price;
    private int qty;
    private LocalDate imported_date;
    private static int count = 0;

    public static int getCount() {
        return count+1;
    }

    public Product(int id, String name, double unit_price, int qty, LocalDate imported_date) {
        this.id = id;
        this.name = name;
        this.unit_price = unit_price;
        this.qty = qty;
        this.imported_date = imported_date;
    }

    public Product(String name, double unit_price, int qty) {
        this.id = ++count;
        this.name = name;
        this.unit_price = unit_price;
        this.qty = qty;
        this.imported_date = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public LocalDate getImported_date() {
        return imported_date;
    }

    public void setImported_date(LocalDate imported_date) {
        this.imported_date = imported_date;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit_price=" + unit_price +
                ", qty=" + qty +
                ", imported_date=" + imported_date +
                '}' + "\n";
    }
}
