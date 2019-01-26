package com.oracle.blackjack.gamemodel.deck;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 *
 */
@Document(collection="cardCollection")
public class Card {

    @Getter() @Setter(AccessLevel.PRIVATE)
    @Id
    private String id;

    @Getter() @Setter(AccessLevel.PRIVATE)
    private CardIdentifier cardType;

    @Getter() @Setter(AccessLevel.PRIVATE)
    private Suit suit;

    /**
     * Answer a default instance
     */
    public Card() {

        super();
        this.setId(new ObjectId().toHexString());

    }

    /**
     * Answer an instance on
     * @param aSuit Suit
     * @param anIdentifier CardIdentifier
     */
    public Card(Suit aSuit, CardIdentifier anIdentifier) {

        this();
        this.setSuit(aSuit);
        this.setCardType(anIdentifier);

    }


    /**
     * Asnwer wether I am equial to o
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {

        Card    card;
        boolean tempResult;

        if (this == o) {

            tempResult = true;
        }
        else if (o == null || getClass() != o.getClass()) {

            tempResult = false;
        }
        else {

            card = (Card) o;

            //Either all attributes are null, or all attributes are not null and equal
            tempResult = (this.getCardType() == null &&
                                card.getCardType() == null &&
                                    this.getSuit() == null &&
                                                card.getSuit() == null)
                    ||
                        (this.getCardType() != null &&
                                card.getCardType() != null &&
                                        this.getSuit() != null &&
                                            card.getSuit() != null &&
                                                this.getCardType().equals(card.getCardType()) &&
                                                    this.getSuit().equals(card.getSuit()));
        }

        return tempResult;
    }

    /**
     * Answer my hash code
     * @return
     */
    @Override
    public int hashCode() {

        return Objects.hash(this.getCardType(), this.getSuit());

    }
}
