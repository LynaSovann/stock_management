package org.example.utils.validation;

import java.util.Scanner;

public class ValidationInput {
    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        String input = scanner.nextLine();
        return input;
    }
}
