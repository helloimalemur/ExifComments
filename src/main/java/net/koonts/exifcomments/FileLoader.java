package net.koonts.exifcomments;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileLoader {
    public String path;
    public char recursiveOption;
    private static final ArrayList<Path> filesList = new ArrayList<Path>();

    FileLoader() {}

    public void build_files_list() {
        Path dir = Paths.get(path);// path is dir

        try (DirectoryStream<Path> files = Files.newDirectoryStream(dir)) {
            files.forEach(et -> {//iterates over Directory stream
                try {
                    loadDirRecurisve(et);//seeks files, and is recursive when directory is found
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void loadDirRecurisve(Path e) throws IOException {//recursively drop through folders in search of files
        if (!Files.isDirectory(e)//if this potential file is not a directory
                && (e.toString().toLowerCase().endsWith(".nef")// and matches
                || e.toString().toLowerCase().endsWith(".jpg"))) {// a file type
            filesList.add(e); // add it to our temporary array
        } else if (Files.isDirectory(e)) { // otherwise, if it's a directory
            try(DirectoryStream<Path> fi = Files.newDirectoryStream(e))// make a Directory stream out of the directory
            {
                fi.forEach(eh -> {// iterate over this directory stream
                    try {
                        loadDirRecurisve(eh);//and implement recursion
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                });
            }
        }
    }

    public ArrayList<Path> getFilesList() {
        return filesList;
    }
    public void clearFilesList() {
        filesList.clear();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
