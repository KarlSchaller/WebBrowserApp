package edu.temple.webbrowserapp;

import java.io.Serializable;

public class Bookmark implements Serializable {
    public String title, link;

    public Bookmark(String title, String link) {
        this.title = title;
        this.link = link;
    }
}
