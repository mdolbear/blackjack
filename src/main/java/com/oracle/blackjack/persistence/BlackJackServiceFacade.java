package com.oracle.blackjack.persistence;

import com.oracle.blackjack.gamemodel.BlackJackGame;
import com.oracle.blackjack.gamemodel.PlayerState;
import com.oracle.blackjack.gamemodel.deck.CardDeck;

import java.util.List;

public interface BlackJackServiceFacade {
    String createNewGame(int aNumberOfPlayers);

    List<PlayerState> playHand(String aGameId,
                               CardDeck aDeck);

    BlackJackGame findGameById(String aGameId);
}
