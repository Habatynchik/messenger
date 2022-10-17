package com.epam.service;

import java.util.Map;

/**
 * The type Client.
 */
public class Client {
    private static final String DEFAULT_ADDRESS = "Default address";

    private String addresses;
    private Map<String, String> templateValues;

    public Client(Map<String, String> templateValues) {
        this.templateValues = templateValues;
        setAddresses(DEFAULT_ADDRESS);
    }

    public Map<String, String> getTemplateValues() {
        return templateValues;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }
}
