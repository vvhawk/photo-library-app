package com.example.bro_comm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class admin2Controller
{

    @FXML
    ComboBox admin2_users;

    @FXML
    TextField admin2_addText;

    @FXML
    AnchorPane admin2_backdrop;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private int user_count;

    ArrayList <User> al = null;


    public admin2Controller() throws IOException, ClassNotFoundException {
    }

    public void initialize() throws IOException, ClassNotFoundException
    {


        try
        {

            FileInputStream fileIn = new FileInputStream("Users.ser");


            ObjectInputStream in = new ObjectInputStream(fileIn);



            al = (ArrayList<User>) in.readObject();



            for (int i = 0; i < al.size(); i++)
            {

                admin2_users.getItems().add(al.get(user_count).getName());
                user_count++;
            }
            admin2_users.setValue(al.get(0).getName());


        }
        catch (EOFException ex){
        }catch(Exception ex)
        {

        }
        if (al == null)
        {
            al = new ArrayList<User>();
        }

    }





    public void switchToHome(ActionEvent event) throws IOException
    {
        FileOutputStream fileOut = new FileOutputStream("Users.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        out.writeObject(al);
        out.close();
        fileOut.close();
        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void add(ActionEvent event) throws IOException
    {

        boolean dupExists = false;

        if (admin2_addText.getText().equals(""))
        {
            return;
        }

        for (int i = 0; i < al.size(); i++)
        {

            if (admin2_addText.getText().equals(al.get(i).getName()))
            {
                dupExists = true;
            }
        }

        if (dupExists)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Attempt");
            alert.setHeaderText("Sorry mate, that user already exists");

            Image image = new Image(getClass().getResource("seshDup.gif").toExternalForm());
            ImageView imageView = new ImageView(image);

            alert.setGraphic(imageView);

            alert.showAndWait();

            return;

        }


        al.add(new User(admin2_addText.getText()));

        admin2_users.getItems().add(al.get(user_count).getName());
        admin2_users.setValue(al.get(user_count).getName());
        admin2_addText.setText(null);

        user_count++;

    }

    public void delete(ActionEvent event) throws IOException
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Are you sure about that?");

        Image image = new Image(getClass().getResource("seshPL.gif").toExternalForm());
        ImageView imageView = new ImageView(image);

        alert.setGraphic(imageView);

        if(alert.showAndWait().get() == ButtonType.OK)
        {

            for (int i = 0; i < al.size(); i++)
            {
                if(al.get(i).getName().equals(admin2_users.getValue()))
                {
                    al.remove(i);
                    admin2_users.getItems().remove(admin2_users.getValue());
                    admin2_users.getSelectionModel().selectNext();
                    user_count--;
                }

            }

        }

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
            FileOutputStream fileOut = new FileOutputStream("Users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(al);
            out.close();
            fileOut.close();

            stage = (Stage) admin2_backdrop.getScene().getWindow();
            stage.close();
        }


    }

}