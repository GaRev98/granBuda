package com.sofka.bingo.repository;

import com.sofka.bingo.domain.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface CardRepository extends CrudRepository<Card, Integer> {

    @Query("select card from Card card")
    public Collection<Card> getAllCards();

    @Query("select cardBall.ballIdball from CardBall cardBall where cardBall.cardIdcard = :cardId")
    public Collection<Integer> getCard(
            @Param(value = "cardId") String cardId
    );
}