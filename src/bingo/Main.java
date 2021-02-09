package bingo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/bingo/game/mainMenu.fxml"));
        primaryStage.setTitle("Bullshit Bingoooo");
        Scene bingoScene = new Scene(root, 1024, 600);
        bingoScene.getStylesheets().add("/bingo/res/style.css");
        mainStage = primaryStage;
        primaryStage.setScene(bingoScene);
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
