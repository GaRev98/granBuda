package com.sofka.bingo.service.interfaces;

import com.sofka.bingo.domain.CardBall;

import java.util.ArrayList;
import java.util.Optional;

public interface ICardBall {
    public CardBall save(CardBall cardBall);

    public Optional<CardBall> findByCardAndBallot(String cardId, Integer ballId);

    public void markCardBall(String cardId, Integer ballId, Boolean status);

    public ArrayList<Boolean> findCardBallMarked(String cardId);
}
