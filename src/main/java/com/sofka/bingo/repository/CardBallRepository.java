package com.sofka.bingo.repository;

import com.sofka.bingo.domain.CardBall;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public interface CardBallRepository extends CrudRepository<CardBall, Integer> {

    @Query("select cardBall.ballIdball from CardBall cardBall where cardBall.cardIdcard = :cardId")
    public Collection<Integer> getCard(
            @Param(value = "cardId") String cardId
    );

    @Query("select cardBall from CardBall cardBall where cardBall.cardIdcard = :cardId " +
            "AND cardBall.ballIdball = :ballId")
    public Optional<CardBall> findByCardAndBallot(
            @Param(value = "cardId") String cardId,
            @Param(value = "ballId") Integer ballId
    );

    @Modifying
    @Query("update CardBall cardBall set cardBall.marked = :status where cardBall.cardIdcard = :cardId "
            + "AND cardBall.ballIdball = :ballId")
    public void markCardBall(
            @Param(value = "cardId") String cardId,
            @Param(value = "ballId") Integer ballId,
            @Param(value = "status") Boolean status
    );

    @Query("select cardBall.marked from CardBall cardBall where cardBall.cardIdcard = :cardId ")
    public ArrayList<Boolean> findCardBallMarked(
            @Param(value = "cardId") String cardId
    );
}