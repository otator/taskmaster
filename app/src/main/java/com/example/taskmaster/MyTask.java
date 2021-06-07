package com.example.taskmaster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mytask")
public class MyTask {
    @PrimaryKey
    private Long id;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name="state")
    private String state;

    @ColumnInfo(name="imageName")
    private String imageName;

    @ColumnInfo(name="address")
    private String address;

    public MyTask(String title, String description, String state, String imageName, String address) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.imageName = imageName;
        this.address = address;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MyTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
