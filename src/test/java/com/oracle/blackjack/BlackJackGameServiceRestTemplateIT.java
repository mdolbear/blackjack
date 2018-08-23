package com.oracle.blackjack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.assertTrue;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BlackJackApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlackJackGameServiceRestTemplateIT {

    @LocalServerPort
    private int port;


    /**
     * Perform simple game test
     */
    @Test
    public void performSimpleGameTest() throws Exception {

        BlackJackGameState      tempResult;
        String                  tempCardDeckId;
        String                  tempGameId;
        String                  tempState;
        String                  tempBaseUrl;
        boolean                 tempGameComplete;

        //Create base url
        System.out.println("Random port generated for test: " + this.getPort());
        tempBaseUrl = "http://localhost:" + this.getPort() + "/blackjack/";

        //Create card deck
        tempCardDeckId = this.performCreateCardDeckInvocation(tempBaseUrl);

        assertTrue("No result", tempCardDeckId != null);
        System.out.println("Card Deck Id: " + tempCardDeckId);


        //Create game
        tempGameId = this.performCreateGameInvocation(tempBaseUrl,3);

        assertTrue("No result", tempGameId != null);
        System.out.println("Game Id: " + tempGameId);


        //Play hand until game complete
        tempGameComplete = false;
        while (!tempGameComplete) {
            tempResult = this.performPlayHandInvocation(tempBaseUrl,
                    tempCardDeckId,
                    tempGameId);

            assertTrue("No result", tempResult != null);
            System.out.println("Game state result: " + tempResult.toString());

            tempGameComplete = tempResult.isGameTerminated();

        }


    }

    /**
     * Perform create card deck invocation
     * @return String
     * @throws Exception
     */
    protected String performCreateCardDeckInvocation(String aBaseUrl) throws Exception {

        RestTemplate                                    tempTemplate;
        String                                          tempResult;

        tempTemplate = new RestTemplate();
        tempResult =
                tempTemplate.postForObject(aBaseUrl + "createCardDeck",
                                           null,
                                           String.class);

        assertTrue("Result is null", tempResult != null);

        return tempResult;

    }


    /**
     * Perform create game invocation
     * @param aBaseUrl String
     * @param aNumberOfPlayers int
     * @return String
     * @throws Exception
     */
    protected String performCreateGameInvocation(String aBaseUrl,
                                                 int aNumberOfPlayers) throws Exception {

        RestTemplate                                    tempTemplate;
        HttpHeaders                                     tempHeaders;
        MultiValueMap<String, String>                   tempParams;
        HttpEntity<MultiValueMap<String, String>>      tempRequest;
        String                                          tempResult;

        tempTemplate = new RestTemplate();
        tempHeaders = new HttpHeaders();
        tempHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        tempParams = new LinkedMultiValueMap<String, String>();
        tempParams.add("numberOfPlayers", String.valueOf(aNumberOfPlayers));

        tempRequest = new HttpEntity<MultiValueMap<String, String>>(tempParams, tempHeaders);

        tempResult =
                tempTemplate.postForObject(aBaseUrl + "startgame",
                                           tempRequest,
                                           String.class);

        assertTrue("Result is null", tempResult != null);

        return tempResult;

    }

    /**
     * Play hand
     * @param aBaseUrl String
     * @param aCardDeckId
     * @param aGameId
     * @return BlackJackGameState
     * @throws Exception
     */
    protected BlackJackGameState performPlayHandInvocation(String aBaseUrl,
                                                           String aCardDeckId,
                                                           String aGameId) throws Exception {


        RestTemplate                                    tempTemplate;
        ResponseEntity<BlackJackGameState>              tempEntity;
        HttpHeaders                                     tempHeaders;
        MultiValueMap<String, String>                   tempParams;
        HttpEntity<MultiValueMap<String, String>>       tempRequest;
        BlackJackGameState                              tempResult;

        tempTemplate = new RestTemplate();
        tempHeaders = new HttpHeaders();
        tempHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        tempParams = new LinkedMultiValueMap<String, String>();
        tempParams.add("cardDeckId", aCardDeckId);
        tempParams.add("gameId", aGameId);
        tempRequest = new HttpEntity<MultiValueMap<String, String>>(tempParams, tempHeaders);
        tempEntity =
                tempTemplate.exchange(aBaseUrl + "playHand",
                                      HttpMethod.PUT,
                                      tempRequest,
                                      BlackJackGameState.class);
        tempResult = tempEntity.getBody();
        assertTrue("Status code failure",
                    tempEntity.getStatusCode() ==
                                      HttpStatus.OK);
        assertTrue("Result is null", tempResult != null);

        return tempResult;

    }

    /**
     * Perform game state invocation
     * @param aGameId String
     * @return BlackJackGameState
     * @throws Exception
     */
    protected BlackJackGameState performGetStateInvocation(String aBaseUrl,
                                                           String aGameId) throws Exception {

        RestTemplate                                    tempTemplate;
        ResponseEntity<BlackJackGameState>              tempEntity;
        HttpHeaders                                     tempHeaders;
        MultiValueMap<String, String>                   tempParams;
        HttpEntity<MultiValueMap<String, String>>       tempRequest;
        BlackJackGameState                              tempResult;

        tempTemplate = new RestTemplate();
        tempHeaders = new HttpHeaders();
        tempHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        tempParams = new LinkedMultiValueMap<String, String>();
        tempParams.add("gameId", aGameId);
        tempRequest = new HttpEntity<MultiValueMap<String, String>>(tempParams, tempHeaders);
        tempResult =
                tempTemplate.getForObject(aBaseUrl + "gameState",
                                          BlackJackGameState.class,
                                          tempRequest);

        assertTrue("Result is null", tempResult != null);

        return tempResult;

    }

    /**
     * Answer my port for testing
     * @return
     */
    protected int getPort() {
        return port;
    }


}
