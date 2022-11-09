module exifcomments {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.imaging;
//    requires org.openjfx.javafxplugin;
//    requires org.beryx.jlink;
//    requires com.gluonhq.gluonfx-gradle-plugin;

    requires org.controlsfx.controls;

    opens exifcomments to javafx.fxml;
    exports exifcomments;
}