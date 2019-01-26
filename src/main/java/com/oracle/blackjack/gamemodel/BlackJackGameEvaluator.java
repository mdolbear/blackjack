package com.oracle.blackjack.gamemodel;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Iterator;
import java.util.Optional;

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
    private boolean areAnyWinningPlayers(BlackJackGame aGame) {

        Optional<Player> tempOpt;


        tempOpt = aGame.getPlayers().stream()
                                    .filter((aPlayer) -> aPlayer.isWinner())
                                    .findFirst();

        return tempOpt.isPresent();

    }

    /**
     * Answer whether there are any remaining active players
     * @param aGame BlackJackGame
     */
    private boolean areAnyRemainingActivePlayers(BlackJackGame aGame) {

        Optional<Player> tempOpt;

        tempOpt = aGame.getPlayers().stream()
                                    .filter((aPlayer) -> aPlayer.isStillPlaying())
                                    .findFirst();

        return tempOpt.isPresent();


    }



}
