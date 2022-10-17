package com.epam;

import com.epam.input.IOService;
import com.epam.service.MailServer;
import com.epam.service.MessengerService;
import com.epam.service.exceptions.MissingValuesForPlaceholderException;
import com.epam.service.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

//C:\Users\habat\IdeaProjects\messenger\src\main\resources\input.txt
//C:\Users\habat\IdeaProjects\messenger\src\main\resources\output.txt
@Slf4j
public class Application {

    public static void main(String[] args) throws MissingValuesForPlaceholderException {
        MailServer mailServer = new MailServer();
        TemplateEngine templateEngine = new TemplateEngine();
        IOService ioService = new IOService();
        MessengerService messengerService = new MessengerService(ioService, mailServer, templateEngine);

        try (Scanner scanner = new Scanner(System.in)) {
            if (args.length == 0) {
                messengerService.sendMessageInConsoleMode();
            } else if (args.length == 2) {
                log.info("Input File path: ");
                Path inputPath = new File(args[0]).toPath();
                log.info("Output File path: ");
                Path outputPath = new File(args[1]).toPath();
                messengerService.sendMessageInFileMode(inputPath, outputPath);
            } else {
                throw new IllegalArgumentException("Wrong application arguments");
            }
        } catch (MissingValuesForPlaceholderException e) {
            throw new MissingValuesForPlaceholderException();
        }
    }
}
