package com.example.bro_comm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class admin1Controller
{
    @FXML
    AnchorPane admin1_backdrop;
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchToAdmin2(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("admin2.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHome(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void quit(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Ready to say goodbye?");
        alert.setContentText("All changes will be saved");

        Image image = new Image(getClass().getResource("seshQuit.gif").toExternalForm());
        ImageView imageView = new ImageView(image);

        alert.setGraphic(imageView);

        if (alert.showAndWait().get() == ButtonType.OK)
        {

            stage = (Stage) admin1_backdrop.getScene().getWindow();
            stage.close();
        }
    }
}