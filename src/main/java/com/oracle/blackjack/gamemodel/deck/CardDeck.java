package com.oracle.blackjack.gamemodel.deck;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 *
 */
@Document(collection="cardCollection")
public class CardDeck {

    @Getter() @Setter(AccessLevel.PRIVATE)
    @Id
    private String id;

    @Getter() @Setter(AccessLevel.PRIVATE)
    private List<Card> availableCards;

    @Getter() @Setter(AccessLevel.PRIVATE)
    private List<Card> playedCards;


    /**
     * Create a deck of cards
     * @return CardDeck
     */
    public static CardDeck createDeckOfCards() {

        Set<Suit>               tempAllSuits = EnumSet.allOf(Suit.class);
        Set<CardIdentifier>     tempAllIdentifiers = EnumSet.allOf(CardIdentifier.class);
        CardDeck                tempDeck;
        Card                    tempCurrentCard;

        tempDeck = new CardDeck();
        for (Suit aSuit : new ArrayList<Suit>(tempAllSuits)) {

            for (CardIdentifier anIdentifier: new ArrayList<CardIdentifier>(tempAllIdentifiers)) {

                tempCurrentCard = new Card(aSuit, anIdentifier);
                tempDeck.addAvailableCard(tempCurrentCard);
            }
        }

        return tempDeck;

    }

    /**
     * Answer my default instance
     */
    public CardDeck() {

        super();
        this.setAvailableCards(new ArrayList<Card>());
        this.setPlayedCards(new ArrayList<Card>());

    }


    /**
     * Deal a card
     * @return Card
     */
    public Card dealACard() {

        Card                tempCard;


        this.validateAvailableCards();

        tempCard = this.getShuffledCard();
        this.removeAvailableCard(tempCard);
        this.addPlayedCard(tempCard);

        return tempCard;

    }

    /**
     * Answer whether all cards have been played
     * @return boolean
     */
    public boolean areAllCardsPlayed() {

        return this.getAvailableCards().isEmpty() &&
                    !this.getPlayedCards().isEmpty();
    }

    /**
     * Answer a shuffled card
     * @return Card
     */
    private Card getShuffledCard() {

        Card                tempCard;
        List<Card>          tempShuffle;

        tempShuffle = new ArrayList<Card>(this.getAvailableCards());
        Collections.shuffle(this.getAvailableCards());
        tempCard = tempShuffle.get(0);

        return tempCard;
    }

    /**
     * Validate available cards
     */
    private void validateAvailableCards() {

        if (this.getAvailableCards().isEmpty()) {

            throw new IllegalArgumentException("No more cards in deck");
        }
    }



    /**
     * Add a Available card to me
     * @param aCard Card
     */
    private void addAvailableCard(Card aCard) {

        this.getAvailableCards().add(aCard);
    }

    /**
     * Remove Available aCard from me
     * @param aCard Card
     */
    private void removeAvailableCard(Card aCard) {

        this.getAvailableCards().remove(aCard);
    }



    /**
     * Add a Played card to me
     * @param aCard Card
     */
    private void addPlayedCard(Card aCard) {

        this.getPlayedCards().add(aCard);
    }


}
