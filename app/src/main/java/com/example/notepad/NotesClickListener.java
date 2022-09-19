package com.example.notepad;

import androidx.cardview.widget.CardView;

import com.example.notepad.Modles.Notes;

public interface NotesClickListener {
    void onClick(Notes notes);
    void onLongClick(Notes notes , CardView cardView);

}
