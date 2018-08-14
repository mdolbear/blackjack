package com.oracle.blackjack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *
 */
@SpringBootApplication
public class BlackJackApplication {

    /**
     * Answer an instance for the following arguments
     */
    public BlackJackApplication() {
        super();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        SpringApplication.run(BlackJackApplication.class, args);

    }

}
