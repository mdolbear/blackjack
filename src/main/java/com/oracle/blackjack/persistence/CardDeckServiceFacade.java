package com.oracle.blackjack.persistence;

import com.oracle.blackjack.gamemodel.deck.CardDeck;

public interface CardDeckServiceFacade {

    /**
     * Create a new card deck. Delete all card decks prior to this
     * @return String
     */
    String createNewCardDeck();

    /**
     * Find card deck by id
     * @param anId
     * @return
     */
    CardDeck findById(String anId);

    /**
     * Save the state of the current card deck
     * @param aDeck
     */
    void saveDeckState(CardDeck aDeck);
}
