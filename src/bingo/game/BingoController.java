package bingo.game;

import bingo.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class BingoController {

    @FXML
    private GridPane gridPane;

    @FXML
    private Text bingoText, informationText;


    private List<String> wordsList;

    private String randomString;


    @FXML
    public void initialize() {

        bingoText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 120));
        bingoText.setTextAlignment(TextAlignment.CENTER);
        bingoText.setTextOrigin(VPos.CENTER);
        bingoText.setVisible(false);
        gridPane.getStylesheets().add("/bingo/res/style.css");
        convertFileToList();
        fillGridPane();
    }

    @FXML
    private void handleButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/bingo/game/MainMenu.fxml"));
        Parent parent = loader.load();
        Main.mainStage.setScene(new Scene(parent));
        Main.mainStage.show();
    }


    public void getMenuData(int quantity) {
        setGridPane(quantity, quantity);
        initialize();
    }

    private void setGridPane(int rows, int columns) {

        for (int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(50);
            column.setFillWidth(true);
            gridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(row);
        }
    }


    private void fillGridPane() {

        Random random = new Random();
        randomString = "";
        int maxRows = gridPane.getRowConstraints().size();
        int maxColumns = gridPane.getColumnConstraints().size();


        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {

                if (wordsList.size() != 0) {
                    int randomIndex = random.nextInt(wordsList.size() & Integer.MAX_VALUE);
                    randomString = wordsList.get(randomIndex);
                    wordsList.remove(randomString);
                    Button textButton = new Button();
                    textButton.setText(randomString);
                    textButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    buttonListener(textButton, gridPane, maxRows, maxColumns);
                    gridPane.add(textButton, j, i);
                } else {
                    informationText.setText("The wordslist too short. Please fill in more words.");
                    System.out.println("Liste nicht gross genug");
                }
            }
        }
    }

    private void convertFileToList() {
        wordsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/bingo/res/words.txt"))) {
            String lineText;
            while ((lineText = reader.readLine()) != null) {
                wordsList.add(lineText);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(wordsList);
    }

    private void buttonListener(Button selectedButton, GridPane gridPane, int maxRows, int maxColumns) {
        selectedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedButton.setStyle("-fx-background-color: aqua");
                int selectedRow = GridPane.getRowIndex(selectedButton);
                int selectedColumn = GridPane.getColumnIndex(selectedButton);
                if (checkBingoHorizontal(selectedColumn, maxColumns, selectedRow, maxRows) || checkBingoVertical(selectedColumn, maxColumns, selectedRow, maxRows) ||
                        checkBingoAcrossLeftToRight(selectedColumn, maxColumns, selectedRow, maxRows) || checkBingoAcrossRightToLeft(selectedColumn, maxColumns, selectedRow, maxRows)) {
                    bingoText.setVisible(true);
                    System.out.println("BINGO");
                }


            }
        });
    }

    private boolean checkBingoVertical(int startingColumn, int maxColumns, int startingRow, int maxRows) {
        System.out.println("START  COL =" + startingColumn + " AND ROW:  " + startingRow);
        int checkingColumn = startingColumn;
        int checkingRow = startingRow;
        int counter = 1;

        while (checkingColumn >= 0 && checkingColumn < maxColumns && checkingRow >= 0 && checkingRow < maxRows) {
            checkingColumn++;
            if (getNodeFormGridPane(checkingColumn, checkingRow)) {
                counter++;
                System.out.println("counting + :" + counter);
            }

            System.out.println("checkCol+: " + checkingColumn);

        }
        System.out.println("RESET");
        checkingColumn = startingColumn;
        checkingRow = startingRow;
        while (checkingColumn > 0 && checkingColumn < maxColumns && checkingRow >= 0 && checkingRow < maxRows) {
            checkingColumn--;
            System.out.println("checkCol-: " + checkingColumn);

            if (getNodeFormGridPane(checkingColumn, checkingRow)) {
                counter++;
                System.out.println("counting - :" + counter);
            }
        }
        System.out.println("EndCount:" + counter);
        if (counter == maxColumns) {
            return true;
        } else {
            return false;
        }

    }

    private boolean checkBingoHorizontal(int startingColumn, int maxColumns, int startingRow, int maxRows) {
        System.out.println("START  COL =" + startingColumn + " AND ROW:  " + startingRow);
        int checkingColumn = startingColumn;
        int checkingRow = startingRow;
        int counter = 1;

        while (checkingColumn >= 0 && checkingColumn < maxColumns && checkingRow >= 0 && checkingRow < maxRows) {
            checkingRow++;
            if (getNodeFormGridPane(checkingColumn, checkingRow)) {
                counter++;
                System.out.println("counting + :" + counter);
            }

            System.out.println("checkCol+: " + checkingColumn);

        }
        System.out.println("RESET");
        checkingColumn = startingColumn;
        checkingRow = startingRow;
        while (checkingColumn > 0 && checkingColumn < maxColumns && checkingRow >= 0 && checkingRow < maxRows) {
            checkingRow--;
            System.out.println("checkCol-: " + checkingColumn);

            if (getNodeFormGridPane(checkingColumn, checkingRow)) {
                counter++;
                System.out.println("counting - :" + counter);
            }
        }
        System.out.println("EndCount:" + counter);
        if (counter == maxColumns) {
            return true;
        } else {
            return false;
        }

    }

    private boolean checkBingoAcrossLeftToRight(int startingColumn, int maxColumns, int startingRow, int maxRows) {
        System.out.println("START  COL =" + startingColumn + " AND ROW:  " + startingRow);
        int checkingColumn = startingColumn;
        int checkingRow = startingRow;
        int counter = 1;

        while (checkingColumn >= 0 && checkingColumn < maxColumns && checkingRow >= 0 && checkingRow < maxRows) {
            checkingColumn++;
            checkingRow++;

            if (getNodeFormGridPane(checkingColumn, checkingRow)) {
                counter++;
                System.out.println("counting + :" + counter);
            }

            System.out.println("checkCol+: " + checkingColumn);

        }
        System.out.println("RESET");
        checkingColumn = startingColumn;
        checkingRow = startingRow;
        while (checkingColumn > 0 && checkingColumn < maxColumns && checkingRow >= 0 && checkingRow < maxRows) {
            checkingColumn--;
            checkingRow--;
            System.out.println("checkCol-: " + checkingColumn);

            if (getNodeFormGridPane(checkingColumn, checkingRow)) {
                counter++;
                System.out.println("counting - :" + counter);
            }
        }
        if (counter == maxColumns) {
            return true;
        } else {
            return false;
        }

    }

    private boolean checkBingoAcrossRightToLeft(int startingColumn, int maxColumns, int startingRow, int maxRows) {
        System.out.println("START  COL =" + startingColumn + " AND ROW:  " + startingRow);
        int checkingColumn = startingColumn;
        int checkingRow = startingRow;
        int counter = 1;

        while (checkingColumn >= 0 && checkingColumn < maxColumns && checkingRow >= 0 && checkingRow < maxRows) {
            checkingColumn++;
            checkingRow--;

            if (getNodeFormGridPane(checkingColumn, checkingRow)) {
                counter++;
                System.out.println("counting + :" + counter);
            }

            System.out.println("checkCol+: " + checkingColumn);

        }
        System.out.println("RESET");
        checkingColumn = startingColumn;
        checkingRow = startingRow;
        while (checkingColumn > 0 && checkingColumn < maxColumns && checkingRow >= 0 && checkingRow < maxRows) {
            checkingColumn--;
            checkingRow++;
            System.out.println("checkCol-: " + checkingColumn);

            if (getNodeFormGridPane(checkingColumn, checkingRow)) {
                counter++;
                System.out.println("counting - :" + counter);
            }
        }
        System.out.println("EndCount:" + counter);
        if (counter == maxColumns) {
            return true;
        } else {
            return false;
        }

    }

    private boolean getNodeFormGridPane(int col, int row) {

        System.out.println("CHECKING  COL:" + col + "AND ROW:  " + row);
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                System.out.println("erwartete ROW: " + GridPane.getRowIndex(node) + "  ...AND COL : " + GridPane.getColumnIndex(node));
                Button button = (Button) node;
                if (button.getStyle().equals("-fx-background-color: aqua")) {
                    System.out.println("Farbe: " + button.getStyle());
                    return true;
                }
            }
        }
        return false;
    }


}
