package com.oracle.blackjack;

import com.oracle.blackjack.gamemodel.BlackJackGame;
import com.oracle.blackjack.gamemodel.Player;
import com.oracle.blackjack.gamemodel.PlayerState;
import com.oracle.blackjack.gamemodel.deck.CardDeck;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class BlackJackGameTest {

    /**
     * Simple black jack game test
     */
    @Test
    public void blackJackGameSimpleTest() {

        BlackJackGame       tempGame;
        List<PlayerState>   tempPlayerStates;
        CardDeck            tempDeck;
        int                 tempNumberOfPlayers;

        //Play game with 3 players
        tempNumberOfPlayers = 3;
        tempGame = new BlackJackGame();
        tempGame.startGame(tempNumberOfPlayers);
        assertTrue("Number of players not " + tempNumberOfPlayers,
                    tempGame.getPlayers().size() == tempNumberOfPlayers);

        tempDeck = CardDeck.createDeckOfCards();
        assertTrue("Card deck not created", !tempDeck.areAllCardsPlayed());

        this.playGame(tempGame, tempDeck, tempNumberOfPlayers);

        //Play game with 1 players
        tempNumberOfPlayers = 1;
        tempGame = new BlackJackGame();
        tempGame.startGame(tempNumberOfPlayers);
        assertTrue("Number of players not " + tempNumberOfPlayers,
                tempGame.getPlayers().size() == tempNumberOfPlayers);

        tempDeck = CardDeck.createDeckOfCards();
        assertTrue("Card deck not created", !tempDeck.areAllCardsPlayed());

        this.playGame(tempGame, tempDeck, tempNumberOfPlayers);

        //Play game with 5 players
        tempNumberOfPlayers = 5;
        tempGame = new BlackJackGame();
        tempGame.startGame(tempNumberOfPlayers);
        assertTrue("Number of players not " + tempNumberOfPlayers,
                tempGame.getPlayers().size() == tempNumberOfPlayers);

        tempDeck = CardDeck.createDeckOfCards();
        assertTrue("Card deck not created", !tempDeck.areAllCardsPlayed());

        this.playGame(tempGame, tempDeck, tempNumberOfPlayers);

        //Play game with 7 players
        tempNumberOfPlayers = 7;
        tempGame = new BlackJackGame();
        tempGame.startGame(tempNumberOfPlayers);
        assertTrue("Number of players not " + tempNumberOfPlayers,
                tempGame.getPlayers().size() == tempNumberOfPlayers);

        tempDeck = CardDeck.createDeckOfCards();
        assertTrue("Card deck not created", !tempDeck.areAllCardsPlayed());

        this.playGame(tempGame, tempDeck, tempNumberOfPlayers);


    }

    /**
     * Play game
     * @param aGame
     * @param aDeck
     * @param aNumberOfPlayers
     */
    protected void playGame(BlackJackGame aGame,
                            CardDeck aDeck,
                            int aNumberOfPlayers) {

        List<PlayerState> tempPlayerStates;

        while (!aGame.isTerminated()) {

            tempPlayerStates = aGame.playHand(aDeck);
            System.out.println("Player states: " + tempPlayerStates.toString());
            assertTrue("Number of players not " + aNumberOfPlayers,
                       tempPlayerStates.size() == aNumberOfPlayers);
            this.dumpCurrentScoresForPlayers(aGame);

        }

        System.out.println("------------------------------------");

    }

    /**
     * Dump current scores for players
     * @param aGame BlackJackGame
     */
    protected void dumpCurrentScoresForPlayers(BlackJackGame aGame) {

        int i = 0;

        for (Player aPlayer: aGame.getPlayers()) {

            System.out.println("Player " + i + " score: " + aPlayer.getCurrentHandPoints());
            i++;
        }
    }
}
