package com.mycompany.pingpong;

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    static double WINDOW_WIDTH = 750;
    static double WINDOW_HEIGHT = 850;

    Label scorePlayerOne;
    Label scorePlayerTwo;
    Random random = new Random();
    Rectangle rec1;
    Rectangle rec2;
    Circle ball;
    Pane pane;
    Button playAgainButton;

    double ballX = getRandomBallPosition();
    double ballY = WINDOW_HEIGHT / 2;
    double ballSpeedX = getRandomBallSpeed(5);
    double ballSpeedY = getRandomBallSpeed(4);
    double rectangle1X = 375;
    double rectangle2X = 375;
    double frozenBallSpeedX;
    double frozenBallSpeedY;
    int scorePlayer1 = 0;
    int scorePlayer2 = 0;
    int playerScored = 0;

    @Override
    public void start(Stage stage) {

        pane = new Pane();
        pane.setBackground(Background.fill(Color.BLACK));

        ball = new Circle(ballX, ballY, 15, Color.GOLD);

        rec1 = new Rectangle(rectangle1X, WINDOW_HEIGHT - 30, 150, 15);
        rec2 = new Rectangle(rectangle2X, 15, 150, 15);

        rec1.setFill(Color.BLUE);
        rec2.setFill(Color.RED);

        scorePlayerOne = new Label("Player 1: " + scorePlayer1);
        scorePlayerOne.setTextFill(Color.WHITE);
        scorePlayerOne.setLayoutY(WINDOW_HEIGHT - 50);
        scorePlayerOne.setLayoutX(20);
        scorePlayerOne.setStyle("-fx-background-color: rgba(0, 0, 255, 0.5);");
        scorePlayerOne.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        scorePlayerTwo = new Label("Player 2: " + scorePlayer2);
        scorePlayerTwo.setTextFill(Color.WHITE);
        scorePlayerTwo.setLayoutY(20);
        scorePlayerTwo.setLayoutX(20);
        scorePlayerTwo.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5);");
        scorePlayerTwo.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        playAgainButton = new Button("Play again");
        playAgainButton.setLayoutX(WINDOW_WIDTH / 2 - 50);
        playAgainButton.setLayoutY(WINDOW_HEIGHT / 2 + 50);
        playAgainButton.setOnAction(e -> resetGame());

        playAgainButton.setVisible(false);
        pane.getChildren().add(playAgainButton);

        Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnKeyPressed(this::getKeyPressed);

        pane.getChildren().addAll(ball, rec1, rec2, scorePlayerOne, scorePlayerTwo);

        Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            play();
        }));
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    private void getKeyPressed(KeyEvent e) {

        if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
            if (rectangle1X - 20 >= 0) {
                rectangle1X -= 20;
            }
        }

        if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
            if (rectangle1X + 20 <= WINDOW_WIDTH - 150) {
                rectangle1X += 20;
            }
        }
    }

    private void play() {
        if (ballSpeedX != 0 || ballSpeedY != 0) {
            moveBall(ball, rec1, rec2);
            rec1.setX(rectangle1X);
            moveSecondRectangle();
        }
    }

    private void freezeGame() {
        frozenBallSpeedX = ballSpeedX;
        frozenBallSpeedY = ballSpeedY;

        ballSpeedX = 0;
        ballSpeedY = 0;
    }

    private void resume() {
        ballSpeedX = frozenBallSpeedX;
        ballSpeedY = frozenBallSpeedY;
    }

    private void reset() {

        switch (playerScored) {
            case 1:
                ballX = getRandomBallPosition();
                ballY = WINDOW_HEIGHT / 2;
                ballSpeedX = getRandomBallSpeed(5);
                ballSpeedY = 4;
                break;
            case 2:
                ballX = getRandomBallPosition();
                ballY = WINDOW_HEIGHT / 2;
                ballSpeedX = getRandomBallSpeed(5);
                ballSpeedY = -4;
                break;
            default:
                ballX = getRandomBallPosition();
                ballY = WINDOW_HEIGHT / 2;
                ballSpeedX = getRandomBallSpeed(5);
                ballSpeedY = getRandomBallSpeed(4);
                break;
        }

    }

    private void resetGame() {

        playAgainButton.setVisible(false);
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        scorePlayerOne.setText("Player 1: " + scorePlayer1);
        scorePlayerTwo.setText("Player 2: " + scorePlayer2);
        resume();
        reset();
        pane.getChildren().removeIf(node -> node instanceof Label);

        pane.getChildren().addAll(scorePlayerOne, scorePlayerTwo);
    }

    private void moveBall(Circle ball, Rectangle... recs) {

        ballX += ballSpeedX;
        ballY += ballSpeedY;

        if (ballX - ball.getRadius() <= 0 || ballX + ball.getRadius() >= WINDOW_WIDTH) {
            ballSpeedX = -ballSpeedX;

        }
        if (ballY - ball.getRadius() <= 0 || ballY + ball.getRadius() >= WINDOW_HEIGHT) {
            ballSpeedY = -ballSpeedY;

        }

        checkCollisionsWithRectangles(ball, recs);
        checkBallAndWallCollision();

        ball.setCenterX(ballX);
        ball.setCenterY(ballY);
    }

    private void checkCollisionsWithRectangles(Circle ball, Rectangle... rectangles) {

        for (Rectangle r : rectangles) {
            if (checkCollision(ball, r)) {
                checkCollisionAngle(ball, r);
            }
        }
    }

    private void checkBallAndWallCollision() {

        if (ballY - 15 <= 0) {
            scorePlayer1++;
            playerScored = 1;
            scorePlayerOne.setText("Player 1: " + scorePlayer1);
            if (scorePlayer1 >= 10) {
                gameOver("Player 1");
            } else {
                reset();
            }
        } else if (ballY + 15 >= WINDOW_HEIGHT) {
            scorePlayer2++;
            playerScored = 2;
            scorePlayerTwo.setText("Player 2: " + scorePlayer2);
            if (scorePlayer2 >= 10) {
                gameOver("Player 2");
            } else {
                reset();
            }
        }
    }

    private void gameOver(String winner) {

        freezeGame();

        Label winnerLabel = new Label(winner + " Wins!");
        winnerLabel.setTextFill(Color.WHITE);
        winnerLabel.setLayoutX(WINDOW_WIDTH / 2 - 50);
        winnerLabel.setLayoutY(WINDOW_HEIGHT / 2);
        winnerLabel.setStyle("-fx-font-size: 20;");
        pane.getChildren().add(winnerLabel);

        playAgainButton.setVisible(true);
    }

    private boolean checkCollision(Circle ball, Rectangle rectangle) {

        double ballLeft = ball.getCenterX() - ball.getRadius();
        double ballTop = ball.getCenterY() - ball.getRadius();
        double ballRight = ball.getCenterX() + ball.getRadius();
        double ballBottom = ball.getCenterY() + ball.getRadius();

        double recLeft = rectangle.getX();
        double recTop = rectangle.getY();
        double recRight = rectangle.getX() + rectangle.getWidth();
        double recBottom = rectangle.getY() + rectangle.getHeight();

        return ballRight > recLeft && ballLeft < recRight
                && ballBottom > recTop && ballTop < recBottom;
    }

    private void checkCollisionAngle(Circle ball, Rectangle rectangle) {

        double ballLeft = ball.getCenterX() - ball.getRadius();
        double ballTop = ball.getCenterY() - ball.getRadius();
        double ballRight = ball.getCenterX() + ball.getRadius();
        double ballBottom = ball.getCenterY() + ball.getRadius();

        double recLeft = rectangle.getX();
        double recTop = rectangle.getY();
        double recRight = rectangle.getX() + rectangle.getWidth();
        double recBottom = rectangle.getY() + rectangle.getHeight();

        double collisionX = Math.min(Math.abs(ballRight - recLeft), Math.abs(recRight - ballLeft));
        double collisionY = Math.min(Math.abs(ballBottom - recTop), Math.abs(recBottom - ballTop));

        if (collisionX < collisionY) {
            ballX = ballSpeedX > 0 ? recLeft - ball.getRadius() : recRight + ball.getRadius();
            ballSpeedX = -ballSpeedX;
        } else {
            ballY = ballSpeedY > 0 ? recTop - ball.getRadius() : recBottom + ball.getRadius();
            ballSpeedY = -ballSpeedY;
        }

    }

    private void moveSecondRectangle() {
        double targetX = ball.getCenterX() - rec2.getWidth() / 2;

        if (targetX >= 0 && targetX + rec2.getWidth() <= WINDOW_WIDTH) {
            rectangle2X = targetX;
            rec2.setX(rectangle2X);
        }
    }

    private double getRandomBallPosition() {
        return 50 + (700 - 50) * random.nextDouble();
    }

    private double getRandomBallSpeed(double d) {
        return random.nextBoolean() ? -d : d;
    }

}
