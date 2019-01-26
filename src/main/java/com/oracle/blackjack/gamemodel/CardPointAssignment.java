package com.oracle.blackjack.gamemodel;

import com.oracle.blackjack.gamemodel.deck.Card;
import com.oracle.blackjack.gamemodel.deck.CardIdentifier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document
public class CardPointAssignment {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
    @Id
    private String id;

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
    private CardIdentifier cardIdentifier;

    @Getter() @Setter(AccessLevel.PRIVATE)
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

}
