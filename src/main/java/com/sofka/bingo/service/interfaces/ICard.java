package com.sofka.bingo.service.interfaces;

import com.sofka.bingo.domain.Card;

import java.util.Collection;

public interface ICard {
    public Card createCard(Card card);

    public Collection<Integer> getCard(String cardId);
}
