package com.sofka.bingo.service;

import com.sofka.bingo.domain.Card;
import com.sofka.bingo.repository.CardRepository;
import com.sofka.bingo.service.interfaces.IGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class GameService implements IGame {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Boolean gameStarted() {
        Collection<Card> cards = cardRepository.getAllCards();
        if(cards.size() > 0) return true;
        else return false;
    }

    @Transactional
    public String getAdmin() {
        return cardRepository.getAdmin();
    }

    @Transactional
    public void setWinner(String id) { cardRepository.setWinner(id);}

    @Transactional
    public Optional<String> getWinner() {
        return cardRepository.getWinner();
    }
}
