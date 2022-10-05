package com.epam.ld.module2.testing.template;

import com.epam.service.Client;
import com.epam.service.template.Template;
import com.epam.service.template.TemplateEngine;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("UnitTest")
public class TemplateEngineTest {
    private final TemplateEngine templateEngine = new TemplateEngine();

    @Test
    void testSuccessGenerateMessageWithValidTemplateAndValues(){
        String templateValue = "I want to see #{name} on #{day}";
        Map<String, String> valuesForTemplate = new HashMap<String, String>(){{
            put("name", "Nikita");
            put("day", "Saturday");
        }};
        Template template = new Template(templateValue);
        Client client = new Client(valuesForTemplate);
        assertEquals("I want to see Nikita on Saturday", templateEngine.generateMessage(template, client));
    }





}
