package com.oracle.blackjack;

import com.oracle.blackjack.gamemodel.BlackJackGame;
import com.oracle.blackjack.gamemodel.GameState;
import com.oracle.blackjack.gamemodel.PlayerState;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class BlackJackGameState implements Serializable {

    private String gameId;
    private GameState gameState;
    private List<String> playerIds;
    private List<PlayerState> playerStates;
    private List<Integer> playerPoints;

    /**
     * Answer a default instance
     * @param aGame BlackJackGame
     */
    public BlackJackGameState(BlackJackGame aGame) {

        super();
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
     * Answer my gameId
     *
     * @return java.lang.String
     */
    public String getGameId() {
        return gameId;
    }

    /**
     * Set my gameId
     *
     * @param gameId java.lang.String
     */
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    /**
     * Answer my gameState
     *
     * @return com.oracle.blackjack.gamemodel.GameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Set my gameState
     *
     * @param gameState com.oracle.blackjack.gamemodel.GameState
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Answer my playerIds
     *
     * @return java.util.List<java.lang.String>
     */
    public List<String> getPlayerIds() {
        return playerIds;
    }

    /**
     * Set my playerIds
     *
     * @param playerIds java.util.List<java.lang.String>
     */
    public void setPlayerIds(List<String> playerIds) {
        this.playerIds = playerIds;
    }

    /**
     * Answer my playerStates
     *
     * @return java.util.List<com.oracle.blackjack.gamemodel.PlayerState>
     */
    public List<PlayerState> getPlayerStates() {
        return playerStates;
    }

    /**
     * Set my playerStates
     *
     * @param playerStates java.util.List<com.oracle.blackjack.gamemodel.PlayerState>
     */
    public void setPlayerStates(List<PlayerState> playerStates) {
        this.playerStates = playerStates;
    }

    /**
     * Answer my playerPoints
     *
     * @return java.util.List<java.lang.Integer>
     */
    public List<Integer> getPlayerPoints() {
        return playerPoints;
    }

    /**
     * Set my playerPoints
     *
     * @param playerPoints java.util.List<java.lang.Integer>
     */
    public void setPlayerPoints(List<Integer> playerPoints) {
        this.playerPoints = playerPoints;
    }

}
