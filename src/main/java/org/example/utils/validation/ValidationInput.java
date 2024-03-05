package org.example.utils.validation;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidationInput {
    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        String input = scanner.nextLine();
        return input;
    }

    public static boolean isValidate(String someString,String regex){
        return Pattern.matches(regex,someString);
    }
}
