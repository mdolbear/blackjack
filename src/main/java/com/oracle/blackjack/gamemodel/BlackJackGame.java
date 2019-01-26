package com.oracle.blackjack.gamemodel;

import com.oracle.blackjack.gamemodel.deck.Card;
import com.oracle.blackjack.gamemodel.deck.CardDeck;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
@Document(collection="gameCollection")
public class BlackJackGame implements Serializable {

    @Getter()
    @Id
    private String id;

    @Getter() @Setter(AccessLevel.PRIVATE)
    private List<Player> players;

    @Getter() @Setter(AccessLevel.PRIVATE)
    private BlackJackGameEvaluator gameEvaluator;

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
    private BlackJackCardPointAssigner pointAssigner;

    @Getter() @Setter(AccessLevel.PROTECTED)
    @Indexed
    private GameState state;


    /**
     * Answer my default instance
     */
    public BlackJackGame() {

        super();
        this.setPlayers(new ArrayList<Player>());
        this.setGameEvaluator(new BlackJackGameEvaluator());
        this.setPointAssigner(new BlackJackCardPointAssigner());
        this.setState(GameState.ACTIVE);
    }

    /**
     * Add aPlayer to me
     * @param aPlayer Player
     */
    public void addPlayer(Player aPlayer) {

        this.getPlayers().add(aPlayer);
    }



    /**
     * Answer whether or not I am terminated
     * @return boolean
     */
    public boolean isTerminated() {

        return this.getState() != null &&
                    this.getState().equals(GameState.TERMINATED);

    }


    /**
     * Start game with aNumberOfPlayers
     * @param aNumberOfPlayers int
     */
    public void startGame(int aNumberOfPlayers) {

        this.validateNoPlayersExist();
        this.createPlayers(aNumberOfPlayers);
    }

    /**
     * Play hand
     * @param aCardDeck CardDeck
     * @return List<PlayerState>
     */
    public List<PlayerState> playHand(CardDeck aCardDeck) {

        int                         tempCurrentPlayerIndex;
        Card                        tempCard;
        Player                      tempCurrentPlayer;
        List<PlayerState>           tempStates = new ArrayList<PlayerState>();

        this.validatePlayersExist();
        this.validateActiveGameState();

        //Initialization
        tempCurrentPlayerIndex = 0;

        //Iterate over active players
        while (tempCurrentPlayerIndex < this.getPlayers().size()) {

            tempCurrentPlayer = this.getPlayers().get(tempCurrentPlayerIndex);

            if (tempCurrentPlayer.isStillPlaying()) {

                tempCard = aCardDeck.dealACard();
                tempCurrentPlayer.acceptCard(tempCard, this);
            }

            tempStates.add(tempCurrentPlayer.getState());

            tempCurrentPlayerIndex++;

        }

        //Evaluate game state
        this.getGameEvaluator().evaluateGameState(this);


        return tempStates;

    }

    /**
     * Assign points for aCard
     * @param aPlayer Player
     * @param aCard Card
     * @return int
     */
    public int assignPointsFor(Card aCard, Player aPlayer) {

        return this.getPointAssigner().assignPointsFor(aCard, aPlayer);

    }
    /**
     * Create players
     * @param aNumberOfPlayers int
     */
    private void createPlayers(int aNumberOfPlayers) {

        Player  tempPlayer;

        for (int i = 0; i < aNumberOfPlayers; i++) {

            tempPlayer = new Player();
            this.addPlayer(tempPlayer);
        }
    }

    /**
     * Validate no players exist
     */
    private void validateNoPlayersExist() {

        if (this.getPlayers().size() != 0) {

            throw new IllegalStateException("No players should exist when a new game is started");

        }
    }

    /**
     * Validate no players exist
     */
    private void validatePlayersExist() {

        if (this.getPlayers().size() == 0) {

            throw new IllegalStateException("Players should exist when a hand is played");

        }
    }

    /**
     * Validate active game state
     */
    private void validateActiveGameState() {

        if (!this.isActive()) {

            throw new IllegalStateException("Game already terminated");
        }
    }

    /**
     * Answer whether or not I am active
     * @return boolean
     */
    private boolean isActive() {

        return this.getState() != null &&
                this.getState().equals(GameState.ACTIVE);

    }

    /**
     * Answer my player states
     * @return List<PlayerState>
     */
    public List<PlayerState> getPlayerStates() {

        return this.getPlayers().stream()
                                .map(this.getPlayerToPlayerStateMappingFunction())
                                .collect(Collectors.toList());
    }

    /**
     * Answer my player to player state mapping function
     * @return Function<Player, PlayerState>
     */
    private Function<Player, PlayerState> getPlayerToPlayerStateMappingFunction() {
        return (Player p) -> p.getState();
    }

    /**
     * Answer my player ids
     * @return List<String>
     */
    public List<String> getPlayerIds() {

        return this.getPlayers().stream()
                .map(this.getPlayerToPlayerIdMappingFunction())
                .collect(Collectors.toList());
    }

    /**
     * Answer my player to player id mapping function
     * @return Function<Player, String>
     */
    private Function<Player, String> getPlayerToPlayerIdMappingFunction() {
        return (Player p) -> p.getId();
    }


    /**
     * Answer my player points
     * @return List<Integer>
     */
    public List<Integer> getPlayerPoints() {

        return this.getPlayers().stream()
                .map(this.getPlayerToPlayerPointsMappingFunction())
                .collect(Collectors.toList());
    }


    /**
     * Answer my player to player id mapping function
     * @return Function<Player, Integer>
     */
    private Function<Player, Integer> getPlayerToPlayerPointsMappingFunction() {
        return (Player p) -> new Integer(p.getCurrentHandPoints());
    }



}
