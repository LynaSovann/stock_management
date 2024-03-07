package org.example;

import org.example.controller.ProductController;
import org.example.utils.table.RenderTable;
import org.example.utils.validation.ValidationInput;

import java.util.Scanner;

public class StockManagement {
    public void run() {

        ProductController productController = new ProductController();
        String choice;
        do {
            System.out.println("\n\n");
            System.out.println("""
                             ███████╗████████╗ ██████╗  ██████╗██╗  ██╗    ███╗   ███╗ ██████╗ ███████╗ \s
                             ██╔════╝╚══██╔══╝██╔═══██╗██╔════╝██║ ██╔╝    ████╗ ████║██╔════╝ ██╔════╝ \s
                             ███████╗   ██║   ██║   ██║██║     █████╔╝     ██╔████╔██║██║  ███╗███████╗ \s
                             ╚════██║   ██║   ██║   ██║██║     ██╔═██╗     ██║╚██╔╝██║██║   ██║╚════██║ \s
                             ███████║   ██║   ╚██████╔╝╚██████╗██║  ██╗    ██║ ╚═╝ ██║╚██████╔╝███████║ \s
                             ╚══════╝   ╚═╝    ╚═════╝  ╚═════╝╚═╝  ╚═╝    ╚═╝     ╚═╝ ╚═════╝ ╚══════╝ \s
                             """);
            RenderTable.renderMenu();
            choice = ValidationInput.validate("=> Choose an option: ", "Number is not allowed!", "^[a-zA-Z\\*]+");
            switch (choice.toLowerCase()) {
                case "*":
                    productController.displayProduct();
                    break;
                case "w":
                    productController.insertUnsavedProduct();
                    break;
                case "r":
                    productController.readOneProduct();
                    break;
                case "u":
                    productController.insertUpdateProduct();
                    break;
                case "d":
                    productController.deleteProduct();
                    break;
                case "s":
                    productController.searchProduct();
                    break;
                case "se":
                    productController.setRow();
                    break;
                case "sa":
                    productController.savedProduct();
                    break;
                case "un":
                    productController.displayUnsavedProduct();
                    break;
                case "ba":
                    break;
                case "re":
                    break;
                case "e":
                    productController.exitProgram();
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        } while(!choice.equalsIgnoreCase("e"));
    }
}
