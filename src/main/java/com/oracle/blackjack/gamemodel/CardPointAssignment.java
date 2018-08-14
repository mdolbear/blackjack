package com.oracle.blackjack.gamemodel;

import com.oracle.blackjack.gamemodel.deck.Card;
import com.oracle.blackjack.gamemodel.deck.CardIdentifier;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document
public class CardPointAssignment {

    @Id
    private String id;
    private CardIdentifier cardIdentifier;

    private int[]   possiblePointValues;

    /**
     * Answer a default instance
     */
    public CardPointAssignment() {

        super();
    }

    /**
     * Answer an instance of me on
     * @param aCardIdentifier CardIdentifier
     * @param aPoints int[]
     */
    public CardPointAssignment(CardIdentifier aCardIdentifier,
                               int[] aPoints) {

        this();
        this.setPossiblePointValues(aPoints);
        this.setCardIdentifier(aCardIdentifier);
    }

    /**
     * Answer whether or not I am the point assignment for aCard
     * @param aCard Card
     */
    public boolean isPointAssignmentFor(Card aCard) {

        return aCard != null &&
                this.getCardIdentifier().equals(aCard.getCardType());

    }

    /**
     * Answer my possiblePointValues
     *
     * @return int[]
     */
    protected int[] getPossiblePointValues() {
        return possiblePointValues;
    }

    /**
     * Answer my cardIdentifier
     *
     * @return com.oracle.blackjack.gamemodel.deck.CardIdentifier
     */
    public CardIdentifier getCardIdentifier() {
        return cardIdentifier;
    }

    /**
     * Set my cardIdentifier
     *
     * @param cardIdentifier com.oracle.blackjack.gamemodel.deck.CardIdentifier
     */
    public void setCardIdentifier(CardIdentifier cardIdentifier) {
        this.cardIdentifier = cardIdentifier;
    }

    /**
     * Set my possiblePointValues
     *
     * @param possiblePointValues int[]
     */
    protected void setPossiblePointValues(int[] possiblePointValues) {
        this.possiblePointValues = possiblePointValues;
    }


}
