package com.sofka.bingo.service.interfaces;

import com.sofka.bingo.domain.Ball;

import java.util.Collection;

public interface IBall {
    public Ball createBall(Ball ball);

    public void outBall(Integer id);

    public Ball getBallById(Integer id);

    public Collection<Ball> getAllBalls();
}
