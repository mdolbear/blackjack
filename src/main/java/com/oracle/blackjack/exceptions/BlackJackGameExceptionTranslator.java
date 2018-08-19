package com.oracle.blackjack.exceptions;

import com.oracle.blackjack.BlackJackGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

/**
 *
 */
public class BlackJackGameExceptionTranslator extends ResponseEntityExceptionHandler {


    //Constants
    private static final Logger logger = LoggerFactory.getLogger(BlackJackGameService.class);

    /**
     * Answer my logger
     * @return Logger
     */
    protected static Logger getLogger() {

        return logger;
    }

    /**
     * Answer a default instance
     */
    public BlackJackGameExceptionTranslator() {

        super();

    }

    /**
     * Handle IllegalStateException
     */
    @ExceptionHandler(value = {IllegalStateException.class,
                               IllegalArgumentException.class,
                               CardDeckNotFoundException.class,
                               GameNotFoundException.class})
    public ResponseEntity<Object> handleConflict(RuntimeException anException,
                                                 WebRequest aRequest) {

        String  tempMsg;

        tempMsg = anException.getMessage();
        getLogger().error(tempMsg);

        return this.handleExceptionInternal(anException,
                                            new ErrorMessage(HttpStatus.CONFLICT, tempMsg),
                                            new HttpHeaders(),
                                            HttpStatus.CONFLICT,
                                            aRequest);

    }

}
