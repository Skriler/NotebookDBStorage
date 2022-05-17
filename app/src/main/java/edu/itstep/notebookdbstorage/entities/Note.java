package edu.itstep.notebookdbstorage.entities;

import java.io.Serializable;

public class Note implements Serializable {
    private String title;
    private String theme;
    private String description;

    public Note(String title, String theme, String description) {
        this.title = title;
        this.theme = theme;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String text) {
        this.description = description;
    }
}
