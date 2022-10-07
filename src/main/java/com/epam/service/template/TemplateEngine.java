package com.epam.service.template;

import com.epam.service.Client;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Template engine.
 */
public class TemplateEngine {
    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {

        String templateMessage = template.getTemplateValue().trim();
        Map<String, String> templateValues = client.getTemplateValues();

        Pattern pattern = Pattern.compile("#\\{([^}]*)}");
        Matcher matcher = pattern.matcher(templateMessage);

        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String patternValues = matcher.group(1);
            String realValue = templateValues.get(patternValues);

            if (realValue == null) {
                throw new IllegalArgumentException("Value for placeholder is missing, placeholder = " + patternValues);
            }

            matcher.appendReplacement(result, realValue);
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
