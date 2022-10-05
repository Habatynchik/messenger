package com.epam.service;

import com.epam.input.IOService;
import com.epam.input.InputMessenger;
import com.epam.service.template.Template;
import com.epam.service.template.TemplateEngine;

public class MessengerService {

    private final IOService ioService;
    private final MailServer mailServer;
    private final TemplateEngine templateEngine;

    public MessengerService(IOService ioService, MailServer mailServer, TemplateEngine templateEngine) {
        this.ioService = ioService;
        this.mailServer = mailServer;
        this.templateEngine = templateEngine;
    }

    public String sendMessageInConsoleMode() {
        InputMessenger inputMessenger = ioService.inputMessengerFromConsole();
        Template template = new Template(inputMessenger.getTemplateValue());
        Client client = new Client(inputMessenger.getTemplateValues());

        String message = templateEngine.generateMessage(template, client);

        System.out.println("Your message:\n" + message);
        Messenger messenger = new Messenger(mailServer, templateEngine);
        messenger.sendMessage(client, template);

        return message;
    }

    public String sendMessageInFileMode(String inputPath, String outputPath) {
        InputMessenger inputMessenger = ioService.inputMessengerFromFile(inputPath);
        Template template = new Template(inputMessenger.getTemplateValue());
        Client client = new Client(inputMessenger.getTemplateValues());

        String message = templateEngine.generateMessage(template, client);

        ioService.outputMessengerFromFile(message, outputPath);
        Messenger messenger = new Messenger(mailServer, templateEngine);
        messenger.sendMessage(client, template);

        return message;
    }
}
