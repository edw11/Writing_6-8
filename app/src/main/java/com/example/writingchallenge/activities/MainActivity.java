package com.example.writingchallenge.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.writingchallenge.R;
import com.example.writingchallenge.adapter.NotesAdapter;
import com.example.writingchallenge.database.NotesDatabase;
import com.example.writingchallenge.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_NOTE = 1;

    private RecyclerView notesRecycleView;
    private List<Note> noteList;
    private NotesAdapter notesAdapter;
    TextView userProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageNote = findViewById(R.id.arrow);

        imageNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getApplicationContext(), CreateNoteActivity.class),
                        REQUEST_CODE_ADD_NOTE)
                ;
            }
        });
        userProfile = findViewById(R.id.name);
        ShowName();

        notesRecycleView = findViewById(R.id.notesRecyclerView);
        notesRecycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList);
        notesRecycleView.setAdapter(notesAdapter);

        getNotes();

    }
    private void getNotes (){

        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase.getDatabase(getApplicationContext()).noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if(noteList.size()==0){
                    noteList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();

                }else {
                    noteList.add(0,notes.get(0));
                    notesAdapter.notifyItemInserted(0);
                }notesRecycleView.smoothScrollToPosition(0);
            }
        }
        new GetNotesTask().execute();
    }

    public void ShowName(){
        Intent intent = getIntent();
        String nameUser = getIntent().getStringExtra("name");
        userProfile.setText(nameUser);
    }
}