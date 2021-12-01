package com.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    //all gui variables
    private GridPane gridPane = new GridPane();
    private BorderPane borderPane = new BorderPane();
    private Label title = new Label("Tic Tac Toe Game");
    private Button restartButton = new Button("Restart Game");
    Font font = Font.font("Roboto", FontWeight.BOLD, 30);


    // 0...8
    private Button[] btns = new Button[9];

    //all logic variable
    boolean gameOver = false;
    int activePlayer = 0;
    int gameState[] = {3, 3, 3, 3, 3, 3, 3, 3, 3};
    int winingPosition[][] = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };


    @Override
    public void start(Stage stage) throws IOException {


        this.createGUI();
        this.handleEvent();
        Scene scene = new Scene(borderPane, 550, 650);
        stage.setScene(scene);

        stage.show();


    }

    //this function is for creating gui
    private void createGUI() {

        //creating title;
        title.setFont(font);
        //creating restart button
        restartButton.setFont(font);
        restartButton.setDisable(true);
        // setting title and restart button to borderPane
        borderPane.setTop(title);
        borderPane.setBottom(restartButton);
        //setting borderPane components to center
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(restartButton, Pos.CENTER);
        borderPane.setPadding(new Insets(20, 20, 20, 20));

        //working on 9 game button 0 to 8
        int label = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setId(label + "");
                button.setFont(font);
                button.setPrefWidth(150);
                button.setPrefHeight(150);
                gridPane.add(button, j, i);
                gridPane.setAlignment(Pos.CENTER);
                btns[label] = button;
                label++;
            }
        }

        borderPane.setCenter(gridPane);

    }

    //method for handling events
    private void handleEvent() {
        //restart button click
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (int i = 0; i < 9; i++) {
                    gameState[i] = 3;
//                    btns[i].setText("");
                    btns[i].setGraphic(null);
                    btns[i].setBackground(null);
                    btns[i].setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
                    gameOver = false;
                    restartButton.setDisable(true);
                }
            }
        });

        for (Button btn : btns) {
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Button currentBtn = (Button) actionEvent.getSource();
                    String idS = currentBtn.getId();
                    int idI = Integer.parseInt(idS);//idS=button
                    //
//                    1.
                    if (gameOver) {
                        //game over and print msg

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setContentText("Game over !!  Try to restart the game");
                        alert.show();
                    } else {
                        //game is not over to do chances
                        //check for player
                        if (gameState[idI] == 3) {
                            //proceed

                            if (activePlayer == 1) {
                                //chance of 1
                                //  currentBtn.setText(activePlayer + "");
                                currentBtn.setGraphic(new ImageView(new Image("file:src/main/resources/img/cross.png")));
                                gameState[idI] = activePlayer;
                                checkForWinner();
                                activePlayer = 0;


                            } else {
//                                chance of 0
//                                currentBtn.setText(activePlayer + "");
                                currentBtn.setGraphic(new ImageView(new Image("file:src/main/resources/img/zero.png")));

                                gameState[idI] = activePlayer;
                                checkForWinner();
                                activePlayer = 1;
                            }


                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error message");
                            alert.setContentText("Placed is already occupied");
                            alert.show();

                        }
                    }
                    ;

                }
            });
        }
    }

    //this method checks for winner
    private void checkForWinner() {

        //winner
        if (!gameOver) {
            for (int wp[] : winingPosition) {


                if (gameState[wp[0]] == gameState[wp[1]] && gameState[wp[1]] == gameState[wp[2]] && gameState[wp[1]] != 3) {
                    // activePlayer has winner
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Success message");
                    alert.setContentText((activePlayer == 1 ? "X " : "0 ") + " has won the game");
                    btns[wp[0]].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    btns[wp[1]].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    btns[wp[2]].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    alert.show();
                    gameOver = true;
                    restartButton.setDisable(false);
                    break;

                }
            }

        }


    }

    public static void main(String[] args) {
        launch();
    }

}