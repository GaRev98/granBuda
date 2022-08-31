package com.sofka.bingo.controller;

import com.sofka.bingo.domain.Ball;
import com.sofka.bingo.domain.Card;
import com.sofka.bingo.domain.CardBall;
import com.sofka.bingo.service.BallService;
import com.sofka.bingo.service.CardBallService;
import com.sofka.bingo.service.CardService;
import com.sofka.bingo.service.GameService;
import com.sofka.bingo.utility.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@CrossOrigin
public class BingoController {

    @Autowired
    private CardService cardService;

    @Autowired
    private BallService ballService;

    @Autowired
    private CardBallService cardBallService;

    @Autowired
    private GameService gameService;

    private Response response = new Response();

    private HttpStatus httpStatus = HttpStatus.OK;

    @PostMapping(path = "/createCard")
    public ResponseEntity<Response> createCard(@RequestBody Card card) {
        response.restart();
        try {
            log.info("Tarjeta a crear: {}", card);
            response.data = cardService.createCard(card);
            ArrayList<Integer> na = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                while (na.size() != i * 5) {
                    CardBall cardBall = new CardBall();
                    int n = new Random().nextInt(15) + (15 * (i - 1));
                    if (!na.contains(n)) {
                        cardBall.setCardIdcard(card.getId());
                        cardBall.setBallIdball(n + 1);
                        cardBall.setMarked(false);
                        cardBallService.save(cardBall);
                        na.add(n);
                    }
                }
            }
            httpStatus = HttpStatus.CREATED;
            response.data = cardService.getCard(card.getId());
            response.message = "Tarjeta creada";
        } catch (Exception exception) {
            log.info(String.valueOf(exception));
            response.message = "Error";
        }
        return new ResponseEntity(response, httpStatus);
    }

    @PostMapping(path = "/generateBalls")
    public ResponseEntity<Response> generateBalls() {
        response.restart();
        try {
            log.info("Generar balotas");
            for (int i = 0; i < 75; i++) {
                Ball ball = new Ball();
                ball.setId(i + 1);
                ball.setOut(false);
                ballService.createBall(ball);
            }
            httpStatus = HttpStatus.CREATED;
            response.message = "Balotas generadas";
        } catch (Exception exception) {
            log.info(String.valueOf(exception));
            response.message = "Error";
        }
        return new ResponseEntity(response, httpStatus);
    }

    @GetMapping(path = "/game")
    public ResponseEntity<Response> gameStarted() {
        response.restart();
        try {
            log.info("Informacion del juego");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("started", gameService.gameStarted());
            map.put("admin", gameService.getAdmin());
            map.put("cards", gameService.allCards());
            response.data = map;
            httpStatus = HttpStatus.OK;
            response.message = "Informacion del juego";
        } catch (Exception exception) {
            log.info(String.valueOf(exception));
            response.message = "Error";
        }
        return new ResponseEntity(response, httpStatus);
    }

    @PostMapping(path = "/getCard")
    public ResponseEntity<Response> getCard(@RequestBody Card card) {
        response.restart();
        try {
            log.info("Obteniendo tarjeta segun id");
            response.data = cardService.getCard(card.getId());
            httpStatus = HttpStatus.CREATED;
            response.message = "Array";
        } catch (Exception exception) {
            log.info(String.valueOf(exception));
            response.message = "Error";
        }
        return new ResponseEntity(response, httpStatus);
    }

    @PutMapping(path = "/outBall")
    public ResponseEntity<Response> outBall() {
        response.restart();
        try {
            log.info("Obteniendo tarjeta segun id");
            Integer id = new Random().nextInt(75) + 1;
            Ball ball = ballService.getBallById(id);
            while (ball.getOut()) {
                id = new Random().nextInt(75) + 1;
                ball = ballService.getBallById(id);
            }
            log.info(String.valueOf(!ball.getOut()));
            if (!ball.getOut()) ballService.outBall(id);
            log.info(ball.toString());
            /*ballService.outBall(id);*/
            response.data = id;
            httpStatus = HttpStatus.CREATED;
            response.message = "Saco bola " + id;
        } catch (Exception exception) {
            log.info(String.valueOf(exception));
            response.message = "Error";
        }
        return new ResponseEntity(response, httpStatus);
    }

    @GetMapping(path = "/allBalls")
    public ResponseEntity<Response> allBalls() {
        response.restart();
        try {
            log.info("Comprobando si el juego esta iniciado");
            response.data = ballService.getAllBalls();
            httpStatus = HttpStatus.CREATED;
            response.message = "Boleano";
        } catch (Exception exception) {
            log.info(String.valueOf(exception));
            response.message = "Error";
        }
        return new ResponseEntity(response, httpStatus);
    }

    @PostMapping(path = "/allBalls")
    public ResponseEntity<Response> allBalls(@RequestBody Card card) {
        response.restart();
        try {
            log.info("Comprobando si el juego esta iniciado");
            response.data = ballService.getAllBalls();
            httpStatus = HttpStatus.CREATED;
            response.message = "Boleano";
        } catch (Exception exception) {
            log.info(String.valueOf(exception));
            response.message = "Error";
        }
        return new ResponseEntity(response, httpStatus);
    }

    @PutMapping(path = "/markCardBall/{cardId}/{ballId}/{status}")
    public ResponseEntity<Response> balCardMarked(@RequestBody @PathVariable("cardId") String cardId,
                                                  @RequestBody @PathVariable("ballId") Integer ballId,
                                                  @RequestBody @PathVariable("status") Integer status) {
        response.restart();
        try {
            Ball ball = ballService.getBallById(ballId);
            if (ball.getOut()) {
                var cardBall = cardBallService.findByCardAndBallot(cardId, ballId);
                if (cardBall.isPresent()) cardBallService.markCardBall(cardId, ballId, status == 1 ? true : false);
                response.data = status;
            } else response.data = false;
            httpStatus = HttpStatus.CREATED;
            response.message = "Tarjeta marcada";
        } catch (Exception exception) {
            log.info(String.valueOf(exception));
            response.message = "Error";
        }
        return new ResponseEntity(response, httpStatus);
    }

    @GetMapping(path = "/checkWinner/{cardId}")
    public ResponseEntity<Response> checkWinner(@RequestBody @PathVariable("cardId") String cardId) {
        response.restart();
        try {
            log.info("Comprobando ganador");
            ArrayList<Boolean> marked = cardBallService.findCardBallMarked(cardId);
            Boolean status = false;
            if (marked.get(0) && marked.get(4) && marked.get(20) && marked.get(24)) status = true;
            else if (marked.get(0) && marked.get(6) && marked.get(18) && marked.get(24)) status = true;
            else if (marked.get(4) && marked.get(8) && marked.get(16) && marked.get(20)) status = true;
            else for (int i = 0; i < 5; i++) {
                    //Horizontales
                    if (marked.get(i)
                            && marked.get(i + 5)
                            && (i != 2 ? marked.get(i + 10) : true)
                            && marked.get(i + 15)
                            && marked.get(i + 20)) {
                        status = true;
                    }
                    //Verticales
                    if (marked.get(i * 5)
                            && marked.get((i * 5) + 1)
                            && (i != 2 ? marked.get((i * 5) + 2) : true)
                            && marked.get((i * 5) + 3)
                            && marked.get((i * 5) + 4)) {
                        status = true;
                    }
                }
            if (status) gameService.setWinner(cardId);
            response.data = status;
            httpStatus = HttpStatus.CREATED;
            response.message = "Boleano";
        } catch (Exception exception) {
            log.info(String.valueOf(exception));
            response.message = String.valueOf(exception);
            response.error = true;
        }
        return new ResponseEntity(response, httpStatus);
    }

    @GetMapping(path = "/getWinner")
    public ResponseEntity<Response> getAdmin() {
        response.restart();
        try {
            log.info("Comprobando si el juego esta iniciado");
            response.data = gameService.getWinner();
            httpStatus = HttpStatus.CREATED;
            response.message = "Id Admin";
        } catch (Exception exception) {
            log.info(String.valueOf(exception));
            response.message = "Error";
        }
        return new ResponseEntity(response, httpStatus);
    }

    @GetMapping(path = "/prueba")
    public ResponseEntity<Object> prueba() {
        var prueba = "Prueba";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", prueba);
        return ResponseEntity.ok(map);
    }
}