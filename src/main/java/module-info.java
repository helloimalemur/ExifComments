module exifcomments {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens exifcomments to javafx.fxml;
    exports exifcomments;
}