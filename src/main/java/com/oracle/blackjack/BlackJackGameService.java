package com.oracle.blackjack;

import com.oracle.blackjack.gamemodel.BlackJackGame;
import com.oracle.blackjack.gamemodel.deck.CardDeck;
import com.oracle.blackjack.persistence.BlackJackServiceFacade;
import com.oracle.blackjack.persistence.CardDeckServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 *
 */
@RestController
@RequestMapping("/blackjack")
public class BlackJackGameService {

    @Autowired
    private BlackJackServiceFacade  blackJackServiceFacade;

    @Autowired
    private CardDeckServiceFacade cardDeckServiceFacade;

    //Constants
    private static final Logger logger = LoggerFactory.getLogger(BlackJackGameService.class);

    /**
     * Answer my logger
     *
     * @return org.slf4j.Logger
     */
    protected static Logger getLogger() {
        return logger;
    }

    /**
     * Answer my default instance
     */
    public BlackJackGameService() {

        super();
    }

    /**
     * Answer my blackJackServiceFacade
     *
     * @return com.oracle.blackjack.persistence.BlackJackServiceFacade
     */
    protected BlackJackServiceFacade getBlackJackServiceFacade() {
        return blackJackServiceFacade;
    }

    /**
     * Answer my cardDeckServiceFacade
     *
     * @return com.oracle.blackjack.persistence.CardDeckServiceFacade
     */
    protected CardDeckServiceFacade getCardDeckServiceFacade() {
        return cardDeckServiceFacade;
    }


    /**
     * Create and start new game
     * @param aNumberOfPlayers int
     */
    @PostMapping("startgame")
    public String createAndStartNewGame(@RequestParam("numberOfPlayers") Integer aNumberOfPlayers) {

        return this.getBlackJackServiceFacade().createNewGame(aNumberOfPlayers);
    }

    /**
     * Answer my game state
     * @param anId String
     * @return BlackJackGameState
     */
    @GetMapping("gameState")
    public BlackJackGameState getGameState(@RequestParam("gameId") String anId) {

        BlackJackGame   tempGame;

        tempGame =  this.getBlackJackServiceFacade().findGameById(anId);
        return new BlackJackGameState(tempGame);

    }

    /**
     * Create card deck
     * @return String
     */
    @PostMapping("createCardDeck")
    public String createCardDeck() {

        return this.getCardDeckServiceFacade().createNewCardDeck();
    }

    /**
     * Play a hand
     * @param aCardDeckId String
     * @param aGameId String
     * @return BlackJackGameState
     */
    @PutMapping("playHand")
    public BlackJackGameState playHand(@RequestParam("cardDeckId") String aCardDeckId,
                                       @RequestParam("gameId") String aGameId) {

        CardDeck        tempDeck;
        BlackJackGame   tempGame;

        tempDeck = this.getCardDeckServiceFacade().findById(aCardDeckId);
        this.getBlackJackServiceFacade().playHand(aGameId, tempDeck);

        tempGame =
                this.getBlackJackServiceFacade().findGameById(aGameId);

        return new BlackJackGameState(tempGame);

    }

}
