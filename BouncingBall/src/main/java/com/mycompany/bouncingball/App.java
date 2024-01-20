package com.mycompany.bouncingball;

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    static double WINDOW_WIDTH = 900;
    static double WINDOW_HEIGHT = 500;

    double ballX = 50;
    double ballY = 50;
    double ballSpeedX = 3;
    double ballSpeedY = 2;

    @Override
    public void start(Stage stage) {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);

        Circle ball = new Circle(ballX, ballY, 30, Color.BLUE);

        Rectangle rec1 = new Rectangle(500, 80, 70, 30);
        Rectangle rec2 = new Rectangle(150, 150, 100, 90);
        Rectangle rec3 = new Rectangle(600, 350, 50, 100);
        Rectangle rec4 = new Rectangle(700, 70, 60, 200);
        Rectangle rec5 = new Rectangle(250, 360, 150, 50);

        rec1.setFill(Color.WHITE);
        rec2.setFill(Color.WHITE);
        rec3.setFill(Color.WHITE);
        rec4.setFill(Color.WHITE);
        rec5.setFill(Color.WHITE);

        pane.setBackground(Background.fill(Color.BLACK));

        pane.getChildren().addAll(ball, rec1, rec2, rec3, rec4, rec5);

        Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(10), t
                -> moveBall(ball, rec1, rec2, rec3, rec4, rec5)
        ));

        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        stage.setTitle("Bouncing Ball");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void moveBall(Circle ball, Rectangle... recs) {

        ballX += ballSpeedX;
        ballY += ballSpeedY;

        if (ballX - ball.getRadius() <= 0 || ballX + ball.getRadius() >= WINDOW_WIDTH) {
            ballSpeedX = -ballSpeedX;
            changeBallColor(ball);
        }
        if (ballY - ball.getRadius() <= 0 || ballY + ball.getRadius() >= WINDOW_HEIGHT) {
            ballSpeedY = -ballSpeedY;
            changeBallColor(ball);
        }

        checkCollisionsWithRectangles(ball, recs);

        ball.setCenterX(ballX);
        ball.setCenterY(ballY);
    }

    public void checkCollisionsWithRectangles(Circle ball, Rectangle... rectangles) {

        for (Rectangle r : rectangles) {
            if (checkCollision(ball, r)) {
                checkCollisionAngle(ball, r);
            }
        }
    }

    public boolean checkCollision(Circle ball, Rectangle rectangle) {

        double ballLeft = ball.getCenterX() - ball.getRadius();
        double ballTop = ball.getCenterY() - ball.getRadius();
        double ballRight = ball.getCenterX() + ball.getRadius();
        double ballBottom = ball.getCenterY() + ball.getRadius();

        double recLeftSide = rectangle.getX();
        double recTopSide = rectangle.getY();
        double recRightSide = rectangle.getX() + rectangle.getWidth();
        double recBottomSide = rectangle.getY() + rectangle.getHeight();

        return ballRight > recLeftSide && ballLeft < recRightSide
                && ballBottom > recTopSide && ballTop < recBottomSide;
    }

    public void checkCollisionAngle(Circle ball, Rectangle rectangle) {

        double ballLeft = ball.getCenterX() - ball.getRadius();
        double ballTop = ball.getCenterY() - ball.getRadius();
        double ballRight = ball.getCenterX() + ball.getRadius();
        double ballBottom = ball.getCenterY() + ball.getRadius();

        double recLeftSide = rectangle.getX();
        double recTopSide = rectangle.getY();
        double recRightSide = rectangle.getX() + rectangle.getWidth();
        double recBottomSide = rectangle.getY() + rectangle.getHeight();

        double collisionX = Math.min(Math.abs(ballRight - recLeftSide), Math.abs(recRightSide - ballLeft));
        double collisionY = Math.min(Math.abs(ballBottom - recTopSide), Math.abs(recBottomSide - ballTop));

        if (collisionX < collisionY) {
            ballX = ballSpeedX > 0 ? recLeftSide - ball.getRadius() : recRightSide + ball.getRadius();
            ballSpeedX = -ballSpeedX;
        } else {
            ballY = ballSpeedY > 0 ? recTopSide - ball.getRadius() : recBottomSide + ball.getRadius();
            ballSpeedY = -ballSpeedY;
        }

        changeBallColor(ball);
    }

    public void changeBallColor(Circle ball) {

        Random random = new Random();
        double red = random.nextDouble();
        double green = random.nextDouble();
        double blue = random.nextDouble();

        ball.setFill(new Color(red, green, blue, 1.0));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
