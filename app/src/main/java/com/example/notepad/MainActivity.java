package com.example.notepad;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.notepad.Adapters.NotesListAdapter;
import com.example.notepad.Database.RoomDB;
import com.example.notepad.Modles.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    List<Notes> notes = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;
    SearchView searchView_home;
    Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.add);
        searchView_home = findViewById(R.id.searchView_home);

        database = RoomDB.getInstance(this);
        notes = database.mainDAO().getAll();

        updateRecycler(notes);

        fab_add.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NotesTakerActivity.class);
                //noinspection deprecation
              startActivityForResult(intent,101);
//                intent.putExtra(EXTRA_MESSAGE , intent); used to put an message while calling an activity
                      //startActivity(intent);

            }


        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }

            private void filter(String newText) {
                List<Notes> filteredList = new ArrayList<>();
                for (Notes singlNote : notes){
                    if(singlNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singlNote.getNotes().toLowerCase().contains(newText.toLowerCase())){
                        filteredList.add(singlNote);
                    }
                }
               notesListAdapter.filterList(filteredList);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101)
        {
            if(resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().insert(new_notes);

                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }
        else if(requestCode == 102){
            if (resultCode==Activity.RESULT_OK){
                Notes new_note = (Notes) data.getSerializableExtra("note");
                database.mainDAO().update(new_note.getID(), new_note.getTitle(), new_note.getNotes());
               notes.clear();
               notes.addAll(database.mainDAO().getAll());
               notesListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Notes> notes){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this, notes, notesClickListener);
         recyclerView.setAdapter(notesListAdapter);
}

private final NotesClickListener notesClickListener = new NotesClickListener() {
    @Override
    public void onClick(Notes notes) {
 Intent intent= new Intent(MainActivity.this,NotesTakerActivity.class);
 intent.putExtra("previous note",notes);
 startActivityForResult(intent,102);

    }

    @Override
    public void onLongClick(Notes notes, CardView cardView) {
    selectedNote = new Notes();
    selectedNote = notes;
    showPopup(cardView);
    }
};
    private void showPopup(CardView cardView){
        PopupMenu popupMenu = new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener( this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.pin:
                if(selectedNote.getPinned()){
                    database.mainDAO().pin(selectedNote.getID(),false);
                    Toast.makeText(MainActivity.this, "Unpinned", Toast.LENGTH_SHORT).show();
                }
                else {
                    database.mainDAO().pin(selectedNote.getID(),true);
                    Toast.makeText(MainActivity.this, "Pinned", Toast.LENGTH_SHORT).show();
                }
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete:
                database.mainDAO().delete(selectedNote);
                notes.remove(selectedNote);
                notesListAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "The Note is deleted", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;
        }

    }
}