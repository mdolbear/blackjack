package com.oracle.blackjack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.function.Predicate;

import static junit.framework.TestCase.assertTrue;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BlackJackApplication.class)
@WebAppConfiguration
public class BlackJackGameServiceTest {

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJacksonHttpMessageConverter;
    private MediaType contentType;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * Answer an instance for the following arguments
     */
    public BlackJackGameServiceTest() {

        super();
        this.setContentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8")));

    }

    /**
     * Dump game state
     * @param aGameState BlackJackGameState
     */
    public void dumpGameState(BlackJackGameState aGameState) {

        System.out.println("gameId: " + aGameState.getGameId());
        System.out.println("gameState: " + aGameState.getGameState());
        System.out.println("playerIds: " + aGameState.getPlayerIds().toString());
        System.out.println("playerStates: " + aGameState.getPlayerStates().toString());
        System.out.println("playerPoints: " + aGameState.getPlayerPoints().toString());

    }

    /**
     * Perform simple game test
     */
    @Test
    public void performSimpleGameTest() throws Exception {

        String                  tempMockResult;
        MockHttpServletResponse tempHttpResponse;
        MvcResult               tempResult;
        String                  tempCardDeckId;
        String                  tempGameId;
        String                  tempState;

        //Create card deck
        tempResult = this.performCreateCardDeckInvocation();

        assertTrue("No result", tempResult != null);
        tempHttpResponse = tempResult.getResponse();
        tempMockResult = tempHttpResponse.getContentAsString();
        System.out.println("Mock result: " + tempMockResult.toString());

        tempCardDeckId = tempMockResult.toString();

        //Create game
        tempResult = this.performCreateGameInvocation(3);

        assertTrue("No result", tempResult != null);
        tempHttpResponse = tempResult.getResponse();
        tempMockResult = tempHttpResponse.getContentAsString();
        System.out.println("Mock result: " + tempMockResult.toString());

        tempGameId = tempMockResult.toString();

        //Play a hand
        tempResult = this.performPlayHandInvocation(tempCardDeckId, tempGameId);

        assertTrue("No result", tempResult != null);
        tempHttpResponse = tempResult.getResponse();
        tempMockResult = tempHttpResponse.getContentAsString();
        System.out.println("Mock result: " + tempMockResult.toString());

        tempState = tempMockResult.toString();

    }


    /**
     * Perform create card deck invocation
     * @return
     * @throws Exception
     */
    protected MvcResult performCreateCardDeckInvocation() throws Exception {

        return this.getMockMvc()
                .perform(MockMvcRequestBuilders.post("/blackjack/createCardDeck"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }


    /**
     * Perform create game invocation
     * @param aNumberOfPlayers int
     * @return
     * @throws Exception
     */
    protected MvcResult performCreateGameInvocation(int aNumberOfPlayers) throws Exception {

        return this.getMockMvc()
                    .perform(MockMvcRequestBuilders.post("/blackjack/startgame")
                                                   .param("numberOfPlayers",
                                                        String.valueOf(aNumberOfPlayers)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

    }

    /**
     * Play hand
     * @param aCardDeckId
     * @param aGameId
     * @return
     * @throws Exception
     */
    protected MvcResult performPlayHandInvocation(String aCardDeckId,
                                                  String aGameId) throws Exception {

        return this.getMockMvc()
                    .perform(MockMvcRequestBuilders.put("/blackjack/playHand")
                                                   .param("cardDeckId",
                                                           aCardDeckId)
                                                   .param("gameId",
                                                           aGameId))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

    }

    /**
     * Perform game state invocation
     * @param aGameId String
     * @return
     * @throws Exception
     */
    protected MvcResult performGetStateInvocation(String aGameId) throws Exception {

        return this.getMockMvc()
                   .perform(MockMvcRequestBuilders.get("/blackjack/gameState")
                                                  .param("gameId",
                                                          aGameId.toString()))
                   .andExpect(MockMvcResultMatchers.status().isOk())
                   .andReturn();

    }


    /**
     * Method to set action jackson converters
     * @param aConverters HttpMessageConverter<?>[]
     */
    @Autowired
    public void setConverters(HttpMessageConverter<?>[] aConverters) {

        this.setMappingJacksonHttpMessageConverter(
                Arrays.asList(aConverters).stream().filter(
                        this.createMappingJackson2HttpMessageConverterFilter()).findAny().get());

    }

    /**
     * Answer a filter lambda for grabbing the proper covnerter
     * @return
     */
    protected Predicate<? super HttpMessageConverter<?>> createMappingJackson2HttpMessageConverterFilter() {

        return (aConverter) -> (aConverter instanceof MappingJackson2HttpMessageConverter);

    }


    /**
     * Test setup
     */
    @Before
    public void setup() throws Exception {

        this.setMockMvc(MockMvcBuilders.webAppContextSetup(this.getWebApplicationContext()).build());
    }
    /**
     * Answer my mockMvc
     *
     * @return org.springframework.test.web.servlet.MockMvc
     */
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    /**
     * Set my mockMvc
     *
     * @param mockMvc org.springframework.test.web.servlet.MockMvc
     */
    protected void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /**
     * Answer my mappingJacksonHttpMessageConverter
     *
     * @return org.springframework.http.converter.HttpMessageConverter
     */
    protected HttpMessageConverter getMappingJacksonHttpMessageConverter() {
        return mappingJacksonHttpMessageConverter;
    }

    /**
     * Set my mappingJacksonHttpMessageConverter
     *
     * @param mappingJackHttpMessageConverter org.springframework.http.converter.HttpMessageConverter
     */
    protected void setMappingJacksonHttpMessageConverter(HttpMessageConverter mappingJackHttpMessageConverter) {
        this.mappingJacksonHttpMessageConverter = mappingJackHttpMessageConverter;
    }

    /**
     * Answer my contentType
     *
     * @return org.springframework.http.MediaType
     */
    protected MediaType getContentType() {
        return contentType;
    }

    /**
     * Set my contentType
     *
     * @param contentType org.springframework.http.MediaType
     */
    protected void setContentType(MediaType contentType) {
        this.contentType = contentType;
    }

    /**
     * Answer my webApplicationContext
     *
     * @return org.springframework.web.context.WebApplicationContext
     */
    protected WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    /**
     * Set my webApplicationContext
     *
     * @param webApplicationContext org.springframework.web.context.WebApplicationContext
     */
    protected void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }
}
