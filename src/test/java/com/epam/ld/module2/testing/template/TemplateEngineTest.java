package com.epam.ld.module2.testing.template;

import com.epam.input.InputMessenger;
import com.epam.ld.module2.testing.UnitTests;
import com.epam.service.Client;
import com.epam.service.template.Template;
import com.epam.service.template.TemplateEngine;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTests
public class TemplateEngineTest {
    private final TemplateEngine templateEngine = new TemplateEngine();

    @Test
    void testSuccessGenerateMessageWithValidTemplateAndValues() {
        String templateValue = "I want to see #{name} on #{day}";
        Map<String, String> valuesForTemplate = new HashMap<String, String>() {{
            put("name", "Nikita");
            put("day", "Saturday");
        }};
        Template template = new Template(templateValue);
        Client client = new Client(valuesForTemplate);
        assertEquals("I want to see Nikita on Saturday", templateEngine.generateMessage(template, client));
    }

    @Test
    void generateMessage_ShouldThrowIllegalArgumentException() {
        String templateValue = "Hello my dear #{one}";
        Map<String, String> valuesForTemplate = new HashMap<>();
        Template template = new Template(templateValue);
        Client client = new Client(valuesForTemplate);

        assertThrows(IllegalArgumentException.class, () -> templateEngine.generateMessage(template, client));
    }

    @ParameterizedTest(name = "generateMessage_ShouldReturnBlankStrings {index}: {0}")
    @ValueSource(strings = {"", " ", "  "})
    void generateMessage_ShouldReturnBlankStrings(String templateValue) {
        Map<String, String> valuesForTemplate = new HashMap<>();
        Template template = new Template(templateValue);
        Client client = new Client(valuesForTemplate);

        assertEquals("", templateEngine.generateMessage(template, client));
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestOneAndTwoPlaceholders() {

        List<InputMessenger> inputMessengerList = new ArrayList<InputMessenger>() {{
            add(new InputMessenger(
                    "Hello my dear #{name}",
                    new HashMap<String, String>() {{
                        put("name", "Ivan");
                    }}
            ));
            add(new InputMessenger(
                    "#{one} must give me #{two}",
                    new HashMap<String, String>() {{
                        put("one", "You");
                        put("two", "fork");
                    }}
            ));
        }};

        List<String> messages = new ArrayList<String>() {{
            add("Hello my dear Ivan");
            add("You must give me fork");
        }};

        return IntStream.range(0, inputMessengerList.size())
                .mapToObj(i -> generateDynamicTest(i, inputMessengerList, messages));
    }

    private DynamicTest generateDynamicTest(int index, List<InputMessenger> inputMessengerList, List<String> messages) {
        Template template = new Template(inputMessengerList.get(index).getTemplateValue());
        Client client = new Client(inputMessengerList.get(index).getTemplateValues());

        return DynamicTest.dynamicTest("Dynamic test with " + template.getTemplateValue(),
                () -> assertEquals(messages.get(index), templateEngine.generateMessage(template, client)));
    }

}
