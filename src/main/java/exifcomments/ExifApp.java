//package exifcomments;
//
//
//import org.apache.commons.imaging.ImageReadException;
//import org.apache.commons.imaging.ImageWriteException;
//
//import javax.swing.*;
//import javax.swing.filechooser.FileSystemView;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//public class ExifApp extends JFrame {
//    public final int WIDTH = 800;
//    public final int HEIGHT = 400;
//    ActionListener actionListener = this::considerInput;// method reference
//    //https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
//    ArrayList<Exif> exifArrayList = new ArrayList<>(); //array holding exif data
//    ArrayList<Exif> toWriteExifArrayList = new ArrayList<>(); //array holding exif data to be written
//    FileLoader fileLoader = new FileLoader(); //loads files from path chosen
//    ExifAppPanel exifAppPanel = new ExifAppPanel(actionListener); //initialize panel
//    public JFileChooser dirFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//    String osType;
//
//    ExifApp() {
//        //swing JFrame settings
//        setSize(new Dimension(WIDTH,HEIGHT)); // set window size and visibility
//        setVisible(true);
//        add(exifAppPanel); // add panel containing buttons and gui components
//
//        osType = System.getProperty("os.name");//get os type
//        System.out.println(System.getProperty("os.name"));
//
//        dirFileChooser.addActionListener(actionListener);// set file chooser options
//        dirFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//    }
//
//
//    public void considerInput(ActionEvent e) {//process buttons/gui components that have triggered our ActionListener
////        System.out.println("AL FIRED");
//        System.out.println(e.getSource().toString());
//        if (e.getSource().equals(exifAppPanel.previewExifWrite)) {//commit button
//            System.out.println("preview button");
//            exifWritePreview();
//        }
//        if (e.getSource().equals(exifAppPanel.commitButton)) {//commit button
//            System.out.println("commit button");
//            writeDesc();
//        }
//        if (e.getSource().equals(exifAppPanel.printExifs)) {//exif print button
//            System.out.println("exif print button");
//            printExifData();
//        }
//        if (e.getSource().equals(exifAppPanel.printPaths)) {//exif paths button
//            System.out.println("paths print button");
//            printExifFilesList();
//        }
//        if (e.getSource().equals(exifAppPanel.clearPaths)) {//exif paths button
//            System.out.println("clear paths button");
//            emptyExifFilesList();
//            //testing
////            System.out.println(exifAppPanel.descriptionExif.getText());
//            //
//        }
//        if (e.getSource().equals(exifAppPanel.workingDirChooserButton)) {//file chooser event triggered
//            workingDirChooser(e);
//        }
//
//    }
//
//    //https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
//    public void workingDirChooser(ActionEvent e) {// see considerInput()
//        emptyExifFilesList();
//        System.out.println("working dir chooser");
//        System.out.println(e.getActionCommand());
//        if (dirFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {//open file chooser and if is true if we chose something
//            fileLoader.setPath(String.valueOf(dirFileChooser.getSelectedFile()));//set fileloader path to the dir we selected
//            fileLoader.build_files_list('Y');//folder recursion (we haven't implemented the ability to disable yet)
//            fileLoader.getFilesList().forEach(ek -> {
//                Exif ekt = new Exif(ek);//if metadata is not null, add it to our array of Exif
//                if (ekt.metaData !=null) {
//                    exifArrayList.add(ekt);
//                }
//            });
//            fileLoader.clearFilesList();
//
//            for (Exif exif : exifArrayList) {//after checking for any null metadata, load tags into exif ArrayList
//                exif.loadExifTags();
//            }
//        }
//        printExifFilesList();//show files loaded in description text field
//    }
//
//    public void exifWritePreview() {
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
//    public void writeDesc() {
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
//
//    public void printExifData() {//print list of file paths currently loaded
//        String tmp = null;
//        exifAppPanel.descriptionExif.setText("");
//        exifArrayList.forEach(eg -> {
//            System.out.println(eg.metaData);
//            exifAppPanel.descriptionExif.setText(exifAppPanel.descriptionExif.getText() + "\n" + eg.metaData);
//
//        });
//    }
//    public void emptyExifFilesList() {//drop loaded files / empty array
//        exifArrayList.clear();
//    }
//
//    public void printExifFilesList() {//print list of file paths currently loaded
//        exifAppPanel.descriptionExif.setText("");
//        exifArrayList.forEach(exif -> {
//            System.out.println(exif.path.toString());
//            exifAppPanel.descriptionExif.setText(exifAppPanel.descriptionExif.getText() + "\n" + exif.path.toString());
//        });
//    }
//
//}
