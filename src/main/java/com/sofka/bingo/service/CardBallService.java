package com.sofka.bingo.service;

import com.sofka.bingo.domain.CardBall;
import com.sofka.bingo.repository.CardBallRepository;
import com.sofka.bingo.service.interfaces.ICardBall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CardBallService implements ICardBall {

    @Autowired
    private CardBallRepository cardBallRepository;

    @Override
    @Transactional
    public CardBall save(CardBall cardBall) {
        return cardBallRepository.save(cardBall);
    }

    @Override
    @Transactional
    public Optional<CardBall> findByCardAndBallot(String cardId, Integer ballId) {
        return cardBallRepository.findByCardAndBallot(cardId, ballId);
    }

    @Override
    @Transactional
    public void markCardBall(String cardId, Integer ballId, Boolean status) {
        cardBallRepository.markCardBall(cardId, ballId, status);
    }

    @Override
    @Transactional
    public ArrayList<Boolean> findCardBallMarked(String cardId) {
        return cardBallRepository.findCardBallMarked(cardId);
    }


}
