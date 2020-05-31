package com.example.notepad;

import android.text.Editable;

public class NoteItem {
    public String title;
    public String content;

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return title + " - " + content;
    }
}
