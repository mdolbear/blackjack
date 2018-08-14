package com.oracle.blackjack.persistence;

import com.oracle.blackjack.gamemodel.deck.CardDeck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardDeckMongoRepository extends MongoRepository<CardDeck, String> {

}
