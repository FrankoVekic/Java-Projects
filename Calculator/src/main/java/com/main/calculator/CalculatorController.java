package com.main.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CalculatorController implements Initializable {

    @FXML
    private TextField text;
    @FXML
    private Label memory;
    @FXML
    private Button mcButton;
    @FXML
    private Button mrButton;
    @FXML
    private Label numbersClickedLabel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button mPlusBtn;
    @FXML
    private Button mMinusBtn;
    @FXML
    private Button msBtn;
    @FXML
    private Button percentBtn;
    @FXML
    private Button oneOverBtn;
    @FXML
    private Button squareBtn;
    @FXML
    private Button divideBtn;
    @FXML
    private Button rootBtn;
    @FXML
    private Button multiplyBtn;
    @FXML
    private Button plusBtn;
    @FXML
    private Button minusBtn;
    @FXML
    private Button dotBtn;
    @FXML
    private Button negationBtn;

    ArrayList<Button> buttons = new ArrayList<>();
    BigDecimal number1 = new BigDecimal("0"), number2 = new BigDecimal("0"), mem, savedNumber1 = null;

    int counter = 0;
    boolean numberOneIsSet = false;
    boolean clearText;
    boolean equalsClicked = false;

    static Operation lastOperation;

    enum Operation {
        PLUS, MINUS, MULTIPLY, DEVIDE, PERCENT, EQUAL;

    };

    Operation op;

    @FXML
    private void mcButtonClicked(ActionEvent event) {
        mem = new BigDecimal("0");

        updateText(memory, "0");

        mcButton.setDisable(true);
        mrButton.setDisable(true);
        clearText = true;

    }

    @FXML
    private void mrButtonClicked(ActionEvent event) {
        updateText(text, mem.toString());
        mcButton.setDisable(false);
        mrButton.setDisable(false);
        clearText = true;
    }

    @FXML
    private void mPlusClicked(ActionEvent event) {
        number1 = new BigDecimal(text.getText());
        mem = mem.add(number1);
        Platform.runLater(() -> {
            memory.setText(mem.toString());
            mcButton.setDisable(false);
            mrButton.setDisable(false);
        });
        clearText = true;
    }

    @FXML
    private void mMinusClicked(ActionEvent event) {
        number1 = new BigDecimal(text.getText());
        mem = mem.subtract(number1);
        updateText(memory, mem.toString());

        if (mem.compareTo(BigDecimal.ZERO) == 0) {
            mcButton.setDisable(true);
            mrButton.setDisable(true);
        }
        clearText = true;
    }

    @FXML
    private void msClicked(ActionEvent event) {

        mem = new BigDecimal(text.getText());
        updateText(memory, mem.toString());
        mcButton.setDisable(false);
        mrButton.setDisable(false);
        clearText = true;

    }

    @FXML
    private void clearClicked(ActionEvent event) {

        updateText(text, "0");
        number1 = new BigDecimal("0");
        number2 = new BigDecimal("0");
        numbersClickedLabel.setText("");
        counter = 0;
        savedNumber1 = new BigDecimal("0");
        numberOneIsSet = false;
        buttons.forEach((t) -> {
            t.setDisable(false);
            if (t == mrButton || t == mcButton) {
                if (memory.getText().equals("0")) {
                    t.setDisable(true);
                } else {
                    t.setDisable(false);
                }
            }
        });
    }

    @FXML
    private void removeLastButtonClicked(ActionEvent event) {
        Platform.runLater(() -> {
            if (!text.getText().isEmpty()) {
                if (text.getText().equals("Can't divide by 0")) {
                    text.setText("0");
                    buttons.forEach((t) -> {
                        t.setDisable(false);
                        if (t == mrButton || t == mcButton) {
                            if (memory.getText().equals("0")) {
                                t.setDisable(true);
                            } else {
                                t.setDisable(false);
                            }
                        }
                    });
                } else {
                    String newText = text.getText().substring(0, text.getText().length() - 1);
                    if (newText.isEmpty()) {
                        text.setText("0");
                    } else {
                        text.setText(newText);
                    }
                }
            }
        });
    }

    @FXML
    private void divideButtonClicked(ActionEvent event) {
        Platform.runLater(() -> {
            number1 = new BigDecimal(text.getText());
            numberOneIsSet = true;
            clearText = true;
            op = Operation.DEVIDE;
            numbersClickedLabel.setText(number1.toString() + " ÷ ");
            lastOperation = Operation.DEVIDE;
        });
    }

    @FXML
    private void multiplyButtonClicked(ActionEvent event) {
        Platform.runLater(() -> {
            number1 = new BigDecimal(text.getText());
            numberOneIsSet = true;
            clearText = true;
            numbersClickedLabel.setText(number1.toString() + " × ");
            op = Operation.MULTIPLY;
            lastOperation = Operation.MULTIPLY;
        });
    }

    @FXML
    private void plusButtonClicked(ActionEvent event) {

        Platform.runLater(() -> {
            number1 = new BigDecimal(text.getText());
            numberOneIsSet = true;
            clearText = true;
            numbersClickedLabel.setText(number1.toString() + " + ");
            op = Operation.PLUS;
            lastOperation = Operation.PLUS;
        });

    }

    @FXML
    private void minusButtonClicked(ActionEvent event) {

        Platform.runLater(() -> {

            number1 = new BigDecimal(text.getText());
            numberOneIsSet = true;
            clearText = true;
            numbersClickedLabel.setText(number1.toString() + " - ");
            op = Operation.MINUS;
            lastOperation = Operation.MINUS;
        });

    }

    @FXML
    private void equalsButtonClicked(ActionEvent event) {

        Platform.runLater(() -> {

            if (number1.compareTo(BigDecimal.ZERO) <= 0 && text.getText().equals("0") && number2.compareTo(BigDecimal.ZERO) <= 0) {
                numbersClickedLabel.setText("0 = ");
                return;
            }

            if (op == null) {
                number1 = new BigDecimal(text.getText());
                numbersClickedLabel.setText(number1.toString() + " = ");
                number1 = new BigDecimal("0");
                return;
            }

            if (numberOneIsSet && !equalsClicked) {
                number2 = new BigDecimal(text.getText());
            }

            BigDecimal result = BigDecimal.ZERO;
            switch (op) {

                case MINUS:
                    result = number1.subtract(number2);
                    numbersClickedLabel.setText(number1.toString() + " - " + number2 + " = ");
                    lastOperation = Operation.PERCENT;

                    break;
                case PLUS:
                    result = number1.add(number2);
                    numbersClickedLabel.setText(number1.toString() + " + " + number2 + " = ");
                    lastOperation = Operation.PERCENT;

                    break;
                case MULTIPLY:
                    result = number1.multiply(number2);
                    numbersClickedLabel.setText(number1.toString() + " × " + number2 + " = ");
                    lastOperation = Operation.PERCENT;
                    break;
                case DEVIDE:
                    if (text.getText().equals("0")) {
                        text.setText("Can't divide by 0");
                        clearText = true;
                        buttons.forEach((t) -> {
                            t.setDisable(true);
                        });
                        lastOperation = Operation.PERCENT;
                        return;
                    }
                    result = number1.divide(number2, MathContext.DECIMAL128);
                    numbersClickedLabel.setText(number1.toString() + " ÷ " + number2 + " = ");
                    break;
                case PERCENT:
                    if (number1.compareTo(BigDecimal.ZERO) > 0
                            && number2.compareTo(BigDecimal.ZERO) > 0
                            && lastOperation != Operation.PERCENT) {

                        numbersClickedLabel.setText(number1.toString() + getOperation(lastOperation) + number2 + " = ");
                        clearText = true;
                        return;
                    }

                    result = number1.divide(new BigDecimal("100"), MathContext.DECIMAL128).multiply(number2).stripTrailingZeros();
                    numbersClickedLabel.setText(number1.toString() + "% " + " × " + number2 + " = ");
                    clearText = true;

                    break;

            }

            number1 = result;
            savedNumber1 = null;
            counter = 0;
            equalsClicked = true;

            updateText(text, result.toPlainString());

        });
    }

    @FXML
    private void percentClicked(ActionEvent event) {
        equalsClicked = false;

        Platform.runLater(() -> {

            if (new BigDecimal(text.getText()).compareTo(BigDecimal.ZERO) <= 0) {
                numbersClickedLabel.setText("0");
                return;
            }

            if (numberOneIsSet) {
                number2 = new BigDecimal(text.getText());
                updateText(text, number1.divide(new BigDecimal("100"), MathContext.DECIMAL128).multiply(number2).toString());
                numbersClickedLabel.setText(number1.toString() + getOperation(op) + number1.divide(new BigDecimal("100"), MathContext.DECIMAL128).multiply(number2).toString());
                op = lastOperation == null ? Operation.PERCENT : lastOperation;
                number2 = number1.divide(new BigDecimal("100"), MathContext.DECIMAL128).multiply(number2);
                clearText = true;
                return;
            }

            op = Operation.PERCENT;
            lastOperation = Operation.PERCENT;
            number1 = new BigDecimal(text.getText());
            numbersClickedLabel.setText(text.getText() + "% ");
            clearText = true;
            numberOneIsSet = true;
        });

    }

    @FXML
    private void oneOverClicked(ActionEvent event) {

        equalsClicked = false;

        try {
            if (new BigDecimal(text.getText()).compareTo(BigDecimal.ZERO) <= 0 || text.getText().equals("Can't divide by 0")) {
                text.setText("Can't divide by 0");
                buttons.forEach((t) -> {
                    t.setDisable(true);
                });
                clearText = true;
                return;
            }
        } catch (NumberFormatException e) {
            text.setText("Can't divide by 0");
            clearText = true;
            return;
        }

        savedNumber1 = savedNumber1 == null || savedNumber1.compareTo(BigDecimal.ZERO) <= 0
                ? new BigDecimal(text.getText())
                : savedNumber1;

        number1 = new BigDecimal(text.getText());
        BigDecimal one = new BigDecimal("1");
        String temp = one.divide(number1, MathContext.DECIMAL64).toString();
        updateText(text, temp);
        clearText = true;
        counter++;
        numbersClickedLabel.setText(getLabelTextOneOverClicked());
    }

    @FXML
    private void squaredClicked(ActionEvent event) {

        Platform.runLater(() -> {
            equalsClicked = false;
            if (new BigDecimal(text.getText()).compareTo(BigDecimal.ZERO) <= 0) {
                counter++;
                savedNumber1 = new BigDecimal("0");
                numbersClickedLabel.setText(getLabelTextSquaredClicked());
                return;
            }

            savedNumber1 = savedNumber1 == null || savedNumber1.compareTo(BigDecimal.ZERO) <= 0
                    ? new BigDecimal(text.getText())
                    : savedNumber1;

            number1 = new BigDecimal(text.getText());
            updateText(text, number1.pow(2).toString());
            number1 = new BigDecimal(text.getText());
            counter++;
            numbersClickedLabel.setText(getLabelTextSquaredClicked());
        });

    }

    private String getLabelTextOneOverClicked() {
        if (counter <= 0 || savedNumber1 == null) {
            return "";
        }

        StringBuilder labelText = new StringBuilder("1/(");

        for (int i = 1; i < counter; i++) {
            labelText.append("1/(");
        }

        labelText.append(savedNumber1.toString()).append(")".repeat(counter));

        return labelText.toString();
    }

    private String getLabelTextSquaredClicked() {
        if (counter <= 0 || savedNumber1 == null) {
            return "";
        }

        StringBuilder labelText = new StringBuilder("sqr(");

        for (int i = 1; i < counter; i++) {
            labelText.append("sqr(");
        }

        labelText.append(savedNumber1.toString()).append(")".repeat(counter));

        return labelText.toString();
    }

    @FXML
    private void rootClicked(ActionEvent event) {

        Platform.runLater(() -> {
            equalsClicked = false;
            number1 = new BigDecimal(text.getText());
            if (number1.doubleValue() < 0) {
                savedNumber1 = new BigDecimal("0");
                counter++;
                return;
            }
            updateText(text, number1.sqrt(MathContext.DECIMAL128).toString());

            savedNumber1 = savedNumber1 == null || savedNumber1.compareTo(BigDecimal.ZERO) <= 0
                    ? new BigDecimal(text.getText())
                    : savedNumber1;

            number1 = new BigDecimal(text.getText());
            counter++;
            numbersClickedLabel.setText(getLabelTextRootClicked());
        });

    }

    private String getLabelTextRootClicked() {
        if (counter <= 0 || savedNumber1 == null) {
            return "";
        }

        StringBuilder labelText = new StringBuilder("√(");

        for (int i = 1; i < counter; i++) {
            labelText.append("√(");
        }

        labelText.append(savedNumber1.toString()).append(")".repeat(counter));

        return labelText.toString();
    }

    private String getOperation(Operation o) {
        if (o != null) {
            switch (o) {
                case DEVIDE:
                    return " ÷ ";
                case MULTIPLY:
                    return " × ";
                case PLUS:
                    return " + ";
                case MINUS:
                    return " - ";
            }
        }

        return "";
    }

    @FXML
    private void ceClicked(ActionEvent event) {
        equalsClicked = false;

        updateText(text, "0");
        counter = 0;
        savedNumber1 = new BigDecimal("0");
        buttons.forEach((t) -> {
            t.setDisable(false);
            if (t == mrButton || t == mcButton) {
                if (memory.getText().equals("0")) {
                    t.setDisable(true);
                } else {
                    t.setDisable(false);
                }
            }
        });
    }

    @FXML
    private void negationClicked(ActionEvent event) {
        equalsClicked = false;
        number1 = new BigDecimal(text.getText());
        updateText(text, number1.negate().toString());
        number1 = new BigDecimal(text.getText());
    }

    @FXML
    private void dotClicked(ActionEvent event) {
        equalsClicked = false;
        if (text.getText().contains(".")) {
            return;
        }
        updateText(text, text.getText() + ".");
    }

    @FXML
    private void numberClicked(ActionEvent event) {

        Platform.runLater(() -> {

            equalsClicked = false;
            Button temp = (Button) event.getSource();

            if (text.getText().equals("0")) {
                text.setText("");
            }

            if (text.getText().equals("Can't divide by 0")) {
                buttons.forEach((t) -> {
                    t.setDisable(false);
                    if (t == mrButton || t == mcButton) {
                        if (memory.getText().equals("0")) {
                            t.setDisable(true);
                        } else {
                            t.setDisable(false);
                        }
                    }
                });
                numbersClickedLabel.setText("");
            }

            if (clearText) {
                text.setText(temp.getText());
                clearText = false;
            } else {
                text.setText(text.getText() + temp.getText());
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        number1 = new BigDecimal("0");
        number2 = new BigDecimal("0");
        mem = new BigDecimal("0");
        clearText = false;
        mcButton.setDisable(true);
        mrButton.setDisable(true);
        buttons.add(mPlusBtn);
        buttons.add(mMinusBtn);
        buttons.add(msBtn);
        buttons.add(percentBtn);
        buttons.add(oneOverBtn);
        buttons.add(squareBtn);
        buttons.add(divideBtn);
        buttons.add(rootBtn);
        buttons.add(multiplyBtn);
        buttons.add(plusBtn);
        buttons.add(minusBtn);
        buttons.add(dotBtn);
        buttons.add(negationBtn);
        buttons.add(mcButton);
        buttons.add(mrButton);

    }

    private void updateText(Node n, String s) {
        if (n instanceof TextField) {

            Platform.runLater(() -> {
                ((TextField) n).setText(s);
            });
        }
        if (n instanceof Label) {
            Platform.runLater(() -> {
                ((Label) n).setText(s);
            });
        }
    }

    @FXML
    private void brushClicked(MouseEvent event) {

        String currentStylesheet = anchorPane.getStylesheets().get(0);

        String nextStylesheet = currentStylesheet.endsWith("darkCalc.css") ? "lightCalc.css" : "darkCalc.css";

        anchorPane.getStylesheets().clear();
        anchorPane.getStylesheets().add(getClass().getResource(nextStylesheet).toExternalForm());
    }

}
