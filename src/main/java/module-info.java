module com.example.fefu_javafx_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.unsupported.desktop;
    requires org.jsoup;
    requires com.google.gson;
    requires java.sql;


    opens com.example.fefu_javafx_2 to javafx.fxml;
    exports com.example.fefu_javafx_2;
//    opens com.example.fefu_javafx_2 to javafx.fxml;
}