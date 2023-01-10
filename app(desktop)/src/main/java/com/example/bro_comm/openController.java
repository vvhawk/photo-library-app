package com.example.bro_comm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class openController
{
    @FXML
    AnchorPane open_backdrop;

    @FXML
    Label open_title;

    @FXML
    TilePane open_tile;

    @FXML
    ScrollPane open_scroll;

    @FXML
    ImageView open_preview;

    @FXML
    ComboBox select_album;
    private Stage stage;
    private Scene scene;
    private Parent root;


    private static int userID;
    private static int albumID;
    ArrayList<User> users = null;

    private Photo chosen;

    int photoID;



    public void initialize() throws IOException, ClassNotFoundException
    {

        try
        {
            FileInputStream fileIn = new FileInputStream("Users.ser");

            ObjectInputStream in = new ObjectInputStream(fileIn);


            users = (ArrayList<User>) in.readObject();

            open_title.setText(users.get(userID).albums.get(albumID).getAlbumName());

            select_album.getItems().clear();

            for (int i = 0; i < users.get(userID).albums.size(); i++)
            {
                select_album.getItems().add(users.get(userID).albums.get(i).getAlbumName());
            }

            select_album.getSelectionModel().selectFirst();

            for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().size(); i++)
            {

                File file = new File(users.get(userID).albums.get(albumID).getPhotos().get(i).getFilePath());

                Photo selected = users.get(userID).albums.get(albumID).getPhotos().get(i);


                Image image = new Image(new FileInputStream(file), 150, 0, true, true);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(100);


                imageView.setOnMouseClicked(e -> {
                    try {
                        select(selected);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });


                open_tile.getChildren().add(imageView);


            }

        }
        catch (EOFException ex)
        {

        }
        catch (Exception ex)
        {

        }

    }

    public static void setUser(int id)
    {
        userID = id;


    }

    public static void setAlbum(int id)
    {
        albumID = id;
    }


    public void add(ActionEvent event) throws FileNotFoundException
    {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(stage); //returns file


        Image image = new Image(new FileInputStream(file), 150, 0, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);


        users.get(userID).albums.get(albumID).getPhotos().add(new Photo(file.getAbsolutePath()));

        int recent = users.get(userID).albums.get(albumID).getPhotos().size() - 1;

        Photo selected = users.get(userID).albums.get(albumID).getPhotos().get(recent);


        imageView.setOnMouseClicked(e -> {
            try {
                select(selected);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        open_tile.getChildren().add(imageView);




    }


    public void select(Photo selected) throws FileNotFoundException
    {


        String myPath = selected.getFilePath();
        File myFile = new File(myPath);
        Image myImage = new Image(new FileInputStream(myFile), 150, 0, true, true);

        chosen = selected;

        for(int i = 0; i < users.get(userID).albums.get(albumID).getCount(); i ++)
        {
            if (users.get(userID).albums.get(albumID).getPhotos().get(i).equals(chosen))
            {
                photoID = i;
            }
        }

        open_preview.setImage(myImage);

    }

    public void delete() throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Are you sure about that?");

        Image image = new Image(getClass().getResource("seshPL.gif").toExternalForm());
        ImageView imageView = new ImageView(image);

        alert.setGraphic(imageView);

        if (alert.showAndWait().get() == ButtonType.OK) {
            for (int i = 0; i < users.get(userID).albums.get(albumID).getCount(); i++) {

                if (users.get(userID).albums.get(albumID).getPhotos().get(i).equals(chosen)) {

                    users.get(userID).albums.get(albumID).getPhotos().remove(i);

                    open_preview.setImage(null);

                    open_tile.getChildren().clear();

                    FileOutputStream fileOut = new FileOutputStream("Users.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);

                    out.writeObject(users);
                    out.close();
                    fileOut.close();

                    initialize();
                    break;

                }

            }
        }
    }

    public void copy() throws IOException, ClassNotFoundException
    {

        if (select_album.getValue() == null)
        {
            return;
        }

        if (select_album.getValue().equals(users.get(userID).albums.get(albumID).getAlbumName()))
        {
            return;
        }
        int dest = 0;

        for (int i = 0; i < users.get(userID).albums.size(); i++)
        {
            if (users.get(userID).albums.get(i).getAlbumName().equals(select_album.getValue().toString()))
            {
                dest = i;
            }
        }

        for (int i = 0; i < users.get(userID).albums.get(albumID).getCount(); i++)
        {

            if (users.get(userID).albums.get(albumID).getPhotos().get(i).equals(chosen))
            {

                if (select_album.getValue() != null)
                {
                    String path = users.get(userID).albums.get(albumID).getPhotos().get(i).getFilePath();

                    users.get(userID).albums.get(dest).getPhotos().add(new Photo(path));
                }

                open_preview.setImage(null);
                break;

            }

        }
    }

    public void move() throws IOException, ClassNotFoundException
    {
        if (select_album == null)
        {
            return;
        }

        if (select_album.getValue().equals(users.get(userID).albums.get(albumID).getAlbumName()))
        {
            return;
        }

        copy();
        delete();
    }


    public void display(ActionEvent event) throws IOException {


        if (open_preview.getImage() == null)
        {
            return;
        }


        displayController.setUser(userID);
        displayController.setAlbum(albumID);
        displayController.setPhoto(photoID);


        FileOutputStream fileOut = new FileOutputStream("Users.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        out.writeObject(users);
        out.close();
        fileOut.close();

        root = FXMLLoader.load(getClass().getResource("display.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void back(ActionEvent event) throws IOException
    {
        FileOutputStream fileOut = new FileOutputStream("Users.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        out.writeObject(users);
        out.close();
        fileOut.close();
        root = FXMLLoader.load(getClass().getResource("user.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHome(ActionEvent event) throws IOException
    {
        FileOutputStream fileOut = new FileOutputStream("Users.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        out.writeObject(users);
        out.close();
        fileOut.close();
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
            FileOutputStream fileOut = new FileOutputStream("Users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(users);
            out.close();
            fileOut.close();

            stage = (Stage) open_backdrop.getScene().getWindow();
            stage.close();
        }
    }

}
