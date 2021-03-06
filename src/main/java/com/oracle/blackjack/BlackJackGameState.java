package com.oracle.blackjack;

import com.oracle.blackjack.gamemodel.BlackJackGame;
import com.oracle.blackjack.gamemodel.GameState;
import com.oracle.blackjack.gamemodel.PlayerState;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Data
public class BlackJackGameState implements Serializable {

    private String gameId;
    private GameState gameState;
    private List<String> playerIds;
    private List<PlayerState> playerStates;
    private List<Integer> playerPoints;

    /**
     * Answer a default instance
     */
    public BlackJackGameState() {

        super();

    }


    /**
     * Answer a default instance
     * @param aGame BlackJackGame
     */
    public BlackJackGameState(BlackJackGame aGame) {

        this();
        this.initializeMeFrom(aGame);
    }

    /**
     * Initialize me from aGame
     * @param aGame BlackJackGame
     */
    protected void initializeMeFrom(BlackJackGame aGame) {

        this.setGameId(aGame.getId());
        this.setGameState(aGame.getState());
        this.setPlayerStates(aGame.getPlayerStates());
        this.setPlayerIds(aGame.getPlayerIds());
        this.setPlayerPoints(aGame.getPlayerPoints());
    }

    /**
     * Answer whether or not game is terminated
     * @return boolean
     */
    public boolean isGameTerminated() {

        return this.getGameState() != null &&
                this.getGameState().equals(GameState.TERMINATED);
    }

    /**
     * Answer my string representation
     * @return String
     */
    @Override
    public String toString() {

        StringBuilder   tempBuilder = new StringBuilder();

        tempBuilder.append("GameId: ");
        tempBuilder.append((this.getGameId() != null) ? this.getGameId() : "null ");
        tempBuilder.append(System.getProperty("line.separator"));

        tempBuilder.append("GameState: " );
        tempBuilder.append((this.getGameState() != null) ? this.getGameState().toString() : " null");
        tempBuilder.append(System.getProperty("line.separator"));

        tempBuilder.append("playerIds: ");
        tempBuilder.append(this.getPlayerIds().toString());
        tempBuilder.append(System.getProperty("line.separator"));

        tempBuilder.append("playerStates: ");
        tempBuilder.append(this.getPlayerStates().toString());
        tempBuilder.append(System.getProperty("line.separator"));

        tempBuilder.append("playerPoints: ");
        tempBuilder.append(this.getPlayerPoints().toString());
        tempBuilder.append(System.getProperty("line.separator"));


        return tempBuilder.toString();

    }

}
