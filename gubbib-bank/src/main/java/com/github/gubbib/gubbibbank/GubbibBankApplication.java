package com.github.gubbib.gubbibbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GubbibBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(GubbibBankApplication.class, args);
    }

}
