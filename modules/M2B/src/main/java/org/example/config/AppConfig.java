package org.example.config;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AppConfig {
    private static AppConfig instance;

    private final String applicationName;
    private final String currency;
    private final BigDecimal taxRate;

    private AppConfig() {
        this.applicationName = "Bootcamp Payment App";
        this.currency = "EUR";
        this.taxRate = new BigDecimal("0.21");
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();

        }
        return instance;
    }

}
