package com.gabbar925.photoeditios.editor.featuresfoto.picker.entity;

public class Photo {

    /* renamed from: id */
    private int f189id;
    private String path;

    public Photo(int i, String str) {
        this.f189id = i;
        this.path = str;
    }

    public Photo() {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof Photo) && this.f189id == ((Photo) obj).f189id) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.f189id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public int getId() {
        return this.f189id;
    }

    public void setId(int i) {
        this.f189id = i;
    }
}
