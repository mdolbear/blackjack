package com.oracle.blackjack.gamemodel.deck;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 *
 */
@Document(collection="cardCollection")
//@CompoundIndex(def="{'cardType':1, 'suit': 1", name="cardCompoundIndex")
public class Card {

    @Id
    private String id;

    private CardIdentifier cardType;
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
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Answer my cardType
     *
     * @return com.oracle.blackjack.gamemodel.deck.CardIdentifier
     */
    public CardIdentifier getCardType() {
        return cardType;
    }

    /**
     * Set my cardType
     *
     * @param cardType com.oracle.blackjack.gamemodel.deck.CardIdentifier
     */
    protected void setCardType(CardIdentifier cardType) {
        this.cardType = cardType;
    }

    /**
     * Answer my suit
     *
     * @return com.oracle.blackjack.gamemodel.deck.Suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Set my suit
     *
     * @param suit com.oracle.blackjack.gamemodel.deck.Suit
     */
    protected void setSuit(Suit suit) {
        this.suit = suit;
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
