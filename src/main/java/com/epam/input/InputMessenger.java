package com.epam.input;

import java.util.Map;

public class InputMessenger {
    private final String templateValue;
    private final Map<String, String> templateValues;

    public InputMessenger(String templateValue, Map<String, String> templateValues) {
        this.templateValue = templateValue;
        this.templateValues = templateValues;
    }

    public String getTemplateValue() {
        return templateValue;
    }

    public Map<String, String> getTemplateValues() {
        return templateValues;
    }
}
