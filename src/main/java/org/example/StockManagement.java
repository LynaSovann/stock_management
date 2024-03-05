package org.example;

import org.example.utils.table.RenderTable;

import java.util.Scanner;

public class StockManagement {
    public void run() {

        String choice;

        do {
            RenderTable.renderMenu();
            System.out.print("=> Choose an option: ");
            choice = new Scanner(System.in).nextLine();
            switch (choice.toLowerCase()) {
                case "*":
                    System.out.println("Display data");
                    break;
                case "w":
                    System.out.println("Write data");
                    break;
                case "r":
                    System.out.println("read data");
                    break;
                case "u":
                    System.out.println("update data");
                    break;
                case "d":
                    System.out.println("delete data");
                    break;
            }
        } while(!choice.equalsIgnoreCase("e"));

    }
}
