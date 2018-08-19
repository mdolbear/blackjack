package com.oracle.blackjack.exceptions;

public class CardDeckNotFoundException extends RuntimeException {

    /**
     * Answer an instance of me on message
     * @param message
     */
    public CardDeckNotFoundException(String message) {
        super(message);
    }

}
