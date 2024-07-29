package com.gabbar925.photoeditios.editor.featuresfoto.puzzle.photopicker.model;

import androidx.annotation.NonNull;

public class ImageModel implements Comparable<ImageModel> {

    /* renamed from: id */
    int f194id;
    String name;
    String pathFile;
    String pathFolder;

    public ImageModel(String str, String str2, String str3) {
        this.name = str;
        this.pathFile = str2;
        this.pathFolder = str3;
    }

    public String getPathFile() {
        return this.pathFile;
    }

    public void setPathFile(String str) {
        this.pathFile = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPathFolder() {
        return this.pathFolder;
    }

    public void setPathFolder(String str) {
        this.pathFolder = str;
    }

    public int getId() {
        return this.f194id;
    }

    public void setId(int i) {
        this.f194id = i;
    }

    public int compareTo(@NonNull ImageModel imageModel) {
        return this.pathFolder.compareTo(imageModel.getPathFolder());
    }
}
