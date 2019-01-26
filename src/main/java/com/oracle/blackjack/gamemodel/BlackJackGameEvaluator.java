package com.oracle.blackjack.gamemodel;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Iterator;

/**
 *
 */
@Document(collection="gameCollection")
public class BlackJackGameEvaluator {


    /**
     * Answer a default instance
     */
    public BlackJackGameEvaluator() {

        super();
    }

    /**
     * Evaluate game state for aGame
     * @param aGame BlackJackGame
     */
    public void evaluateGameState(BlackJackGame aGame) {

        boolean tempGameTerminated;

        tempGameTerminated =
                this.areAnyWinningPlayers(aGame) ||
                        !this.areAnyRemainingActivePlayers(aGame);

        if (tempGameTerminated) {

            aGame.setState(GameState.TERMINATED);
        }
        else {

            aGame.setState(GameState.ACTIVE);
        }
    }

    /**
     * Answer whether there are any winning players
     * @param aGame BlackJackGame
     */
    protected boolean areAnyWinningPlayers(BlackJackGame aGame) {

        Iterator<Player> tempPlayers;
        Player           tempCurrent;
        Player           tempResult = null;

        tempPlayers = aGame.getPlayers().iterator();
        while (tempResult == null &&
                tempPlayers.hasNext()) {

            tempCurrent = tempPlayers.next();
            if (tempCurrent.isWinner()) {
                tempResult = tempCurrent;
            }
        }

        return tempResult != null;

    }

    /**
     * Answer whether there are any remaining active players
     * @param aGame BlackJackGame
     */
    protected boolean areAnyRemainingActivePlayers(BlackJackGame aGame) {

        Iterator<Player> tempPlayers;
        Player           tempCurrent;
        Player           tempResult = null;

        tempPlayers = aGame.getPlayers().iterator();
        while (tempResult == null &&
                    tempPlayers.hasNext()) {

            tempCurrent = tempPlayers.next();
            if (tempCurrent.isStillPlaying()) {
                tempResult = tempCurrent;
            }
        }

        return tempResult != null;

    }



}
