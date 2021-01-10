package com.example.digitalresidence.Fragment.DirectoryDB;

public class DirectoryModel {
    public String DirectoryTitle;
    public int DImage;

    public DirectoryModel(String directoryTitle, int DImage) {
        DirectoryTitle = directoryTitle;
        this.DImage = DImage;
    }

    public String getDirectoryTitle() {
        return DirectoryTitle;
    }

    public void setDirectoryTitle(String directoryTitle) {
        DirectoryTitle = directoryTitle;
    }

    public int getDImage() {
        return DImage;
    }

    public void setDImage(int DImage) {
        this.DImage = DImage;
    }
}
