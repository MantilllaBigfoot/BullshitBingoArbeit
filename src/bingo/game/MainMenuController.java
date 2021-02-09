package bingo.game;

import bingo.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

import java.io.IOException;


public class MainMenuController {

    @FXML
    ChoiceBox choiceBox;

    @FXML
    Text informationText;

    int quantity;

    @FXML
    public void initialize() {
        manageChoiceBox();
    }

    public void manageChoiceBox() {
        choiceBox.setValue("Spalten/Zeilen");
        for (int i = 2; i <= 6; i++) {
            choiceBox.getItems().add(i + "  Spalten/Zeilen");
        }

        choiceBox.setOnAction((event -> {
            int selectedItem = choiceBox.getSelectionModel().getSelectedIndex();
            quantity = selectedItem +2;
        }));
    }

    @FXML
    public void sendUserToBingoGame() throws IOException {
        if (choiceBox.getSelectionModel().isEmpty()){
            System.out.println("upssssss");
            informationText.setText("Bitte zuerst die Spalten/Zeilen-Anzahl auswählen");
        }else{
            BingoController bingoController = new BingoController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bingo/game/bingo.fxml"));
            Parent parent = loader.load();
            bingoController = loader.getController();
            bingoController.getMenuData(quantity);
            Main.mainStage.setScene(new Scene(parent));
            Main.mainStage.show();
        }


    }


}
