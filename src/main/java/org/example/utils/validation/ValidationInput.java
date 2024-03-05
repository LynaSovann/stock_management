package org.example.utils.validation;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationInput {

    public static String validate(String prompt, String errorMsg, String regex){
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
