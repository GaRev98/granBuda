package com.sofka.bingo.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "card_ball")
public class CardBall implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcard_ball", nullable = false)
    private Integer id;

    @Column(name = "card_idcard", nullable = false)
    private String cardIdcard;

    @Column(name = "ball_idball", nullable = false)
    private Integer ballIdball;

    @Column(name = "marked")
    private Boolean marked;

}