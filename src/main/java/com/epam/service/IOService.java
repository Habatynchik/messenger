package com.epam.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public InputMessenger inputMessengerFromFile(String path) {

        String templateValue;
        Map<String, String> templateValues = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {

            templateValue = reader.readLine();

            Pattern pattern = Pattern.compile("#\\{([^}]*)}");
            Matcher matcher = pattern.matcher(templateValue);

            while (matcher.find()) {
                String key = matcher.group(1);
                String value = reader.readLine();

                templateValues.put(key, value);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new InputMessenger(templateValue, templateValues);
    }

    public boolean outputMessengerFromFile(String message, String path) {

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(path))) {
            bufferedWriter.write(message);
        } catch (IOException e) {
            return false;
        }
        return true;
    }


}
