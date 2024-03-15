package com.main.inputwindowtransferapp;

import com.main.inputwindowtransferapp.helpers.Statics;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LockedInputController implements Initializable {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtGender;

    InputFormController ifc;

    static List<User> userList = new ArrayList<>();
    int currentUserIndex = 0;
    User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadUsersFromFile();
        if (!userList.isEmpty()) {
            displayCurrentUser();
        }

    }

    @FXML
    private void deleteClicked(ActionEvent event) {
        if (!userList.isEmpty()) {
            userList.remove(currentUser);
            if (!userList.isEmpty()) {
                currentUserIndex = Math.min(currentUserIndex, userList.size() - 1);
                displayCurrentUser();
            } else {
                clearTextFields();
            }
            saveUsersToFile();
        } else {
            Statics.showAlert("User list is empty!");
        }
    }

    private void clearTextFields() {
        txtFirstName.clear();
        txtLastName.clear();
        txtAge.clear();
        txtGender.clear();
    }

    @FXML
    private void rotateLeftClicked(ActionEvent event) {
        if (userList.size() > 1) { 
            currentUserIndex = (currentUserIndex - 1 + userList.size()) % userList.size();
            if (currentUserIndex == userList.size() - 1) { 
                currentUserIndex = 0;
            }
            displayCurrentUser();
        }
    }

    @FXML
    private void rotateRightClicked(ActionEvent event) {
        if (userList.size() > 1) { 
            currentUserIndex = (currentUserIndex + 1) % userList.size();
            if (currentUserIndex == 0) { 
                currentUserIndex = userList.size() - 1;
            }
            displayCurrentUser();
        }
    }

    @FXML
    private void insertClicked(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("inputForm.fxml"));
            AnchorPane ap = loader.load();
            ifc = loader.getController();
            ifc.setLockedInputController(this);
            Scene scene = new Scene(ap);
            Stage stage = new Stage();
            stage.setX(300);
            stage.setY(500);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void displayCurrentUser() {
        Platform.runLater(() -> {
            currentUser = userList.get(currentUserIndex);
            txtFirstName.setText(currentUser.getFirstName());
            txtLastName.setText(currentUser.getLastName());
            txtAge.setText(String.valueOf(currentUser.getAge()));
            txtGender.setText(currentUser.getGender());
        });

    }

    public void displayCurrentUser(String firstname, String lastname, int age, String gender) {
        Platform.runLater(() -> {
            currentUser = userList.get(currentUserIndex);
            txtFirstName.setText(currentUser.getFirstName());
            txtLastName.setText(currentUser.getLastName());
            txtAge.setText(String.valueOf(currentUser.getAge()));
            txtGender.setText(currentUser.getGender());
        });

    }

    private void loadUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("userdata.ser"))) {
            userList = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            userList = new ArrayList<>();
        }
    }

    public static void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("userdata.ser"))) {
            oos.writeObject(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentUserIndex(int index) {
        this.currentUserIndex = index;
    }
}
