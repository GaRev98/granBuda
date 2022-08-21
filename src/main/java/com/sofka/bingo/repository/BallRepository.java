package com.sofka.bingo.repository;

import com.sofka.bingo.domain.Ball;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface BallRepository extends CrudRepository<Ball, Integer> {

    @Modifying
    @Query("update Ball ball set ball.out = true, ball.order = current_timestamp where ball.id = :id")
    public void outBall(
            @Param(value = "id") Integer id
    );

    @Query("SELECT ball FROM Ball ball where ball.id = :id")
    public Ball getBallById(
            @Param(value = "id") Integer id
    );

    @Query("SELECT ball FROM Ball ball where ball.out = true order by ball.order")
    public Collection<Ball> getAllBalls();

}