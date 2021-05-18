package com.example.taskmaster;

public class Task {
    private String title;
    private String description;
    private String state;

    public Task(String title, String description, String state) {
        this.title = title;
        this.description = description;
        this.state = state;
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

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
