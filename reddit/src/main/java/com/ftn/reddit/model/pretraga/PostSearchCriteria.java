package com.ftn.reddit.model.pretraga;

public class PostSearchCriteria {

    String text;

    String title;

    public PostSearchCriteria(String text, String title) {
        this.text = text;
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
