package com.oracle.blackjack.gamemodel;

import com.oracle.blackjack.gamemodel.deck.Card;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document(collection="gameCollection")
public class PlayedCard {

    @Getter() @Setter(AccessLevel.PRIVATE)
    @Id
    private String id;

    @Getter() @Setter(AccessLevel.PRIVATE)
    @DBRef
    private Card card;

    @Getter() @Setter(AccessLevel.PRIVATE)
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



}
