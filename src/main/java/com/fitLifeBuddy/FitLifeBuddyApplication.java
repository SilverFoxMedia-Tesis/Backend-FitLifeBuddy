package com.fitLifeBuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FitLifeBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitLifeBuddyApplication.class, args);
    }

}
