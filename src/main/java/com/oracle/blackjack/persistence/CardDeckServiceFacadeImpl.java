package com.oracle.blackjack.persistence;

import com.oracle.blackjack.gamemodel.deck.CardDeck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 */
@Service
public class CardDeckServiceFacadeImpl implements CardDeckServiceFacade {

    @Autowired
    private CardDeckMongoRepository repository;

    /**
     * Answer a default instance
     */
    public CardDeckServiceFacadeImpl() {

        super();
    }

    /**
     * Answer my repository
     *
     * @return com.oracle.blackjack.persistence.CardDeckMongoRepository
     */
    protected CardDeckMongoRepository getRepository() {
        return repository;
    }

    /**
     * Create new card deck
     * @return String
     */
    @Override
    public String createNewCardDeck() {

        CardDeck    tempDeck;

        //Delete all existing card decks
        this.getRepository().deleteAll();

        //Create and save card deck
        tempDeck = CardDeck.createDeckOfCards();
        this.getRepository().save(tempDeck);

        return tempDeck.getId();

    }

    /**
     * Find a card deck for anId
     * @param anId String
     * @return CardDeck
     */
    @Override
    public CardDeck findById(String anId) {

        Optional<CardDeck> tempOptional;

        tempOptional = this.getRepository().findById(anId);
        return tempOptional.get();

    }

    /**
     * Save aDeck
     * @param aDeck CardDeck
     */
    @Override
    public void saveDeckState(CardDeck aDeck) {

        this.getRepository().save(aDeck);

    }

}
