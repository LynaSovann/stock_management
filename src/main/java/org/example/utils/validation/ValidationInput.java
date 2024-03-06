package org.example.utils.validation;

import org.example.model.Product;
import org.example.utils.table.RenderTable;

import java.util.ArrayList;
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
//                System.out.println(errorMsg);
                RenderTable.tableRender(new String[]{errorMsg},"",new ArrayList<Product>(),"msg","");
            }
        } while (!input.matches(regex));
        return input;
    }
}
