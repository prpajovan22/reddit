package com.ftn.reddit.model.pretraga;

public class CommentSerachCriteria {

    private String text;

    public CommentSerachCriteria(String text) {
        this.text = text;
    }
    public CommentSerachCriteria(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
