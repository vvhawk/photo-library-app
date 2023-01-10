package com.example.bro_comm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;


public class Photos extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene home = new Scene(root);


        Font.loadFont(getClass().getResourceAsStream("Caveat.ttf"), 14);
        String style = this.getClass().getResource("style.css").toExternalForm();
        home.getStylesheets().add(style);
        stage.setTitle("Photos");
        stage.setScene(home);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}