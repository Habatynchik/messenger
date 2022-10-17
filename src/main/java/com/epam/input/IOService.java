package com.epam.input;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class IOService {
    public InputMessenger inputMessengerFromConsole() {
        String templateValue;
        Map<String, String> templateValues = new HashMap<>();
        try (Scanner scanner = new Scanner(System.in)) {

            log.info("Please input template: ");
            templateValue = scanner.nextLine();

            Pattern pattern = Pattern.compile("#\\{([^}]*)}");
            Matcher matcher = pattern.matcher(templateValue);

            log.info("Please input values: ");
            while (matcher.find()) {
                String key = matcher.group(1);
                log.info("{}: ", key);
                String value = scanner.nextLine();

                templateValues.put(key, value);
            }
        }
        return new InputMessenger(templateValue, templateValues);
    }

    public InputMessenger inputMessengerFromFile(Path path) {
        String templateValue;
        Map<String, String> templateValues = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
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

    public void outputMessengerFromFile(String message, Path path) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(message);
        } catch (IOException e) {
            log.error("IOException: %s%n", e);
        }
    }
}
