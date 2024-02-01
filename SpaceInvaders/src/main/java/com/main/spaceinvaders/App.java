package com.main.spaceinvaders;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    static final int WIDTH = 1200;
    static final int HEIGHT = 800;
    static final int SPEED = 10;
    static final double BULLET_RADIUS = 5;
    static final Color BULLET_COLOR = Color.RED;

    int meteorsPassedCount = 0;
    int difficultyLevel = 1;
    int score = 0;
    double meteorCount = 1;
    double meteorSpeed = 1.0;
    double shipDirectionX = 0;
    double randomValue;
    boolean gameStarted = false;
    boolean gameEnded = false;
    boolean isMuted = false;

    Pane pane;
    Scene scene;
    Timeline gameTimeline;
    Timeline bulletTimeline;
    Timeline meteorTimeline;
    Timeline explosionTimeline;
    Label label;
    Label meteorsPassedLabel;
    ImageView shipView;
    ImageView meteorView;
    ImageView explosionView;
    Circle bullet;

    MediaPlayer mediaPlayer;

    ArrayList<Circle> bullets = new ArrayList<>();
    ArrayList<ImageView> meteors = new ArrayList<>();

    Iterator<Circle> bulletIterator;
    Iterator<Circle> bulletIterator2;
    Iterator<ImageView> meteorIterator;

    @Override

    public void start(Stage stage) {

        initializePane();
        initializeShip();
        initializeTimelines();
        initalizeScoreLabel();
        initalizeMeteorsPassedLabel();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    private void initializePane() {
        Image image = new Image("file:spacebackground.jpg", 1920, 1080, false, true);
        BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(bi);

        pane = new Pane();
        pane.setBackground(background);

        scene = new Scene(pane, WIDTH, HEIGHT);
        scene.setOnKeyPressed(this::sceneKeyPressed);
        scene.setOnKeyReleased(this::sceneKeyReleased);
        scene.setOnMouseClicked(this::sceneMouseClicked);
        scene.setOnMouseMoved(this::sceneMouseMoved);

    }

    private void initializeShip() {

        shipView = new ImageView(new Image("file:ship.png"));

        shipView.setX(WIDTH / 2);
        shipView.setY(HEIGHT - 100);
        shipView.setFitWidth(50);
        shipView.setFitHeight(80);

        pane.getChildren().addAll(shipView);

    }

    private void initializeTimelines() {

        gameTimeline = new Timeline(new KeyFrame(Duration.millis(SPEED), this::move));
        gameTimeline.setCycleCount(Animation.INDEFINITE);
        gameTimeline.play();

        meteorTimeline = new Timeline(new KeyFrame(Duration.seconds(meteorSpeed), e -> {
            if (gameStarted && !gameEnded) {
                for (int i = 0; i < meteorCount; i++) {
                    createMeteors();
                }
                updateDifficulty();
            }
        }));
        meteorTimeline.setCycleCount(Animation.INDEFINITE);
        meteorTimeline.play();
    }

    private void updateDifficulty() {
        if (score > 10 && score < 30) {
            setDifficultyLevel(2);
        } else if (score >= 30 && score < 50) {
            setDifficultyLevel(3);
        } else if (score >= 50 && score < 80) {
            setDifficultyLevel(4);
        } else if (score >= 80) {
            setDifficultyLevel(5);
        }
    }

    private void setDifficultyLevel(int level) {
        if (level > difficultyLevel) {
            difficultyLevel = level;
            meteorSpeed -= 0.10;
            meteorCount += 0.1;
        }
    }

    private void initializeExplosion(double x, double y, ImageView meteor) {
        explosionView = new ImageView(new Image("file:explosion.png"));
        explosionView.setX(x);
        explosionView.setY(y);
        explosionView.setFitWidth(meteor.getFitWidth());
        explosionView.setFitHeight(meteor.getFitHeight());
        pane.getChildren().add(explosionView);

        explosionTimeline = new Timeline(new KeyFrame(Duration.seconds(0.15), e -> {
            pane.getChildren().remove(explosionView);
            explosionTimeline.stop();
        }));
        explosionTimeline.setCycleCount(1);
        explosionTimeline.setOnFinished(event -> pane.getChildren().remove(explosionView));
        explosionTimeline.play();
    }

    private void createMeteors() {

        for (int i = 0; i < meteorCount; i++) {

            randomValue = ThreadLocalRandom.current().nextDouble(30, 120);
            meteorView = new ImageView(new Image("file:meteor.png"));
            meteorView.setX(ThreadLocalRandom.current().nextDouble(0, WIDTH - randomValue));
            meteorView.setY(-30);
            meteorView.setFitHeight(randomValue);
            meteorView.setFitWidth(randomValue);

            boolean intersectsExistingMeteor = false;
            for (ImageView existingMeteor : meteors) {
                if (meteorView.getBoundsInParent().intersects(existingMeteor.getBoundsInParent())) {
                    intersectsExistingMeteor = true;
                    break;
                }
            }

            if (!intersectsExistingMeteor) {
                pane.getChildren().add(meteorView);
                meteors.add(meteorView);
            }
        }
        label.toFront();
        shipView.toFront();
        meteorsPassedLabel.toFront();
    }

    public void move(ActionEvent e) {
        System.out.println(isMuted);
        if (gameStarted && !gameEnded) {
            shipView.setX(shipView.getX() + shipDirectionX);
            shipView.setY(HEIGHT - 100);

            bulletIterator = bullets.iterator();
            while (bulletIterator.hasNext()) {
                Circle bullet = bulletIterator.next();
                bullet.setCenterY(bullet.getCenterY() - 5);

                if (bullet.getCenterY() <= 0) {
                    bulletIterator.remove();
                    pane.getChildren().remove(bullet);
                }
            }

            meteorIterator = meteors.iterator();
            while (meteorIterator.hasNext()) {
                ImageView meteor = meteorIterator.next();
                meteor.setY(meteor.getY() + 1);

                if (meteor.getY() > HEIGHT) {
                    meteorsPassedCount++;
                    meteorIterator.remove();
                    pane.getChildren().remove(meteor);
                    updateMeteorsPassedLabel();
                }

                if (meteorsPassedCount >= 5) {
                    gameOver();
                }

                if (shipView.getBoundsInParent().intersects(meteor.getBoundsInParent())) {
                    gameOver();
                }

                bulletIterator2 = bullets.iterator();
                while (bulletIterator2.hasNext()) {
                    Circle bullet = bulletIterator2.next();
                    if (bullet.getBoundsInParent().intersects(meteor.getBoundsInParent())) {
                        meteorIterator.remove();
                        pane.getChildren().remove(meteor);
                        bulletIterator2.remove();
                        pane.getChildren().remove(bullet);
                        explode(meteor.getX(), meteor.getY(), meteor);
                    }
                }
            }
        }
    }

    private void explode(double x, double y, ImageView meteor) {
        if (explosionTimeline != null && explosionTimeline.getStatus() == Animation.Status.RUNNING) {
            return;
        }
        playSound("explosion.wav");
        initializeExplosion(x, y, meteor);
        score++;
        updateScoreLabel();
    }

    private void sceneMouseClicked(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            fire();
            gameStarted = true;
        }
    }

    private void sceneMouseMoved(MouseEvent e) {
        if (!gameEnded) {
            double mouseX = e.getX();
            shipView.setX(mouseX - shipView.getFitWidth() / 2);
            gameStarted = true;
        }
    }

    private void sceneKeyPressed(KeyEvent e) {

        if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
            shipDirectionX = -5;
            gameStarted = true;
        } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
            shipDirectionX = 5;
            gameStarted = true;
        } else if (e.getCode() == KeyCode.R) {
            resetGame();
        } else if (e.getCode() == KeyCode.M) {
            toggleMute();
        } else if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.F) {
            gameStarted = true;
            fire();
        } else if (e.getCode() == KeyCode.ESCAPE) {
            Platform.exit();
        }
    }

    private void sceneKeyReleased(KeyEvent e) {
        if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
            shipDirectionX = 0;
        }
    }

    private void fire() {
        if (gameStarted && !gameEnded) {
            playSound("lasersound.wav");
            bullet = new Circle(shipView.getX() + (shipView.getFitWidth() / 2),
                    shipView.getY() - BULLET_RADIUS, BULLET_RADIUS, BULLET_COLOR);

            pane.getChildren().addAll(bullet);
            bullets.add(bullet);

            if (bulletTimeline == null || bulletTimeline.getStatus() == Animation.Status.STOPPED) {
                bulletTimeline = new Timeline(new KeyFrame(Duration.millis(10), (t) -> {
                    for (int i = 0; i < bullets.size(); i++) {
                        Circle currentBullet = bullets.get(i);
                        currentBullet.setCenterY(currentBullet.getCenterY() - 5);

                        if (currentBullet.getCenterY() <= 0) {
                            bulletTimeline.stop();
                            pane.getChildren().remove(currentBullet);
                            bullets.remove(currentBullet);
                        }
                    }
                }));
                bulletTimeline.setCycleCount(Animation.INDEFINITE);
                bulletTimeline.play();
            }
        }
    }

    private void gameOver() {
        gameEnded = true;
        gameStarted = false;
        playSound("gameover.wav");
        meteorsPassedLabel.setVisible(false);
        label.setText("GAME OVER\nSCORE: " + score);
        label.toFront();
        label.setFont(new Font("Consolas", 50));
        label.setTextFill(Color.RED);
        label.setLayoutX(WIDTH / 2 - 130);
        label.setLayoutY(HEIGHT / 2 - 70);
        stopTimelines();
    }

    private void initalizeScoreLabel() {

        label = new Label("METEORS DESTROYED: " + score);
        label.setFont(new Font("Consolas", 25));
        label.setTextFill(Color.BLUE);
        label.setLayoutX(10);
        label.setLayoutY(10);
        pane.getChildren().add(label);

    }

    private void initalizeMeteorsPassedLabel() {

        meteorsPassedLabel = new Label("METEORS PASSED: " + meteorsPassedCount);
        meteorsPassedLabel.setFont(new Font("Consolas", 25));
        meteorsPassedLabel.setTextFill(Color.BLUE);
        meteorsPassedLabel.setLayoutX(900);
        meteorsPassedLabel.setLayoutY(10);
        pane.getChildren().add(meteorsPassedLabel);

    }

    private void updateMeteorsPassedLabel() {
        meteorsPassedLabel.setText("METEORS PASSED: " + meteorsPassedCount);
        meteorsPassedLabel.setFont(new Font("Consolas", 25));
        meteorsPassedLabel.setTextFill(Color.BLUE);
        meteorsPassedLabel.setLayoutX(900);
        meteorsPassedLabel.setLayoutY(10);
    }

    private void updateScoreLabel() {
        label.setText("METEORS DESTROYED: " + score);
        label.setFont(new Font("Consolas", 25));
        label.setTextFill(Color.BLUE);
        label.setLayoutX(10);
        label.setLayoutY(10);
    }

    private void stopTimelines() {
        if (bulletTimeline != null) {
            bulletTimeline.stop();
        }

        if (gameTimeline != null) {
            gameTimeline.stop();
        }

        if (meteorTimeline != null) {
            meteorTimeline.stop();
        }
        if (explosionTimeline != null) {
            explosionTimeline.stop();
        }
    }

    private void playSound(String soundTitle) {

        if (!isMuted) {
            File soundFile = new File(soundTitle);
            String uri = soundFile.toURI().toString();

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            Media sound = new Media(uri);
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }
    }

    private void toggleMute() {
        isMuted = !isMuted;
        if (isMuted) {
            stopSound("lasersound.wav");
            stopSound("explosion.wav");
            stopSound("gameover.wav");
        }
    }

    private void stopSound(String soundTitle) {
        File soundFile = new File(soundTitle);
        String soundUri = soundFile.toURI().toString();
        Media sound = new Media(soundUri);
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.stop();
    }

    private void resetGame() {
        gameStarted = false;
        gameEnded = false;
        meteorsPassedCount = 0;
        difficultyLevel = 1;
        score = 0;
        meteorCount = 1;
        meteorSpeed = 1.0;
        shipDirectionX = 0;
        bullets.clear();

        for (ImageView meteor : meteors) {
            pane.getChildren().remove(meteor);
        }
        meteors.clear();

        updateScoreLabel();
        updateMeteorsPassedLabel();
        meteorsPassedLabel.setVisible(true);

        shipView.setX(WIDTH / 2);
        shipView.setY(HEIGHT - 100);

        stopTimelines();
        initializeTimelines();
    }

}
