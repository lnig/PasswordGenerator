package org.lnig.passwordgenerator;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;

import static java.security.spec.MGF1ParameterSpec.SHA256;

public class HelloController implements Initializable {
    @FXML
    private Slider lengthSlider;

    @FXML
    private CheckBox lowercaseCB;

    @FXML
    private CheckBox numbersCB;

    @FXML
    private CheckBox symbolsCB;

    @FXML
    private ComboBox<passType> typeCombo;

    @FXML
    private CheckBox uppercaseCB;

    @FXML
    private Label generatedPass;

    @FXML
    private ImageView close;

    @FXML
    private ImageView minimize;

    @FXML
    private Label lengthText;

    private String lowerCase = "abcdefghijklmnopqrstuvwxyz";
    private String upperCase = "ABCDEFGHIJKLMOPQRSTUVWXYZ";
    private String numbers = "0123456789";
    private String symbols = "!@#$%^&*()-_";



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        close.setOnMouseClicked(mouseEvent ->{
            System.exit(0);
        });

        minimize.setOnMouseClicked(mouseEvent ->{
            Stage stage = (Stage) minimize.getScene().getWindow();
            stage.setIconified(true);
        });

        typeCombo.getItems().setAll(passType.values());
        lengthText.setText("Length: 0");

        lengthSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            int value = newValue.intValue();
            lengthText.setText("Length: " + value);
        });
    }

    public void generate(ActionEvent actionEvent) {

       StringBuilder pass = new StringBuilder();
       StringBuilder availableChars = new StringBuilder();
       SecureRandom random = new SecureRandom();

       if (lowercaseCB.isSelected()){
           availableChars.append(lowerCase);
       }
       if (uppercaseCB.isSelected()){
           availableChars.append(upperCase);
       }
       if (numbersCB.isSelected()){
           availableChars.append(numbers);
       }
       if (symbolsCB.isSelected()){
           availableChars.append(symbols);
       }

       int passLength = (int) lengthSlider.getValue();

       for (int i = 0; i < passLength; i++){
           int index = random.nextInt(availableChars.length());
           pass.append(availableChars.charAt(index));
       }

       generatedPass.setText(pass.toString());

    }
}