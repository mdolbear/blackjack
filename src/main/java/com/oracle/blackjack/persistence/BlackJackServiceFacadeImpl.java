package com.oracle.blackjack.persistence;

import com.oracle.blackjack.exceptions.CardDeckNotFoundException;
import com.oracle.blackjack.exceptions.GameNotFoundException;
import com.oracle.blackjack.gamemodel.BlackJackGame;
import com.oracle.blackjack.gamemodel.PlayerState;
import com.oracle.blackjack.gamemodel.deck.CardDeck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *
 */
@Service
public class BlackJackServiceFacadeImpl implements BlackJackServiceFacade {


    @Autowired
    private BlackJackGameMongoRepository repository;

    @Autowired
    private CardDeckServiceFacade cardDeckServiceFacade;

    /**
     * Answer my default instance
     */
    public BlackJackServiceFacadeImpl() {

        super();

    }

    /**
     * Start game for aNumberOfPlayers
     * @param aNumberOfPlayers int
     * @return String
     */
    @Override
    public String createNewGame(int aNumberOfPlayers) {

        BlackJackGame   tempGame;

        //Delete all existing games -- will only allow one until we figure
        //out more about Mongo
        this.getRepository().deleteAll();

        //Create new game, start it, and save it
        tempGame = new BlackJackGame();
        tempGame.startGame(aNumberOfPlayers);

        this.getRepository().save(tempGame);

        return tempGame.getId();

    }

    /**
     * Play a hand for a game identified by anId
     * @param aGameId String
     * @param aDeck CardDeck
     * @return List<PlayerState>
     */
    @Override
    public List<PlayerState> playHand(String aGameId,
                                      CardDeck aDeck) {

        List<PlayerState>   tempStates;
        BlackJackGame       tempGame;

        tempGame = this.getRepository().findById(aGameId).get();
        tempStates = tempGame.playHand(aDeck);

        //Both the deck and the game have been modified by this operation -- save both
        this.getRepository().save(tempGame);
        this.getCardDeckServiceFacade().saveDeckState(aDeck);

        return tempStates;
    }

    /**
     * Find a game by its id
     * @param aGameId String
     * @return BlackJackGame
     */
    @Override
    public BlackJackGame findGameById(String aGameId) {

        BlackJackGame       tempGame;

        tempGame = this.safelyGetGame(aGameId,
                                      this.getRepository().findById(aGameId));
        return tempGame;

    }

    /**
     * Answer my card deck or throw a not found exception
     * @param anOptional
     * @return
     */
    protected BlackJackGame safelyGetGame(String anId,
                                          Optional<BlackJackGame> anOptional)
            throws GameNotFoundException {

        try {
            return anOptional.get();
        }
        catch (NoSuchElementException e) {

            throw new GameNotFoundException("Game not found for id: " + anId);
        }

    }


    /**
     * Answer my repository
     *
     * @return com.oracle.blackjack.persistence.BlackJackGameMongoRepository
     */
    protected BlackJackGameMongoRepository getRepository() {
        return repository;
    }


    /**
     * Answer my cardDeckServiceFacade
     *
     * @return com.oracle.blackjack.persistence.CardDeckServiceFacade
     */
    protected CardDeckServiceFacade getCardDeckServiceFacade() {
        return cardDeckServiceFacade;
    }

}
