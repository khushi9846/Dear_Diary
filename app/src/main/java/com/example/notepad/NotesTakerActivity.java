package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notepad.Modles.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity  {
EditText editText_Title,editText_notes;
ImageView imageview_save;
Notes notes;
boolean isprevious_note = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        imageview_save = findViewById(R.id.imageview_save);
        editText_Title = findViewById(R.id.editText_Title);
        editText_notes= findViewById(R.id.editText_notes);


       notes = new Notes();
       try {
           notes = (Notes) getIntent().getSerializableExtra("previous note");
           editText_Title.setText(notes.getTitle());
           editText_notes.setText(notes.getNotes());
           isprevious_note=true;
       }catch (Exception e){
           e.printStackTrace();
       }
        imageview_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_Title.getText().toString();
                String description = editText_notes.getText().toString();

                if(description.isEmpty()){
                    Toast.makeText(NotesTakerActivity.this, "PLEASE ADD YOUR NOTES!!!",Toast.LENGTH_SHORT).show();
                    return ;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if(!isprevious_note){
                    notes = new Notes();

                }
                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", notes);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}