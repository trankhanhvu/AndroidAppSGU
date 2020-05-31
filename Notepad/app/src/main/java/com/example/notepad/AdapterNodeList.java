package com.example.notepad;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

class AdapterNoteList extends ArrayAdapter<NoteItem> {
    Context context;
    //NoteItem items[];
    ArrayList<NoteItem> items = null;

    public AdapterNoteList(Context context, int layoutTobeInflated, ArrayList<NoteItem> items) {
        super(context, layoutTobeInflated, items);
        this.context = context;
        this.items = items;
    }
}

