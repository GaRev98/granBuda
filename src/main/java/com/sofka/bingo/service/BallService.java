package com.sofka.bingo.service;

import com.sofka.bingo.domain.Ball;
import com.sofka.bingo.repository.BallRepository;
import com.sofka.bingo.service.interfaces.IBall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class BallService implements IBall {

    @Autowired
    private BallRepository ballRepository;
    @Override
    @Transactional
    public Ball createBall(Ball ball) {
        return ballRepository.save(ball);
    }

    @Override
    @Transactional
    public void outBall(Integer id) {
        ballRepository.outBall(id);
    }

    @Override
    @Transactional
    public Ball getBallById(Integer id){
        return ballRepository.getBallById(id);
    }

    @Override
    @Transactional
    public Collection<Ball> getAllBalls() {
        return ballRepository.getAllBalls();
    }
}
