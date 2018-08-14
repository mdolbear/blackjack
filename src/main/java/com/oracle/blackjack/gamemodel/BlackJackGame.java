package com.oracle.blackjack.gamemodel;

import com.oracle.blackjack.gamemodel.deck.Card;
import com.oracle.blackjack.gamemodel.deck.CardDeck;
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

    @Id
    private String id;

    private List<Player> players;
    private BlackJackGameEvaluator gameEvaluator;
    private BlackJackCardPointAssigner pointAssigner;
    private int currentPlayerIndex;

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
     * Remove aPlayer from me
     * @param aPlayer Player
     */
    public void removePlayer(Player aPlayer) {

        this.getPlayers().remove(aPlayer);
    }


    /**
     * Answer my players
     *
     * @return java.util.List<com.oracle.blackjack.gamemodel.Player>
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Set my players
     *
     * @param players java.util.List<com.oracle.blackjack.gamemodel.Player>
     */
    protected void setPlayers(List<Player> players) {
        this.players = players;
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
     * Answer my gameEvaluator
     *
     * @return com.oracle.blackjack.gamemodel.BlackJackGameEvaluator
     */
    protected BlackJackGameEvaluator getGameEvaluator() {
        return gameEvaluator;
    }

    /**
     * Set my gameEvaluator
     *
     * @param gameEvaluator com.oracle.blackjack.gamemodel.BlackJackGameEvaluator
     */
    protected void setGameEvaluator(BlackJackGameEvaluator gameEvaluator) {
        this.gameEvaluator = gameEvaluator;
    }

    /**
     * Answer my pointAssigner
     *
     * @return com.oracle.blackjack.gamemodel.BlackJackCardPointAssigner
     */
    protected BlackJackCardPointAssigner getPointAssigner() {
        return pointAssigner;
    }


    /**
     * Set my pointAssigner
     *
     * @param pointAssigner com.oracle.blackjack.gamemodel.BlackJackCardPointAssigner
     */
    protected void setPointAssigner(BlackJackCardPointAssigner pointAssigner) {
        this.pointAssigner = pointAssigner;
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
     * Answer my state
     *
     * @return com.oracle.blackjack.gamemodel.GameState
     */
    public GameState getState() {
        return state;
    }

    /**
     * Set my state
     *
     * @param state com.oracle.blackjack.gamemodel.GameState
     */
    protected void setState(GameState state) {
        this.state = state;
    }

    /**
     * Increment player index
     * @return int
     */
    protected int incrementPlayerIndex() {

        int tempIndex;

        tempIndex = this.getCurrentPlayerIndex();
        tempIndex = (tempIndex + 1) % this.getPlayers().size();
        this.setCurrentPlayerIndex(tempIndex);

        return tempIndex;

    }


    /**
     * Answer my currentPlayerIndex
     *
     * @return int
     */
    protected int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Set my currentPlayerIndex
     *
     * @param currentPlayerIndex int
     */
    protected void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    /**
     * Start game with aNumberOfPlayers
     * @param aNumberOfPlayers int
     */
    public void startGame(int aNumberOfPlayers) {

        this.validateNoPlayersExist();
        this.createPlayers(aNumberOfPlayers);
        this.setCurrentPlayerIndex(0);
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
        this.setCurrentPlayerIndex(0);
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
            this.setCurrentPlayerIndex(tempCurrentPlayerIndex);

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
    protected int assignPointsFor(Card aCard, Player aPlayer) {

        return this.getPointAssigner().assignPointsFor(aCard, aPlayer);

    }
    /**
     * Create players
     * @param aNumberOfPlayers int
     */
    protected void createPlayers(int aNumberOfPlayers) {

        Player  tempPlayer;

        for (int i = 0; i < aNumberOfPlayers; i++) {

            tempPlayer = new Player();
            this.addPlayer(tempPlayer);
        }
    }

    /**
     * Validate no players exist
     */
    protected void validateNoPlayersExist() {

        if (this.getPlayers().size() != 0) {

            throw new IllegalStateException("No players should exist when a new game is started");

        }
    }

    /**
     * Validate no players exist
     */
    protected void validatePlayersExist() {

        if (this.getPlayers().size() == 0) {

            throw new IllegalStateException("Players should exist when a hand is played");

        }
    }

    /**
     * Validate active game state
     */
    protected void validateActiveGameState() {

        if (!this.isActive()) {

            throw new IllegalStateException("Game already terminated");
        }
    }

    /**
     * Answer whether or not I am active
     * @return boolean
     */
    protected boolean isActive() {

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
    protected Function<Player, PlayerState> getPlayerToPlayerStateMappingFunction() {
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
    protected Function<Player, String> getPlayerToPlayerIdMappingFunction() {
        return (Player p) -> p.getId();
    }


}
