package com.main.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(App.class.getResource("Calculator.fxml"));

        scene = new Scene(loader.load());
        stage.setTitle("Calculator");
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        stage.getIcons().add(icon);

        stage.setMinHeight(537);
        stage.setMinWidth(338);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
