package com.oracle.blackjack.gamemodel.deck;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 *
 */
@Document(collection="cardCollection")
public class CardDeck {

    @Id
    private String id;

    private List<Card> availableCards;
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
    protected Card getShuffledCard() {

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
    protected void validateAvailableCards() {

        if (this.getAvailableCards().isEmpty()) {

            throw new IllegalArgumentException("No more cards in deck");
        }
    }


    /**
     * Answer my id
     *
     * @return java.lang.String
     */
    public String getId() {
        return id;
    }

    /**
     * Set my id
     *
     * @param id java.lang.String
     */
    protected void setId(String id) {
        this.id = id;
    }


    /**
     * Add a Available card to me
     * @param aCard Card
     */
    protected void addAvailableCard(Card aCard) {

        this.getAvailableCards().add(aCard);
    }

    /**
     * Remove Available aCard from me
     * @param aCard Card
     */
    protected void removeAvailableCard(Card aCard) {

        this.getAvailableCards().remove(aCard);
    }


    /**
     * Answer my available cards
     *
     * @return java.util.List<com.oracle.blackjack.gamemodel.deck.Card>
     */
    public List<Card> getAvailableCards() {
        return availableCards;
    }

    /**
     * Set my available cards
     *
     * @param cards java.util.List<com.oracle.blackjack.gamemodel.deck.Card>
     */
    protected void setAvailableCards(List<Card> cards) {
        this.availableCards = cards;
    }

    /**
     * Add a Played card to me
     * @param aCard Card
     */
    protected void addPlayedCard(Card aCard) {

        this.getPlayedCards().add(aCard);
    }

    /**
     * Remove Played aCard from me
     * @param aCard Card
     */
    protected void removePlayedCard(Card aCard) {

        this.getPlayedCards().remove(aCard);
    }


    /**
     * Answer my playedCards
     *
     * @return java.util.List<com.oracle.blackjack.gamemodel.deck.Card>
     */
    public List<Card> getPlayedCards() {
        return playedCards;
    }

    /**
     * Set my playedCards
     *
     * @param playedCards java.util.List<com.oracle.blackjack.gamemodel.deck.Card>
     */
    protected void setPlayedCards(List<Card> playedCards) {
        this.playedCards = playedCards;
    }


}
