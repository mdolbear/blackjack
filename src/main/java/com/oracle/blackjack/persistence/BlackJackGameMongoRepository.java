package com.oracle.blackjack.persistence;

import com.oracle.blackjack.gamemodel.BlackJackGame;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlackJackGameMongoRepository  extends MongoRepository<BlackJackGame, String> {

}
