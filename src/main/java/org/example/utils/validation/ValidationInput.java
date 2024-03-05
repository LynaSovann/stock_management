package org.example.utils.validation;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationInput {
    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        String input = scanner.nextLine();
        return input;
    }

    public String validate(String prompt, String errorMsg, String regex){
        String input;
        Pattern pattern = Pattern.compile(regex);
        do {
            System.out.print(prompt);
            input = new Scanner(System.in).nextLine();
            Matcher matcher = pattern.matcher(input);
            if (!matcher.matches()) {
                System.out.println(errorMsg);
            }
        } while (!input.matches(regex));
        return input;
    }
}
