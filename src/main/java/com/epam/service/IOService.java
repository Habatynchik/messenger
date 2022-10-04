package com.epam.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOService {

    public InputMessenger inputMessengerFromConsole() {

        String templateValue;
        Map<String, String> templateValues = new HashMap<>();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please input template: ");
            templateValue = scanner.nextLine();

            Pattern pattern = Pattern.compile("#\\{([^}]*)}");
            Matcher matcher = pattern.matcher(templateValue);

            System.out.println("Please input values: ");
            while (matcher.find()) {
                String key = matcher.group(1);
                System.out.print(key + ": ");
                String value = scanner.nextLine();

                templateValues.put(key, value);
            }
        }
        return new InputMessenger(templateValue, templateValues);
    }

  /*  public InputMessenger inputMessengerFromFile() {

        String templateValue;
        Map<String, String> templateValues = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

        }

        return new InputMessenger(templateValue, templateValues);
    }*/
}
