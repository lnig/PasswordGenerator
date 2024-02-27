package org.lnig.passwordgenerator;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.util.*;


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

        typeCombo.valueProperty().addListener((obs, oldValue, newValue) -> {

           lengthSlider.setMax(50);

           if (newValue.toString().equals("WORDS")){
               lengthSlider.setMax(20);
           }
        });

    }

    public void generate(ActionEvent actionEvent) throws FileNotFoundException {

       StringBuilder pass = new StringBuilder();
       StringBuilder availableChars = new StringBuilder();
       SecureRandom random = new SecureRandom();
       Map<Character, Character> specialCharsMapping = getCharacterCharacterMap();

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
       passType choosenPassType = typeCombo.getValue();

       if (choosenPassType == null || passLength == 0){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setHeaderText("Ostrzezenie");
           alert.setContentText("Wybierz typ hasła i liczbe znakow");
           alert.showAndWait();
       }else {
           if (choosenPassType == passType.RANDOM) {
               for (int i = 0; i < passLength; i++) {
                   int index = random.nextInt(availableChars.length());
                   pass.append(availableChars.charAt(index));
               }
           } else {

               File file = new File("src/main/resources/org/lnig/passwordgenerator/words_alpha.txt");
               Scanner sc = new Scanner(file);

               List<String> words = new ArrayList<>();
               String readedWord;

               while (sc.hasNextLine()) {
                   readedWord = sc.nextLine();
                   words.add(readedWord);
               }

               if (!words.isEmpty()) {

                   if (symbolsCB.isSelected()){
                       passLength -= 1;
                   }
                   do {
                       int index = random.nextInt(words.size());
                       readedWord = words.get(index);
                   }while(readedWord.length() != passLength);

                   System.out.println(readedWord);
                   boolean added = false;

                   if (uppercaseCB.isSelected() && !lowercaseCB.isSelected()){
                       readedWord = readedWord.toUpperCase();
                   }

                   for (char c : readedWord.toCharArray()){
                       if (numbersCB.isSelected()){
                           if (specialCharsMapping.containsKey(c)){
                               pass.append(specialCharsMapping.get(c));
                               added = true;
                           }
                       }

                       if (uppercaseCB.isSelected() && lowercaseCB.isSelected()) {
                           boolean rand = random.nextBoolean();

                           if (!added){
                               if (rand){
                                   pass.append(Character.toUpperCase(c));
                                   added = true;
                               }
                           }
                       }

                       if (!added){
                           pass.append(c);
                       }

                       added = false;
                   }

                   if (symbolsCB.isSelected()){
                       int index = random.nextInt(symbols.length());
                       pass.append(symbols.charAt(index));
                   }


               }
           }
       }

       generatedPass.setText(pass.toString());

    }

    private static Map<Character, Character> getCharacterCharacterMap() {
        Map<Character, Character> specialCharsMapping = new HashMap<>();
        specialCharsMapping.put('a', '4');
        specialCharsMapping.put('e', '3');
        specialCharsMapping.put('i', '1');
        specialCharsMapping.put('s', '5');
        specialCharsMapping.put('b', '6');
        specialCharsMapping.put('o', '0');
        specialCharsMapping.put('A', '4');
        specialCharsMapping.put('E', '3');
        specialCharsMapping.put('I', '1');
        specialCharsMapping.put('S', '5');
        specialCharsMapping.put('B', '6');
        specialCharsMapping.put('O', '0');
        return specialCharsMapping;
    }
}