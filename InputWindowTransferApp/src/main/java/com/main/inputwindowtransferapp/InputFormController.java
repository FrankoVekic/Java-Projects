package com.main.inputwindowtransferapp;

import com.main.inputwindowtransferapp.helpers.Statics;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class InputFormController implements Initializable {

    @FXML
    private TextField inputFirstName;
    @FXML
    private TextField inputLastName;
    @FXML
    private TextField inputAge;
    @FXML
    private ComboBox inputGender;

    List<String> genders = Arrays.asList("MALE", "FEMALE", "OTHER");

    LockedInputController lic;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        inputGender.setPromptText("Select gender");
        inputGender.getItems().addAll(genders);
    }

    @FXML
    private void submitClicked(ActionEvent event) {

        if (validateInput()) {
            String firstName = inputFirstName.getText();
            String lastName = inputLastName.getText();
            int age = Integer.parseInt(inputAge.getText());
            String gender = (String) inputGender.getValue();

            User newUser = new User(firstName, lastName, age, gender);

            LockedInputController.userList.add(newUser);
            LockedInputController.saveUsersToFile();

            inputFirstName.getScene().getWindow().hide();

            int newIndex = LockedInputController.userList.indexOf(newUser);
            if (newIndex != -1) {
                lic.displayCurrentUser();
                lic.setCurrentUserIndex(newIndex);
            }
        }

    }

    @FXML
    private void cancelClicked(ActionEvent event) {
        inputFirstName.getScene().getWindow().hide();
    }

    private boolean validateInput() {
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String ageText = inputAge.getText();
        String gender = (String) inputGender.getValue();

        if (firstName.trim().isEmpty()) {
            Statics.showAlert("First name can't be empty!");
            return false;
        }

        if (lastName.trim().isEmpty()) {
            Statics.showAlert("Last name can't be empty!");
            return false;
        }

        if (ageText.trim().isEmpty()) {
            Statics.showAlert("Age can't be empty!");
            return false;
        }

        try {
            int age = Integer.parseInt(ageText);

            if (age < 0) {
                Statics.showAlert("Age must be positive number!");
                return false;
            }
        } catch (NumberFormatException e) {
            Statics.showAlert("Age must be a number!");
            return false;
        }

        if (gender == null) {
            Statics.showAlert("You must select a gender!");
            return false;
        }

        return true;
    }

    public void setLockedInputController(LockedInputController lic) {
        this.lic = lic;
    }
}
