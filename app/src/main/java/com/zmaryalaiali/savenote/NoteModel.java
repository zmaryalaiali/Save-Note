package com.zmaryalaiali.savenote;

public class NoteModel {
    String title;
    String description;
    String createDate;
    String updateDate;

    public NoteModel() {
    }

    // our constructor
    public NoteModel(String title, String description, String createDate, String updateDate) {
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
    // getter and setter our class member
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
