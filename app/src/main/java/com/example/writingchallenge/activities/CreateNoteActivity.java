package com.example.writingchallenge.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.writingchallenge.R;
import com.example.writingchallenge.database.NotesDatabase;
import com.example.writingchallenge.entities.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {


    private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
    private TextView textDateTime;
    Button generate;

    public static int challenge = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {onBackPressed();}});

        inputNoteTitle = findViewById(R.id.inputNoteTitle);
        inputNoteSubtitle = findViewById(R.id.inputNoteSubtitle);
        inputNoteText = findViewById(R.id.inputNote);
        textDateTime = findViewById(R.id.textDateTime);
        generate = (Button) findViewById(R.id.generate);
        textDateTime.setText(new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date()));

        ImageView imageSave = findViewById(R.id.imageSave);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputNoteTitle.setText("Generation 1");
               //MainActivity.challengeText.setText("you have completed " + (challenge = challenge + 1) + " challenge");
                challenge = challenge + 1;
               //MainActivity.challengeText.setText("you have completed " + challenge  + " challenge");
            }
        });


        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveNote();
                challenge = challenge + 1;
                MainActivity.challengeText.setText("you have completed " + (challenge-1)  + " challenge");
            }
        });
    }


    private  void saveNote(){
        if(inputNoteTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Note title can't be empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (inputNoteSubtitle.getText().toString().trim().isEmpty() && inputNoteText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note can't be empty", Toast.LENGTH_SHORT).show();
            return;

        }
        final Note note = new Note();
        note.setTitle(inputNoteTitle.getText().toString());
        note.setSubtitle(inputNoteSubtitle.getText().toString());
        note.setNoteText(inputNoteText.getText().toString());
        note.setDateTime(textDateTime.getText().toString());

        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getDatabase(getApplicationContext()).noteDao(). insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }

        new SaveNoteTask().execute();
    }
}