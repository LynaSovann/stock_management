package org.example;

import org.example.model.Product;
import org.example.utils.table.RenderTable;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StockManagement stockManagement = new StockManagement();
        stockManagement.run();
    }
}