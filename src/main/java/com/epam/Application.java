package com.epam;

import com.epam.input.IOService;
import com.epam.service.MailServer;
import com.epam.service.MessengerService;
import com.epam.service.template.TemplateEngine;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        MailServer mailServer = new MailServer();
        TemplateEngine templateEngine = new TemplateEngine();
        IOService ioService = new IOService();
        MessengerService messengerService = new MessengerService(ioService, mailServer, templateEngine);

        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Choose your mode(Console/File): ");

            if (args.length == 0){
                messengerService.sendMessageInConsoleMode();
            } else if (args.length == 2) {
                System.out.println("Input File path: ");
                String inputPath = scanner.next();
                System.out.println("Output File path: ");
                String outputPath = scanner.next();
                messengerService.sendMessageInFileMode(inputPath, outputPath);
            }
            {





            }
        }
    }
}
