package com.example.bro_comm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;



public class searchController
{

    @FXML
    AnchorPane search_backdrop;

    @FXML
    Button search_back;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToHome(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("user.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
