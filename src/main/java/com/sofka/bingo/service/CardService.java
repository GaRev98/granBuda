package com.sofka.bingo.service;

import com.sofka.bingo.domain.Card;
import com.sofka.bingo.repository.CardRepository;
import com.sofka.bingo.service.interfaces.ICard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CardService implements ICard {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Collection<Integer> getCard(String cardId) {
        return cardRepository.getCard(cardId);
    }

}
