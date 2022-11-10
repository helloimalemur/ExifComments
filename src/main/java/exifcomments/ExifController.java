package exifcomments;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExifController {
    @FXML
    public Label welcomeText;
    @FXML
    public Button clearButton;
    @FXML
    public Button clearSettingsButton;
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
    public TextField dateSetting = new TextField();
    @FXML
    public TextField folderNameSetting = new TextField();
    @FXML
    public TextField nameSetting = new TextField();
    @FXML
    public TextField locationSetting = new TextField();

    @FXML
    public TextArea previewExif = new TextArea();
    @FXML
    public TextArea previewTwoExif = new TextArea();

    @FXML
    public List<Exif> exifJList = new ArrayList<>();
    @FXML
    public ArrayList<Exif> exifArrayList = new ArrayList<>();
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
    public void clearButtonPressed() {
        emptyExifFilesList();
    }
    @FXML
    public void setClearSettingsButton() {
        dateSetting.clear();
        folderNameSetting.clear();
        nameSetting.clear();
        locationSetting.clear();
    }
    @FXML
    public void loadButtonPressed() {
        fileChooser();
    }
    @FXML
    public void writeButtonPressed() {}
    @FXML
    public void previewButtonPressed() {
        printExifFilesList();
        printExifData();
        exifDescriptionPreview();
    }


    private void fileChooser() {
//        emptyExifFilesList();
        System.out.println("working dir chooser");
//        String path = dirFileChooser.showOpenDialog(null).getAbsolutePath();
        String path = dirFileChooser.showDialog(null).getAbsolutePath();
        System.out.println(path);
        //open file chooser and if is true if we chose something
        fileLoader.setPath(path);//set fileloader path to the dir we selected
        fileLoader.build_files_list('Y');//folder recursion (we haven't implemented the ability to disable yet)
        fileLoader.getFilesList().forEach(ek -> {
            Exif ekt = new Exif(ek);//if metadata is not null, add it to our array of Exif
            boolean a = ekt.loadExif();
            if (ekt.metaData !=null && a) {
//                exifAppPanel.ifLocationSetting(ekt);
                exifArrayList.add(ekt);
            }
        });
        fileLoader.clearFilesList();

        for (Exif exif : exifArrayList) {//after checking for any null metadata, load tags into exif ArrayList
            exif.loadExifTags();
        }
        printExifFilesList();//show files loaded in description text field
//        exifDescriptionPreview();
    }





    public void exifDescriptionPreview() {
//        String newDescription = "";
        previewExif.setText("");
        exifArrayList.forEach(exif -> {
            updateInfo(exif);
            String newDescription =
                    ""
                            + exif.creationDate
                            + " : " + exif.parentFolder.getFileName().toString()//parent folder name
                            + " : " + exif.fileName
                            + " : " + exif.location;
            exif.setNewDescription(newDescription);
            previewExif.setText(previewExif.getText() + newDescription + "\n");//print tags as loading
        });
    }

    public String getLocationSetting(Exif exif) {
        return exif.toString();
    }
    public void writeDesc() {
        previewExif.setText("");
        for (Exif exif: exifArrayList) {
            System.out.println("Writing.. " + exif.path.toString());
            try {
                exif.writeNewDecription(exif.newDescription);
            } catch (ImageWriteException | IOException | ImageReadException e) {
                throw new RuntimeException(e);
            }
            System.out.println(exif.newDescription);
        }
    }

    public void printExifData() {//print list of file paths currently loaded
        String tmp = null;
        previewTwoExif.setText("");
        exifArrayList.forEach(eg -> {
            System.out.println(eg.metaData);
            previewTwoExif.setText(previewTwoExif.getText() + "\n" + eg.metaData);

        });
    }




    public void emptyExifFilesList() {//drop loaded files / empty array
        exifArrayList.clear();
        previewExif.clear();
        previewTwoExif.clear();
    }

    public void printExifFilesList() {//print list of file paths currently loaded
        previewExif.clear();
        exifArrayList.forEach(exif -> {
            System.out.println(exif.path.toString());
            previewExif.setText(previewExif.getText() + exif.path.toString() + "\n");
        });
        System.out.println("\n");
    }



    ///
    ///
    private void updateInfo(Exif exif) {
        ifLocationChanged(exif);
        ifDateChanged(exif);
        ifLocationSetting(exif);

    }

    public void ifDateChanged(Exif exif) {
        if (dateSetting.getText().length() > 0) {
            exif.creationDate = dateSetting.getText();
        } else if (Objects.equals(dateSetting.getText(), "")) {
            exif.creationDate = exif.OGcreationDate;
        }
    }
    public void ifLocationChanged(Exif exif) {
        if (locationSetting.getText().length() > 0) {
            exif.location = locationSetting.getText();
        } else if (Objects.equals(locationSetting.getText(), "")) {
            exif.location = exif.OGlocation;
        }
    }

    public void ifLocationSetting(Exif exif) {
        if (locationSetting.getText().length() != 0) {
            exif.location = locationSetting.getText();
        }
    }


}