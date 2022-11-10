package exifcomments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;

public class ExifController {
    @FXML
    public Label welcomeText;
    @FXML
    public Button clearButton;
    @FXML
    public Button loadButton = new Button();
    @FXML
    public Button writeButton = new Button();
    @FXML
    public Button previewButton = new Button();


    @FXML
    public Button commitButton = new Button();

    @FXML
    public Button printExifs = new Button();
    @FXML
    public Button previewExifWrite = new Button();
    @FXML
    public Button printPaths = new Button();
    @FXML
    public Button clearPaths = new Button();
    @FXML
    public Button workingDirChooserButton = new Button();
    @FXML
    public DirectoryChooser dirFileChooser = new DirectoryChooser();
    @FXML
    public TextArea descriptionExif = new TextArea();
    @FXML
    public List<Exif> exifJList = new ArrayList<>();
    @FXML
    public ToggleButton recursionToggleButton = new ToggleButton();
    @FXML
    public ToggleButton saveasToggleButton = new ToggleButton();
    @FXML
    public ToggleButton overwriteToggleButton = new ToggleButton();
    @FXML
    public FileLoader fileLoader = new FileLoader();
    String osType;



    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onFileChooserButtonClick() {
//        emptyExifFilesList();
        System.out.println("working dir chooser");
//        String path = dirFileChooser.showOpenDialog(null).getAbsolutePath();
        System.out.println(dirFileChooser.showDialog(null).getAbsolutePath());
////        if (dirFileChooser.showOpenDialog(this) == FileChooser.ExtensionFilter.) {//open file chooser and if is true if we chose something
//            fileLoader.setPath(String.valueOf(dirFileChooser.getSelectedFile()));//set fileloader path to the dir we selected
//            fileLoader.build_files_list('Y');//folder recursion (we haven't implemented the ability to disable yet)
//            fileLoader.getFilesList().forEach(ek -> {
//                Exif ekt = new Exif(ek);//if metadata is not null, add it to our array of Exif
//                if (ekt.metaData !=null) {
//                    exifArrayList.add(ekt);
//                }
//            });
//            fileLoader.clearFilesList();

//            for (Exif exif : exifArrayList) {//after checking for any null metadata, load tags into exif ArrayList
//                exif.loadExifTags();
//            }
        }
//        printExifFilesList();//show files loaded in description text field
//    }

////    public void printExifData() {//print list of file paths currently loaded
////        String tmp = null;
////        exifAppPanel.descriptionExif.setText("");
//        exifArrayList.forEach(eg -> {
//            System.out.println(eg.metaData);
//            exifAppPanel.descriptionExif.setText(exifAppPanel.descriptionExif.getText() + "\n" + eg.metaData);
//
//        });
//    }
//    public void emptyExifFilesList() {//drop loaded files / empty array
//        exifArrayList.clear();
//    }

//    public void printExifFilesList() {//print list of file paths currently loaded
//        exifAppPanel.descriptionExif.setText("");
//        exifArrayList.forEach(exif -> {
//            System.out.println(exif.path.toString());
//            exifAppPanel.descriptionExif.setText(exifAppPanel.descriptionExif.getText() + "\n" + exif.path.toString());
//        });
//    }


//        public void writeDesc() {
//        exifAppPanel.descriptionExif.setText("");
//        for (Exif exif: exifArrayList) {
//            System.out.println("Writing.. " + exif.path.toString());
//            try {
//                exif.writeNewDecription(exif.newDescription);
//            } catch (ImageWriteException | IOException | ImageReadException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(exif.newDescription);
//        }
//
//    }

//        public void exifWritePreview() {
////        String newDescription = "";
//        exifAppPanel.descriptionExif.setText("");
//        exifArrayList.forEach(exif -> {
//            String newDescription =
//                    ""
//                            + exif.creationDate
//                            + " : " + exif.parentFolder.getFileName().toString()//parent folder name
//                            + " : " + exif.fileName
//                            + " : LOCATION";
//            exif.setNewDescription(newDescription);
//            exifAppPanel.descriptionExif.setText(exifAppPanel.descriptionExif.getText() + "\n" + newDescription);//print tags as loading
//        });
//
//    }

}