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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class displayController {



    @FXML
    Button addTagButton;
    @FXML
    Button removeTagButton;
    @FXML
    ComboBox selectTagType;
    @FXML
    Label captionDisplay;
    @FXML
    Label datesDisplay;
    @FXML
    TextField addCaptionField;
    @FXML
    Button captionButton;
    @FXML
    TextField createTagType;
    @FXML
    Button createTagButton;

    @FXML TextField tagValueField;

    @FXML
    AnchorPane display_backdrop;

    @FXML ListView tagList;

    @FXML
    Label display_title;

    @FXML
    ImageView display_view;

    @FXML
    Button display_forward;

    @FXML
    Button display_reverse;

    @FXML
    Button deleteTagType;

    @FXML
    Button deleteCaption;


    private Stage stage;

    private Scene scene;

    private Parent root;

    ArrayList<User> users = null;

    private static int userID;

    private static int albumID;

    private static int photoID;



    public void initialize() throws IOException, ClassNotFoundException
    {
        try
        {

            FileInputStream fileIn = new FileInputStream("Users.ser");

            ObjectInputStream in = new ObjectInputStream(fileIn);

            users = (ArrayList<User>) in.readObject();

            display_title.setText(users.get(userID).albums.get(albumID).getAlbumName());

            String myPath = users.get(userID).albums.get(albumID).getPhotos().get(photoID).getFilePath();
            File myFile = new File(myPath);
            Image myImage = new Image(new FileInputStream(myFile));

            display_view.setImage(myImage);
            captionDisplay.setText(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getCaption());

            Calendar cal = users.get(userID).albums.get(albumID).getPhotos().get(photoID).getDate();
            SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
            String formatted = format1.format(cal.getTime());

            datesDisplay.setText(formatted);



            for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
            {
                selectTagType.getItems().add(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName());
            }

            selectTagType.getSelectionModel().selectFirst();
            tagList.getItems().clear();
            for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
            {
                if (users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName().equals(selectTagType.getValue().toString()))
                {
                    tagList.getItems().addAll(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getTagValue());
                }
            }




        }
        catch (EOFException ex){


        }catch(Exception ex)
        {

        }

    }


    public static void setUser(int id){ userID = id;}

    public static void setAlbum(int id){ albumID = id;}

    public static void setPhoto(int id){ photoID = id;}

    public void displayForward() throws FileNotFoundException {

        if (photoID + 1 == users.get(userID).albums.get(albumID).getCount())
        {
            photoID = -1;
        }

        String myPath = users.get(userID).albums.get(albumID).getPhotos().get(photoID + 1).getFilePath();
        File myFile = new File(myPath);
        Image myImage = new Image(new FileInputStream(myFile));

        display_view.setImage(myImage);

        captionDisplay.setText(users.get(userID).albums.get(albumID).getPhotos().get(photoID + 1).getCaption());

        Calendar cal = users.get(userID).albums.get(albumID).getPhotos().get(photoID + 1).getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String formatted = format1.format(cal.getTime());

        datesDisplay.setText(formatted);

        photoID++;




        tagList.getItems().clear();
        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {
            if (users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName().equals(selectTagType.getValue().toString()))
            {
                tagList.getItems().addAll(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getTagValue());
            }
        }

        selectTagType.getItems().clear();
        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {
            selectTagType.getItems().add(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName());
        }

        selectTagType.getSelectionModel().selectFirst();
    }

    public void displayReverse() throws FileNotFoundException {


        if (photoID - 1 < 0)
        {
            photoID = users.get(userID).albums.get(albumID).getCount();
        }

        String myPath = users.get(userID).albums.get(albumID).getPhotos().get(photoID - 1).getFilePath();
        File myFile = new File(myPath);
        Image myImage = new Image(new FileInputStream(myFile));

        display_view.setImage(myImage);

        captionDisplay.setText(users.get(userID).albums.get(albumID).getPhotos().get(photoID - 1).getCaption());

        Calendar cal = users.get(userID).albums.get(albumID).getPhotos().get(photoID - 1).getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String formatted = format1.format(cal.getTime());

        datesDisplay.setText(formatted);

        photoID--;



        tagList.getItems().clear();
        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {
            if (users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName().equals(selectTagType.getValue().toString()))
            {

                tagList.getItems().addAll(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getTagValue());

            }
        }

        selectTagType.getItems().clear();

        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {
            selectTagType.getItems().add(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName());
        }

        selectTagType.getSelectionModel().selectFirst();
    }

    public void addCaption()
    {
        String cap = addCaptionField.getText();

        users.get(userID).albums.get(albumID).getPhotos().get(photoID).setCaption(cap);

        captionDisplay.setText(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getCaption());

        addCaptionField.setText(null);

    }

    public void deleteCaption()
    {
        users.get(userID).albums.get(albumID).getPhotos().get(photoID).setCaption("");
        addCaption();
    }

    public void addTagType(ActionEvent event)
    {
//
        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {
            if (createTagType.getText().equals(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName()))
            {
                return;
            }
        }

        users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().add(new Tag(createTagType.getText()));

        selectTagType.getItems().clear();

        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {
            selectTagType.getItems().add(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName());
        }

        selectTagType.getSelectionModel().selectLast();

        createTagType.setText(null);

    }

    public void deleteTagType()
    {

        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {
            if (selectTagType.getValue().toString().equals(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName()))
            {
                selectTagType.getValue().toString().equals(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().remove(i));
            }
        }

        selectTagType.getItems().clear();

        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {
            selectTagType.getItems().add(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName());
        }

        selectTagType.getSelectionModel().selectFirst();

    }

    public void addTag()
    {





        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {


            if (users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName().equals(selectTagType.getValue().toString()))
            {

                users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).setTagValue(tagValueField.getText());
            }

        }

        tagList.getItems().clear();
        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {

            if (users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName().equals(selectTagType.getValue().toString()))
            {

                tagList.getItems().addAll(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getTagValue());
            }
        }

        tagValueField.setText(null);

    }

    public void listTags(ActionEvent event)
    {
        tagList.getItems().clear();

        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {
            if (selectTagType.getValue() != null)
            {
                if (users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName().equals(selectTagType.getValue().toString()))
                {
                    tagList.getItems().addAll(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getTagValue());
                }
            }

        }
    }

    public void deleteTag(ActionEvent event)
    {

        boolean del = false;
        if (tagList.getSelectionModel().getSelectedItem() == null)
        {
            return;
        }

        String compare = tagList.getSelectionModel().getSelectedItem().toString();



        for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
        {
            for (int j = 0; j < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getTagValue().size(); j ++)
            {
                if (users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getTagValue().get(j).equals(compare))
                {
                    users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getTagValue().remove(j);
                    del = true;
                }
            }

        }

        if (del == true)
        {
            tagList.getItems().clear();

            for (int i = 0; i < users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().size(); i ++)
            {
                if (users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getName().equals(selectTagType.getValue().toString()))
                {
                    tagList.getItems().addAll(users.get(userID).albums.get(albumID).getPhotos().get(photoID).getTags().get(i).getTagValue());
                }
            }
        }

    }





    public void back(ActionEvent event) throws IOException
    {
        FileOutputStream fileOut = new FileOutputStream("Users.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        out.writeObject(users);
        out.close();
        fileOut.close();
        root = FXMLLoader.load(getClass().getResource("open.fxml"));
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

            stage = (Stage) display_backdrop.getScene().getWindow();
            stage.close();
        }
    }


}
