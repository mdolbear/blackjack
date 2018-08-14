package com.oracle.blackjack.gamemodel;

import com.oracle.blackjack.gamemodel.deck.Card;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Document(collection="gameCollection")
public class Player {

    @Id
    private String id;

    private List<PlayedCard> cards;

    @Indexed
    private PlayerState state;

    /**
     * Answer a default instance
     */
    public Player() {

        super();
        this.setCards(new ArrayList<PlayedCard>());
        this.setState(PlayerState.PLAYING);
        this.setId(new ObjectId().toHexString());

    }

    /**
     * Accept aCard
     * @param aCard Card
     * @param aGame BlackJackGame
     */
    public void acceptCard(Card aCard, BlackJackGame aGame) {

        int         tempPoints;
        PlayedCard  tempCard;

        //Validate that the current user is still playing
        this.validateStillPlaying();

        //Assign points and add new card
        tempPoints = aGame.assignPointsFor(aCard, this);
        tempCard = new PlayedCard(aCard, tempPoints);
        this.addPlayedCard(tempCard);

        //Evaluate state based on points of current hand
        this.determineNextState();

    }

    /**
     * Validate still playing
     */
    protected void validateStillPlaying() {

        if (!this.isStillPlaying()) {

            throw new IllegalStateException("Player " + this.getId() + " is no longer playing");
        }

    }

    /**
     * Determine state
     */
    protected void determineNextState() {

        if (this.getCurrentHandPoints() > BlackJackCardPointAssigner.MAXIMUM_NUMBER_OF_POINTS) {

            this.setState(PlayerState.LOST);
        }
        else if (this.getCurrentHandPoints() == BlackJackCardPointAssigner.MAXIMUM_NUMBER_OF_POINTS) {

            this.setState(PlayerState.WON);
        }
        else {

            this.setState(PlayerState.PLAYING);
        }

    }

    /**
     * Add aPlayedCard
     * @param aPlayedCard PlayedCard
     */
    protected void addPlayedCard(PlayedCard aPlayedCard) {

        this.getCards().add(aPlayedCard);
    }


    /**
     * Answer my cards
     *
     * @return java.util.List<com.oracle.blackjack.gamemodel.PlayedCard>
     */
    public List<PlayedCard> getCards() {
        return cards;
    }

    /**
     * Set my cards
     *
     * @param cards java.util.List<com.oracle.blackjack.gamemodel.PlayedCard>
     */
    protected void setCards(List<PlayedCard> cards) {
        this.cards = cards;
    }

    /**
     * Answer my state
     *
     * @return com.oracle.blackjack.gamemodel.PlayerState
     */
    public PlayerState getState() {
        return state;
    }

    /**
     * Answer whether I am still playing
     * @return boolean
     */
    public boolean isStillPlaying() {

        return this.getState() != null &&
                    this.getState().equals(PlayerState.PLAYING);

    }

    /**
     * Answer whether or not I am out
     * @return boolean
     */
    public boolean isOut() {

        return this.getState() != null &&
                    this.getState().equals(PlayerState.LOST);

    }

    /**
     * Answer whether or not I am a winner
     * @return boolean
     */
    public boolean isWinner() {

        return this.getState() != null &&
                this.getState().equals(PlayerState.WON);

    }


    /**
     * Set my state
     *
     * @param state com.oracle.blackjack.gamemodel.PlayerState
     */
    public void setState(PlayerState state) {
        this.state = state;
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
     * Answer my current hand points
     * @return int
     */
    public int getCurrentHandPoints() {

        int tempResult = 0;

        for (PlayedCard aPlayedCard: this.getCards()) {

            tempResult += aPlayedCard.getPoints();
        }

        return tempResult;
    }
}
