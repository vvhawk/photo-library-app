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
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class userController {


        @FXML
        Button album_add;

        @FXML
        TextField album_add_field;

        @FXML
        Button album_delete;

        @FXML
        Button album_rename;

        @FXML
        Button album_open;

        @FXML
        Button album_search;

        @FXML
        Button admin2_logout;

        @FXML
        Label album_name;

        @FXML
        Label album_count;

        @FXML
        Label album_range;

        @FXML
        ComboBox album_list;

        @FXML
        TextField album_rename_field;

        @FXML
        AnchorPane users_backdrop;

        private Stage stage;
        private Scene scene;
        private Parent root;

        private static int userID;

        private int albumCount;


        ArrayList<User> users = null;

        public void initialize() throws IOException, ClassNotFoundException
        {

            try
            {
                FileInputStream fileIn = new FileInputStream("Users.ser");

                ObjectInputStream in = new ObjectInputStream(fileIn);


                users = (ArrayList<User>) in.readObject();



                for (int i = 0; i < users.get(userID).albums.size(); i++)
                {
                    album_list.getItems().add(users.get(userID).albums.get(i).getAlbumName());
                    albumCount++;
                }
                album_list.setValue(users.get(userID).albums.get(0).getAlbumName());
                album_name.setText(users.get(userID).albums.get(0).getAlbumName());
                album_count.setText(users.get(userID).albums.get(0).getCount()+ " photo(s)");
                album_range.setText(users.get(userID).albums.get(0).getFirstDate() + " - "  + users.get(userID).albums.get(0).getLastDate());


            } catch (EOFException ex) {

            } catch (Exception ex) {

            }

        }


        public static void setUser(int id)
        {
            userID = id;
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


        public void addAlbum(ActionEvent event)
        {
            boolean dupExists = false;

            try
            {
                if (album_add_field.getText().equals(""))
                {
                    return;
                }
            }
            catch(Exception ex)
            {

            }

            if (album_add_field.getText() == null)
            {
                return;
            }

            for (int i = 0; i < users.get(userID).albums.size(); i++)
            {

                if (album_add_field.getText().equals(users.get(userID).albums.get(i).getAlbumName()))
                {
                    dupExists = true;
                }
            }

            if (dupExists) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Attempt");
                alert.setHeaderText("Sorry, that album already exists");

                Image image = new Image(getClass().getResource("seshDup.gif").toExternalForm());
                ImageView imageView = new ImageView(image);

                alert.setGraphic(imageView);

                alert.showAndWait();

                return;

            }


            users.get(userID).albums.add(new Album(album_add_field.getText(), new ArrayList<Photo>()));
            album_list.getItems().add(users.get(userID).albums.get(albumCount).getAlbumName());
            album_list.setValue(users.get(userID).albums.get(albumCount).getAlbumName());

            album_name.setText(users.get(userID).albums.get(albumCount).getAlbumName());
            album_count.setText(users.get(userID).albums.get(albumCount).getCount()+ " photo(s)");
            album_range.setText(users.get(userID).albums.get(albumCount).getFirstDate() + " - "  + users.get(userID).albums.get(albumCount).getLastDate());

            album_add_field.setText(null);

            albumCount++;
        }

        public void deleteAlbum(ActionEvent event) {



            if (album_list.getValue() == null)
            {
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Are you sure about that?");

            Image image = new Image(getClass().getResource("seshPL.gif").toExternalForm());
            ImageView imageView = new ImageView(image);

            alert.setGraphic(imageView);

            if (alert.showAndWait().get() == ButtonType.OK)
            {

                for (int i = 0; i < users.get(userID).albums.size(); i++)
                {
                    if (users.get(userID).albums.get(i).getAlbumName().equals(album_list.getValue()))
                    {
                        if (users.get(userID).getName().equals("stock") && users.get(userID).albums.get(i).getAlbumName().equals("stock"))
                        {
                            User.killStock();
                        }
                        users.get(userID).albums.remove(i);
                        album_list.getItems().remove(album_list.getValue());

                        if(album_list.getValue() != null)
                        {
                            album_list.getSelectionModel().selectNext();
                            album_name.setText(album_list.getValue().toString());
                            for (int j = 0; j < users.get(userID).albums.size(); j++)
                            {
                                if (users.get(userID).albums.get(j).getAlbumName().equals(album_list.getValue().toString()))
                                {
                                    album_count.setText(users.get(userID).albums.get(j).getCount() + " photo(s)");
                                    if (users.get(userID).albums.get(j).getCount() != 0)
                                    {
                                        album_range.setText(users.get(userID).albums.get(j).getFirstDate() + " - "  + users.get(userID).albums.get(j).getLastDate());
                                    }
                                    else
                                    {
                                        album_range.setText("");
                                    }
                                }
                            }
                        }
                        if(album_list.getValue() == null)
                        {
                            album_list.getSelectionModel().selectNext();
                        }

                        if(album_list.getValue() == null)
                        {
                            album_name.setText("");
                        }



                        albumCount--;
                    }

                }



            }
        }

        public void renameAlbum(ActionEvent event) {

            if (album_list.getValue() == null) {
                return;
            }

            if (album_rename_field.getText() == null) {

                return;
            }

            try
            {
                if (album_rename_field.getText().equals(""))
                {

                    return;
                }
            }
            catch(Exception ex)
            {

            }



            boolean dupExists = false;


            for (int i = 0; i < users.get(userID).albums.size(); i++)
            {

                if (album_rename_field.getText().equals(users.get(userID).albums.get(i).getAlbumName())) {

                    dupExists = true;
                }
            }

            if (dupExists)
            {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Rename Attempt");
                alert.setHeaderText("Sorry, that album already exists");

                Image image = new Image(getClass().getResource("seshDup.gif").toExternalForm());
                ImageView imageView = new ImageView(image);

                alert.setGraphic(imageView);

                alert.showAndWait();

            }
            else
            {

                for (int i = 0; i < users.get(userID).albums.size(); i++)
                {
                    if (album_list.getValue().equals(users.get(userID).albums.get(i).getAlbumName()))
                    {
                        users.get(userID).albums.get(i).setAlbumName(album_rename_field.getText());
                        album_rename_field.setText(null);

                        album_list.getItems().clear();

                        for (int j = 0; j < users.get(userID).albums.size(); j++)
                        {
                            album_list.getItems().add(users.get(userID).albums.get(j).getAlbumName());
                        }

                        album_list.setValue(users.get(userID).albums.get(i).getAlbumName());

                        album_name.setText(users.get(userID).albums.get(i).getAlbumName());


                    }
                }

            }

        }

        public void setAlbumList()
        {
            if (album_list.getValue() != null)
            {
                album_name.setText(album_list.getValue().toString());

                for (int i = 0; i < users.get(userID).albums.size(); i++)
                {
                    if (users.get(userID).albums.get(i).getAlbumName().equals(album_name.getText()))
                    {
                        album_count.setText(users.get(userID).albums.get(i).getCount() + " photo(s)");
                        if (users.get(userID).albums.get(i).getCount() != 0)
                        {
                            album_range.setText(users.get(userID).albums.get(i).getFirstDate() + " - "  + users.get(userID).albums.get(i).getLastDate());
                        }
                        else
                        {
                            album_range.setText(" - ");
                        }
                    }
                }

            }
            else
            {
                album_count.setText("");
                album_range.setText("");
            }
        }

        public void open(ActionEvent event) throws IOException
        {

            if (album_list.getValue() == null)
            {
                return;
            }

            for (int i = 0; i < users.get(userID).albums.size(); i++)
            {
                if (users.get(userID).albums.get(i).getAlbumName().equals(album_list.getValue()))
                {
                    openController.setAlbum(i);
                }
            }

            openController.setUser(userID);

            FileOutputStream fileOut = new FileOutputStream("Users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(users);
            out.close();
            fileOut.close();

            root = FXMLLoader.load(getClass().getResource("open.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        public void search(ActionEvent event) throws IOException {
            FileOutputStream fileOut = new FileOutputStream("Users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(users);
            out.close();
            fileOut.close();

            root = FXMLLoader.load(getClass().getResource("search.fxml"));
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
                FileOutputStream fileOut = new FileOutputStream("Users.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);

                out.writeObject(users);
                out.close();
                fileOut.close();

                stage = (Stage) users_backdrop.getScene().getWindow();
                stage.close();
            }
        }

    }



