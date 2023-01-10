module com.example.bro_comm {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bro_comm to javafx.fxml;
    exports com.example.bro_comm;
}