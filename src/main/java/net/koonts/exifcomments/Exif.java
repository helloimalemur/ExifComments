package net.koonts.exifcomments;


import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class Exif {
    String fileName;
    String OGcreationTime;
    String creationTime;
    String OGcreationDate;
    String creationDate;
    Path parentFolder;
    Path path;
    String OGLocation;
    String location;
    String OGLocationSpec;
    ImageMetadata metaData;
    ImageMetadata toBeWrittenMetaData;
    String newDescription;
    ArrayList<ImageMetadata.ImageMetadataItem> exifs = new ArrayList<>();
    String locationDesc;


    Exif(Path path) {
        this.path = path;
        this.parentFolder = path.getParent();
        this.fileName = path.getFileName().toString();
    }

    public boolean loadExif() {
        if (path.toFile().exists() && !path.toFile().isDirectory()) {
            if ((path.toString().toLowerCase().endsWith(".nef") || path.toString().toLowerCase().endsWith(".jpg"))) {
                try {//TODO: check if corrupt/valid
                    metaData = Imaging.getMetadata(path.toFile());/////////////
                    return true;
                } catch (ImageReadException | IOException | IllegalArgumentException exception) {
                    System.out.println(exception.getMessage());
                    return false;
                }
            }
            return false;
        }

        //
        return false;
    }
    public void loadExifTags() {
        exifs.addAll(metaData.getItems());
//        jpegMetadata.findEXIFValueWithExactMatch(tagInfo)
//        exifs.forEach(imageMetadataItem -> System.out.println(imageMetadataItem + "\n"));
        exifs.forEach(imageMetadataItem -> {
            if (imageMetadataItem.toString().startsWith("UserComment")) {
                System.out.println(imageMetadataItem + "\n");
            }
        });

        exifs.forEach(imageMetadataItem -> {
            if (imageMetadataItem.toString().startsWith("DateTime")) {
//                creationDate = imageMetadataItem.toString();
                System.out.println(imageMetadataItem.toString());
                creationDate = imageMetadataItem.toString().split(" ")[1].substring(1);
                creationTime = imageMetadataItem.toString().split(" ")[2].substring(0,8);
                OGcreationDate = creationDate;
                OGcreationTime = creationTime;
                OGLocation = location;
                OGLocationSpec = locationDesc;


                /////////

            }
        });
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }
    public void writeNewDecription(String newDescription) throws ImageWriteException, IOException, ImageReadException {
        //write newDescription to UserComment tag

        System.out.println(path.toAbsolutePath().toString().substring(0,(path.toAbsolutePath().toString().lastIndexOf('.')))+"_tmp.NEF");
//        String tmp = path.toAbsolutePath().toString().substring(0,(path.toAbsolutePath().toString().lastIndexOf('.')))+"_tmp.NEF";
        String tmp = path.toAbsolutePath().toString().substring(0,(path.toAbsolutePath().toString().lastIndexOf('.')))+"_tmp.JPG";
        Path path2 = Path.of(tmp);
        changeExifMetadata(new File(path.toUri()), new File(path2.toUri()), newDescription);
    }


    public static void changeExifMetadata(final File imageFile, final File dst, String newDescription)
            throws IOException, ImageReadException, ImageWriteException {

        try (FileOutputStream fos = new FileOutputStream(dst);
             OutputStream os = new BufferedOutputStream(fos)) {

            TiffOutputSet outputSet = null;

            // note that metadata might be null if no metadata is found.
            final ImageMetadata metadata = Imaging.getMetadata(imageFile);
            try {
                final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                if (null != jpegMetadata) {
                    // note that exif might be null if no Exif metadata is found.
                    final TiffImageMetadata exif = jpegMetadata.getExif();

                    if (null != exif) {
                        // TiffImageMetadata class is immutable (read-only).
                        // TiffOutputSet class represents the Exif data to write.
                        //
                        // Usually, we want to update existing Exif metadata by
                        // changing
                        // the values of a few fields, or adding a field.
                        // In these cases, it is easiest to use getOutputSet() to
                        // start with a "copy" of the fields read from the image.
                        outputSet = exif.getOutputSet();
                    }
                }
            } catch (ImageWriteException e) {
                //write as tiff instead
                final TiffImageMetadata tiffImageMetadata = (TiffImageMetadata) metadata;
                if (tiffImageMetadata != null) {
                    outputSet = tiffImageMetadata.getOutputSet();
                }
            }

            if (null == outputSet) {
                outputSet = new TiffOutputSet();
            }

            {
                final TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
                // make sure to remove old value if present (this method will
                // not fail if the tag does not exist).
                exifDirectory.removeField(ExifTagConstants.EXIF_TAG_USER_COMMENT);
                if (Objects.equals(newDescription, "")) {

                    exifDirectory.add(ExifTagConstants.EXIF_TAG_USER_COMMENT, "");
                } else {
                    exifDirectory.add(ExifTagConstants.EXIF_TAG_USER_COMMENT, newDescription);
                }
            }
            //https://stackoverflow.com/questions/40241030/write-exif-data-to-tiff-file-using-apache-commons-imaging
            new ExifRewriter().updateExifMetadataLossless(imageFile, os,
                    outputSet);
            os.close();
            fos.close();
        }
    }


    public void setToBeWrittenMetaData() {
        //set new metadata

    }

    public void writeToBeWrittenMetaData() {
        //write new metadata
        //exif name "UserComment"
    }

    public boolean testloadExifTags() {

        String s = "";
        exifs.addAll(metaData.getItems());
        for (ImageMetadata.ImageMetadataItem imageMetadataItem : exifs) {

            if (imageMetadataItem.toString().startsWith("UserComment")) {
                s = imageMetadataItem.toString();
                System.out.println(imageMetadataItem + "\n");
                if (s.endsWith("'test'")) {
                    return true;
                } else {continue;}
            }
        }
        return false;
    }

    public void setLocation(String newLocation){
        this.location = newLocation;
    }
    public String getLocation(){
        return this.location;
    }



}
