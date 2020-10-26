package com.cstiweb.rcpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cstiweb.rcpt")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
