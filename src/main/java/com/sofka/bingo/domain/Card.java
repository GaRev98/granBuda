package com.sofka.bingo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "card")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idcard", nullable = false, length = 24)
    private String id;

}