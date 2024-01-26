package com.snake;

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class App extends Application {

    // Constants for snake dimensions, distance, speed, and initial size
    final int rectangleWidth = 15;
    final int rectangleHeight = 15;
    final int distance = 20;
    final int speed = 100;
    final int initSize = 5;

    // Flags to track game state
    boolean gameOver = false;
    boolean gameStarted = false;

    // Variables to track snake movement direction
    double directionX = 0.0;
    double directionY = 0.0;

    // Components of the game
    ArrayList<Rectangle> snake;
    Circle circle;
    Label label;
    Pane pane;
    Scene scene;
    Timeline timeLine;

    @Override
    public void start(Stage stage) {
        
        //Adding background image if needed
//        Image image = new Image("file:image.jpg", 1920, 1080, false, true);
//        BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.REPEAT,
//                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
//        Background background = new Background(bi);


        // Initialize the game pane
        pane = new Pane();
        pane.setBackground(Background.fill(Color.TRANSPARENT));
//        pane.setBackground(background);

        // Initialize game elements
        drawCircle();
        snake = getNewSnake();
        createLabel();
        
        // Add elements to the pane
        pane.getChildren().addAll(circle, label);
        pane.getChildren().addAll(snake);

        // Initialize and start game timeline
        timeLine = new Timeline(new KeyFrame(Duration.millis(speed), this::move));
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        // Set up the game scene
        scene = new Scene(pane);
        scene.setOnKeyPressed(this::sceneKeyPressed);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    // Variable to store the last pressed key
    KeyCode lastKey;

    // Handle key events for game control
    private void sceneKeyPressed(KeyEvent e) {
        
        // Game initialization on the first key press
        if (!gameStarted) {
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
                return;
            }

            gameStarted = true;
            directionX = 0;
            directionY = 0;
            timeLine.play();
        }

        // Avoid opposite direction key press to prevent self-collision
        if (lastKey == KeyCode.A && (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT)) {
            return;
        }
        if (lastKey == KeyCode.D && (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT)) {
            return;
        }
        if (lastKey == KeyCode.W && (e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN)) {
            return;
        }
        if (lastKey == KeyCode.S && (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP)) {
            return;
        }
        
        // Game over check
        if (gameOver) {
            return;
        }

        // Handle key presses for snake movement and game control
        if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
            gameStarted = true;
            directionX = 0;
            directionY = -distance;
            lastKey = KeyCode.W;

        } else if (e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN) {
            gameStarted = true;
            directionX = 0;
            directionY = distance;
            lastKey = KeyCode.S;

        } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
            gameStarted = true;
            directionX = distance;
            directionY = 0;
            lastKey = KeyCode.D;

        } else if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
            gameStarted = true;
            directionX = -distance;
            directionY = 0;
            lastKey = KeyCode.A;

        } else if (e.getCode() == KeyCode.ESCAPE) {
            Platform.exit();
        } else if (e.getCode() == KeyCode.SPACE) {
            gameStarted = !gameStarted;
        }

        // Restart the game on 'N' key press
        if (e.getCode() == KeyCode.N) {
            resetGame();
        }

    }

    // Handle snake movement on each timeline tick
    private void move(ActionEvent e) {

        if (gameStarted && !gameOver) {

            double headX = snake.get(0).getX() + directionX;
            double headY = snake.get(0).getY() + directionY;

            for (int i = snake.size() - 1; i > 0; i--) {
                snake.get(i).setX(snake.get(i - 1).getX());
                snake.get(i).setY(snake.get(i - 1).getY());
            }
            snake.get(0).setX(snake.get(0).getX() + directionX);
            snake.get(0).setY(snake.get(0).getY() + directionY);

            if (headX < 0) {
                headX = Screen.getPrimary().getBounds().getWidth() - rectangleWidth;
            } else if (headX > Screen.getPrimary().getBounds().getWidth() - rectangleWidth) {
                headX = 0;
            } else if (headY < 0) {
                headY = Screen.getPrimary().getBounds().getHeight() - rectangleHeight;
            } else if (headY > Screen.getPrimary().getBounds().getHeight() - rectangleHeight) {
                headY = 0;
            }

            snake.get(0).setX(headX);
            snake.get(0).setY(headY);

            snakeAndAppleCollision();
            checkSnakeCollision();

        }

    }

    // Generate a new random position for the apple
    private void drawCircle() {

        if (circle == null) {
            circle = new Circle(200, 200, 8);
        }

        circle.setCenterX(Math.random() * Screen.getPrimary().getBounds().getWidth());
        circle.setCenterY(Math.random() * Screen.getPrimary().getBounds().getHeight());
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
    }

    // Initialize the snake with a default length and color
    private ArrayList<Rectangle> getNewSnake() {

        ArrayList<Rectangle> temp = new ArrayList<>();

        // Create rectangles representing the initial snake segments
        for (int i = 1; i < 6; i++) {
            Rectangle r = new Rectangle(i * distance + 200, 100, rectangleWidth, rectangleHeight);
            // Customize the appearance of the snake head
            r.setFill(Color.GREEN);
            temp.add(r);

        }
        temp.get(0).setFill(Color.GREENYELLOW);
        return temp;
    }

    // Create and customize the label displaying the score
    private void createLabel() {
        label = new Label("" + (snake.size() - initSize));
        label.setFont(new Font("Consolas", 50));
        label.setTextFill(Color.BLUE);
        label.setLayoutX(Screen.getPrimary().getBounds().getWidth() / 2 - label.getWidth() / 2);
        label.setLayoutY(Screen.getPrimary().getBounds().getHeight() / 2 - label.getHeight() / 2);
    }

    // Update the score label based on the snake length
    private void updateLabel() {

        label.setText("" + (snake.size() - initSize));
    }

    // Check for collisions between the snake head and the apple
    private void snakeAndAppleCollision() {

        Shape collision = Shape.intersect(snake.get(0), circle);

        if (collision.getBoundsInLocal().getWidth() != -1) {
            drawCircle();
            addSnakeRectangle();
            updateLabel();

        }

    }

    // Add a new rectangle to the snake (representing a new snake segment)
    private void addSnakeRectangle() {

        Rectangle tail = snake.get(snake.size() - 1);

        double cordX = 0;
        double cordY = 0;

         // Calculate the position of the new rectangle based on the direction of movement
        if (directionX < 0) {
            cordX = tail.getX() + distance;
            cordY = tail.getY();
        } else if (directionX > 0) {
            cordX = tail.getX() - distance;
            cordY = tail.getY();
        } else if (directionY < 0) {
            cordX = tail.getX();
            cordY = tail.getY() + distance;
        } else if (directionY > 0) {
            cordX = tail.getX();
            cordY = tail.getY() - distance;
        }

        // Create and add the new rectangle to the snake
        Rectangle newRectangle = new Rectangle(cordX, cordY, rectangleWidth, rectangleHeight);
        newRectangle.setFill(Color.GREEN);

        snake.add(newRectangle);
        pane.getChildren().add(newRectangle);

    }

    // Check for collisions between the snake head and the rest of the snake
    private void checkSnakeCollision() {

        for (int i = 1; i < snake.size(); i++) {

            Shape collision = Shape.intersect(snake.get(0), snake.get(i));

            if (collision.getBoundsInLocal().getWidth() != -1) {
                 // If collision occurs with the snake itself, trigger the game over state
                gameOver();
            }
        }

    }

    // Handle the game over state
    private void gameOver() {
        // Display game over message, set direction to 0, update flags, and adjust label position
        label.setText("GAME OVER!\nscore: " + (snake.size() - initSize));
        directionX = 0;
        directionY = 0;
        gameStarted = false;
        gameOver = true;
        label.setTextFill(Color.RED);
        label.setLayoutX(Screen.getPrimary().getBounds().getWidth() / 2 - label.getWidth() - 100);
        label.setLayoutY(Screen.getPrimary().getBounds().getHeight() / 2 - label.getHeight() / 2);

         // Allow the user to exit the game with ESC, or restart with N
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
            if (e.getCode() == KeyCode.N) {
                resetGame();
            }
        });

    }

    // Reset the game to its initial state
    private void resetGame() {
        directionX = 0;
        directionY = 0;
        pane.getChildren().removeAll(snake);
        pane.getChildren().removeAll(label, circle);
        snake = getNewSnake();
        drawCircle();
        createLabel();
        pane.getChildren().addAll(snake);
        pane.getChildren().addAll(label, circle);
        timeLine.pause();
        gameStarted = false;
        gameOver = false;
        label.setText("" + (snake.size() - initSize));
        label.setTextFill(Color.BLUE);
        scene.setOnKeyPressed(this::sceneKeyPressed);
    }

}
