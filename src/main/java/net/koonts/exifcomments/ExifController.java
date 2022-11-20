package net.koonts.exifcomments;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
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
    public TextField timeSetting = new TextField();
    @FXML
    public TextField folderNameSetting = new TextField();
    @FXML
    public TextField nameSetting = new TextField();
    @FXML
    public TextField locationSetting = new TextField();
    @FXML
    public TextField locationDescSetting = new TextField();

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
    public void clearSettingsButtonPressed() {
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
    public void writeButtonPressed() {
        writeDesc();
    }
    @FXML
    public void previewButtonPressed() {
        printExifFilesList();
//        printExifData();
        exifDescriptionPreview();
    }

    ///
    ///
    ///
    ///
    ///
    ///
    public void exifDescriptionPreview() {
        previewTwoExif.setText("");
        exifArrayList.forEach(exif -> {//iterate over list of Exif
            String[] fileNameString = ifFileNameChanged(exif).substring(0, ifFileNameChanged(exif).length()-4).split(" ");
            String[] folderNameString = ifFolderNameChanged(exif).substring(0, ifFolderNameChanged(exif).length()).split(" ");
            String newDescription =//create a new description String to be written to UserComment Exif tag of target file
                    ""
                            + ifDateChanged(exif)
                            + " : " + ifTimeChanged(exif)
                            + " : " + ifLocationChanged(exif)
                            + " : (" + fileNameString[fileNameString.length-1] // number at end of file name ex; 'bear creek 052.jpg' -> 052
                            + ", " + folderNameString[folderNameString.length-1] + ")" //number at end of folder name ex; 'N 0003' -> 0003
                            + " : " + ifLocationDescChanged(exif)

//                            + ifDateChanged(exif)
//                            + " : " + ifTimeChanged(exif)
//                            + " : " + ifLocationChanged(exif)
//                            + " : " + ifFileNameChanged(exif)
//                            + " : " + ifFolderNameChanged(exif)
//                            + " : " + ifLocationDescChanged(exif)
//
                    ;
            exif.setNewDescription(newDescription);//set description string
            previewTwoExif.setText(previewTwoExif.getText() + newDescription + "\n");//display string to be written in previewTwo field
        });
    }


    public void writeDesc() {
        previewExif.setText("");//clear preview
        for (Exif exif: exifArrayList) {//iterate over list of Exif
            System.out.println("Writing.. " + exif.path.toString());
            try {
                exif.writeNewDecription(exif.newDescription);//attempt to write new metadata
            } catch (ImageWriteException | IOException | ImageReadException e) {
                throw new RuntimeException(e);
            }
            System.out.println(exif.newDescription);
        }
    }

    public void printExifData() {//print list of file paths currently loaded
        previewTwoExif.setText("");
        exifArrayList.forEach(eg -> {
            System.out.println(eg.metaData);
            previewTwoExif.setText(previewTwoExif.getText() + "\n" + eg.metaData);

        });
    }

    public void emptyExifFilesList() {
        exifArrayList.clear();//clear Exif list
        previewExif.clear();
        previewTwoExif.clear();
    }
    public void printExifFilesList() {//print list of file paths currently loaded
        previewExif.clear();
        exifArrayList.forEach(exif -> {
//            System.out.println(exif.path.toString());
            previewExif.setText(previewExif.getText() + exif.path.toString() + "\n");
        });
//        System.out.println("\n");
    }

    private void fileChooser() {
//        emptyExifFilesList();
        System.out.println("working dir chooser");
        String path = String.valueOf(dirFileChooser.showDialog(null));//open chooser window
//        System.out.println(path);
        if (path!=null) {
            fileLoader.setPath(path);//set fileloader path to the dir we selected
            fileLoader.build_files_list();//call method which uses folder recursion to create files list
            fileLoader.getFilesList().forEach(ek -> {//iterate over files list
                Exif ekt = new Exif(ek);//Create a new Exif object for each file
                boolean a = ekt.loadExif();//attempt to load Exif data, if unsuccessful skip file

                if (ekt.metaData !=null && a) {//if metadata is not null
                    exifArrayList.add(ekt);//add it to our array of Exif
                    System.out.println("Loaded: " + ekt.path.toString());
                } else {System.out.println("NOT Loaded (no Exif data): " + ekt.path.toString());}
            });
        }
        fileLoader.clearFilesList();

        for (Exif exif : exifArrayList) {//after checking for any null metadata, load tags into exif ArrayList
            exif.loadExifTags();
        }
        printExifFilesList();//show files loaded in description text field
    }

    ///
    ///
    ///
    ///
    ///
    ///

    public String ifDateChanged(Exif exif) {
        if (dateSetting.getText().length() > 0 && dateSetting.getText()!=null) {
            return Objects.requireNonNullElse(dateSetting.getText(),"");

        } else if (Objects.equals(dateSetting.getText(), "") && dateSetting.getText()!=null) {
            return Objects.requireNonNullElse(exif.OGcreationDate, "");
        }
        return "";
    }
    public String ifTimeChanged(Exif exif) {
        if (timeSetting.getText().length() > 0 && timeSetting.getText()!=null) {
            return Objects.requireNonNullElse(timeSetting.getText(), "");
        } else if (Objects.equals(timeSetting.getText(), "") && timeSetting.getText()!=null) {
            return Objects.requireNonNullElse(exif.OGcreationTime, "");
        }
        return "";
    }
    public String ifFileNameChanged(Exif exif) {
        if (nameSetting.getText().length() > 0 && nameSetting.getText()!= null) {
            return Objects.requireNonNullElse(nameSetting.getText(), "");
        } else if (Objects.equals(nameSetting.getText(), "") && nameSetting.getText()!= null) {
            return Objects.requireNonNullElse(exif.fileName, "");
        }
        return "";
    }
    public String ifFolderNameChanged(Exif exif) {
        if (folderNameSetting.getText().length() > 0 && folderNameSetting.getText()!=null) {
            return Objects.requireNonNullElse(folderNameSetting.getText(), "");
        } else if (Objects.equals(folderNameSetting.getText(), "") && folderNameSetting.getText()!=null) {
            return Objects.requireNonNullElse(exif.parentFolder.getFileName().toString(), "");
        }
        return "";
    }
    public String ifLocationChanged(Exif exif) {
        if (locationSetting.getText().length() > 0 && locationSetting.getText()!=null) {
            return Objects.requireNonNullElse(locationSetting.getText(), "");
        } else if (Objects.equals(locationSetting.getText(), "") && locationSetting.getText()!=null) {
            return Objects.requireNonNullElse(exif.OGLocation, "");
        }
        return "";
    }
    public String ifLocationDescChanged(Exif exif) {
        if (locationDescSetting.getText().length() > 0 && locationDescSetting.getText()!=null) {
            return Objects.requireNonNullElse(locationDescSetting.getText(), "");
        } else if (Objects.equals(locationDescSetting.getText(), "") && locationDescSetting.getText()!=null) {
            return Objects.requireNonNullElse(exif.OGLocationSpec, "");
        }
        return "";
    }
}