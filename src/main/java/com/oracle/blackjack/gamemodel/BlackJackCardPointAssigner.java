package com.oracle.blackjack.gamemodel;

import com.oracle.blackjack.gamemodel.deck.Card;
import com.oracle.blackjack.gamemodel.deck.CardIdentifier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Document(collection="gameCollection")
public class BlackJackCardPointAssigner {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
    private List<CardPointAssignment> pointAssignments;

    //Constants
    protected static final int MAXIMUM_NUMBER_OF_POINTS = 21;


    /**
     * Answer a default instance
     */
    public BlackJackCardPointAssigner() {

        super();
        this.intializePointAssignments();
    }


    /**
     * Initialize Card point assignments
     * @return List<CardPointAssignment>
     */
    protected void intializePointAssignments() {

        List<CardPointAssignment> tempAssignments = new ArrayList<CardPointAssignment>();
        int[]                     tempPointValues;

        tempPointValues = new int[1];
        tempPointValues[0] = 2;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.TWO,
                                                    tempPointValues));

        tempPointValues = new int[1];
        tempPointValues[0] = 3;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.THREE,
                                                    tempPointValues));

        tempPointValues = new int[1];
        tempPointValues[0] = 4;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.FOUR,
                                                    tempPointValues));

        tempPointValues = new int[1];
        tempPointValues[0] = 5;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.FIVE,
                                                    tempPointValues));

        tempPointValues = new int[1];
        tempPointValues[0] = 6;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.SIX,
                                                    tempPointValues));

        tempPointValues = new int[1];
        tempPointValues[0] = 7;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.SEVEN,
                                                    tempPointValues));

        tempPointValues = new int[1];
        tempPointValues[0] = 8;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.EIGHT,
                                                    tempPointValues));

        tempPointValues = new int[1];
        tempPointValues[0] = 9;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.NINE,
                                                    tempPointValues));

        tempPointValues = new int[1];
        tempPointValues[0] = 10;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.TEN,
                                                    tempPointValues));

        tempPointValues = new int[1];
        tempPointValues[0] = 10;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.JACK,
                                                    tempPointValues));

        tempPointValues = new int[1];
        tempPointValues[0] = 10;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.QUEEN,
                                                    tempPointValues));

        tempPointValues = new int[1];
        tempPointValues[0] = 10;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.KING,
                                                    tempPointValues));

        tempPointValues = new int[2];
        tempPointValues[0] = 11;
        tempPointValues[1] = 1;
        tempAssignments.add(new CardPointAssignment(CardIdentifier.ACE,
                                                    tempPointValues));

        this.setPointAssignments(tempAssignments);

    }

    /**
     * Answer my assigment for aCard
     * @param aCard Card
     * @return CardPointAssignment
     */
    protected CardPointAssignment getCardPointAssignment(Card aCard) {

        Optional<CardPointAssignment> tempOpt;
        CardPointAssignment           tempResult = null;


        tempOpt =  this.getPointAssignments()
                   .stream()
                   .filter((anAssignment -> anAssignment.isPointAssignmentFor(aCard)))
                   .findFirst();

        if (tempOpt.isPresent()) {

            tempResult = tempOpt.get();
        }

        return tempResult;


    }

    /**
     * Assign point for aCard and aPlayer
     * @param aCard Card
     * @param aPlayer Player
     * @return int
     */
    public int assignPointsFor(Card aCard, Player aPlayer) {

        int                     tempResult = 0;
        int[]                   tempPossiblePoints;
        CardPointAssignment     tempAssignment;
        int                     i = 0;

        tempAssignment = this.getCardPointAssignment(aCard);
        if (tempAssignment != null) {

            tempPossiblePoints = tempAssignment.getPossiblePointValues();
            while (i < tempPossiblePoints.length && tempResult == 0) {

                if (i + 1 == tempPossiblePoints.length) {

                    tempResult = tempPossiblePoints[i];

                }
                else if (aPlayer.getCurrentHandPoints()
                            + tempPossiblePoints[i] <= MAXIMUM_NUMBER_OF_POINTS) {

                    tempResult = tempPossiblePoints[i];
                }

                i += 1;

            }
        }

        return tempResult;
    }


}
