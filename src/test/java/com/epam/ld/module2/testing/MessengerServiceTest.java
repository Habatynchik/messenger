package com.epam.ld.module2.testing;

import com.epam.input.IOService;
import com.epam.input.InputMessenger;
import com.epam.service.MailServer;
import com.epam.service.MessengerService;
import com.epam.service.template.TemplateEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@UnitTests
public class MessengerServiceTest {

    private final Logger log = LogManager.getLogger(MessengerServiceTest.class);
    private MailServer mailServer;

    private IOService messengerIOService ;
    private MessengerService messengerService;
    private InputMessenger inputMessenger;

    @BeforeEach
    public void setUp() {
        mailServer = new MailServer();
        messengerIOService = spy(new IOService());
        messengerService = new MessengerService(messengerIOService, mailServer, new TemplateEngine());

        inputMessenger = new InputMessenger(
                "I want to see #{name} on #{day}",
                new HashMap<String, String>() {{
                    put("name", "Nikita");
                    put("day", "Saturday");
                }}
        );
    }

    @Test
    void testCorrectMessageOutputConsole() {

        doReturn(inputMessenger).when(messengerIOService).inputMessengerFromConsole();

        String message = messengerService.sendMessageInConsoleMode();

        assertEquals("I want to see Nikita on Saturday", message);
    }

    @Test
    void testCorrectMessageOutputFile(@TempDir Path tempDir) throws IOException {

        doReturn(inputMessenger).when(messengerIOService).inputMessengerFromFile(any(Path.class));

        Path outputFile = Files.createFile(tempDir.resolve("output.txt"));

        String contentMessage = messengerService.sendMessageInFileMode(new File("input.txt").toPath(), outputFile);

        assertLinesMatch(Collections.singletonList(contentMessage), Files.readAllLines(outputFile));

    }

}
