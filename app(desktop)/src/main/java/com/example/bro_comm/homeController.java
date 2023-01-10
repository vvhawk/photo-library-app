package com.example.bro_comm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;


public class homeController {

    @FXML
    TextField home_username;
    @FXML
    AnchorPane home_backdrop;
    private Stage stage;
    private Scene scene;
    private Parent root;

    ArrayList<User> al = new ArrayList<User>();


    public void initialize() throws IOException, ClassNotFoundException
    {


        try
        {

            FileInputStream fileIn = new FileInputStream("Users.ser");

            ObjectInputStream in = new ObjectInputStream(fileIn);

            al = (ArrayList<User>) in.readObject();

        }
        catch (EOFException ex)
        {

            al.add(new User("stock"));

            if (User.getStock() == true)
            {
                al.get(0).albums.add(new Album("stock", new ArrayList<Photo>()));

                al.get(0).albums.get(0).getPhotos().add(new Photo("src/main/resources/com/example/bro_comm/stock1.jpeg"));
                al.get(0).albums.get(0).getPhotos().add(new Photo("src/main/resources/com/example/bro_comm/stock2.jpeg"));
                al.get(0).albums.get(0).getPhotos().add(new Photo("src/main/resources/com/example/bro_comm/stock3.jpeg"));
                al.get(0).albums.get(0).getPhotos().add(new Photo("src/main/resources/com/example/bro_comm/stock4.jpeg"));
                al.get(0).albums.get(0).getPhotos().add(new Photo("src/main/resources/com/example/bro_comm/stock5.jpeg"));

            }




            FileOutputStream fileOut = new FileOutputStream("Users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(al);
            out.close();
            fileOut.close();

        }
        catch(Exception ex)
        {

        }

    }

    public void switchToAdmin1(ActionEvent event) throws IOException
    {


        if (home_username.getText().equals("admin"))
        {
            root = FXMLLoader.load(getClass().getResource("admin1.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        try
        {
            FileInputStream fileIn = new FileInputStream("Users.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            ArrayList<User> actual = (ArrayList<User>) in.readObject();

            for (int i = 0; i < actual.size(); i++)
            {

                if (home_username.getText().equals(actual.get(i).getName()))
                {

                    userController.setUser(i);



                    FXMLLoader loader = new FXMLLoader(getClass().getResource("user.fxml"));
                    root = loader.load();

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    return;
                }
                else
                {
                    if (i == actual.size() - 1 && (!home_username.getText().equals(actual.get(i).getName())) && (!home_username.getText().equals("admin")))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Login Attempt");
                        alert.setHeaderText("User '" + home_username.getText() + "' does not exist");
                        alert.setContentText("Enter 'admin' to configure");

                        Image image = new Image(getClass().getResource("seshQuit.gif").toExternalForm());
                        ImageView imageView = new ImageView(image);

                        alert.setGraphic(imageView);
                        alert.showAndWait();
                    }

                }

            }
        }
        catch (IOException e) {
            //throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            //throw new RuntimeException(e);
        }



    }



    public void switchToAbout(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("about.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
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

            stage = (Stage) home_backdrop.getScene().getWindow();
            stage.close();
        }
    }

}