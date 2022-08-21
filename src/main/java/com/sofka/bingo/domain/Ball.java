package com.sofka.bingo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "ball")
public class Ball implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idball", nullable = false)
    private Integer id;

    @Column(name = "`out`")
    private Boolean out;

    @Column(name = "`order`")
    private Timestamp order;
}