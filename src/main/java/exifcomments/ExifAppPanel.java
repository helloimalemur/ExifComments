//package exifcomments;
////for reference only
//
//import javax.swing.*;
//import javax.swing.filechooser.FileSystemView;
//import java.awt.*;
//import java.awt.event.ActionListener;
//
//public class ExifAppPanel extends JPanel {
//    ActionListener actionListener;
//    public Button commitButton = new Button();
//    public Button printExifs = new Button();
//    public Button previewExifWrite = new Button();
//    public Button printPaths = new Button();
//    public Button clearPaths = new Button();
//    public Button workingDirChooserButton = new Button();
//    public JFileChooser dirFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//    public JTextArea descriptionExif = new JTextArea();
//    public JList<Exif> exifJList = new JList<>();
//    public JToggleButton recursionToggleButton = new JToggleButton();
//    public JToggleButton saveasToggleButton = new JToggleButton();
//    public JToggleButton overwriteToggleButton = new JToggleButton();
//
//    ExifAppPanel(ActionListener actionListener) {
//        this.actionListener = actionListener;
//        setPreferredSize(new Dimension(super.getWidth()-20,super.getHeight()-20));
//        this.setVisible(true);
//        this.setLayout(new FlowLayout());
//
//        //Preview exif write
//        previewExifWrite.setLabel("Preview Write");
//        previewExifWrite.addActionListener(actionListener);
//        add(previewExifWrite);
//
//        //commit button
//        commitButton.addActionListener(actionListener);
//        commitButton.setVisible(true);
//        commitButton.setLabel("Commit");
//        add(commitButton);
//
//
//        //print Exif data
//        printExifs.addActionListener(actionListener);
//        printExifs.setVisible(true);
//        printExifs.setLabel("Print Exifs");
//        add(printExifs);
//
//        //print paths
//        printPaths.addActionListener(actionListener);
//        printPaths.setVisible(true);
//        printPaths.setLabel("Print Paths");
//        add(printPaths);
//
//        //clear paths
//        clearPaths.addActionListener(actionListener);
//        clearPaths.setVisible(true);
//        clearPaths.setLabel("Clear Paths");
//        add(clearPaths);
//
//        //working directory
//        workingDirChooserButton.addActionListener(actionListener);
//        workingDirChooserButton.setVisible(true);
//        workingDirChooserButton.setLabel("Working Dir Chooser");
//        dirFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        add(workingDirChooserButton);
//
//        //Exif tags
//        add(exifJList);
//
//
//        //recursion toggle
//        add(recursionToggleButton);
//
//        //save as toggle
//        add(saveasToggleButton);
//
//        //overwrite toggle
//        add(overwriteToggleButton);
//
//        //description field
//        descriptionExif.setPreferredSize(new Dimension(680,300));
//        descriptionExif.setLineWrap(true);
//        descriptionExif.setWrapStyleWord(true);
//        descriptionExif.setAutoscrolls(true);
//        add(descriptionExif);
//    }
//
//}
