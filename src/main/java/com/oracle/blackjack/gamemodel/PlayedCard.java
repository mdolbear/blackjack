package com.oracle.blackjack.gamemodel;

import com.oracle.blackjack.gamemodel.deck.Card;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document(collection="gameCollection")
public class PlayedCard {

    @Id
    private String id;

    @DBRef
    private Card card;

    private int points;

    /**
     * Answer a default instance
     */
    public PlayedCard() {

        super();
        this.setId(new ObjectId().toHexString());
    }


    /**
     * Answer an instance of me on
     * @param aCard Card
     * @param aPoints int
     */
    public PlayedCard(Card aCard,
                      int aPoints) {

       this();
       this.setCard(aCard);
       this.setPoints(aPoints);
    }


    /**
     * Answer my card
     *
     * @return com.oracle.blackjack.gamemodel.deck.Card
     */
    public Card getCard() {
        return card;
    }

    /**
     * Set my card
     *
     * @param card com.oracle.blackjack.gamemodel.deck.Card
     */
    protected void setCard(Card card) {
        this.card = card;
    }


    /**
     * Answer my points
     *
     * @return int
     */
    public int getPoints() {
        return points;
    }

    /**
     * Set my points
     *
     * @param points int
     */
    public void setPoints(int points) {
        this.points = points;
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

}
