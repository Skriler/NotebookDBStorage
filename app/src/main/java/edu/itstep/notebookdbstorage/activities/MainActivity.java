package edu.itstep.notebookdbstorage.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import edu.itstep.notebookdbstorage.R;

import edu.itstep.notebookdbstorage.entities.Note;
import edu.itstep.notebookdbstorage.fragments.NoteAddFragment;
import edu.itstep.notebookdbstorage.fragments.NoteDetailsFragment;
import edu.itstep.notebookdbstorage.fragments.NoteListFragment;
import edu.itstep.notebookdbstorage.services.DBService;
import edu.itstep.notebookdbstorage.services.NotebookService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String THEME_LIST_TAG = "themes";
    public static final String NOTE_NAME_LIST_TAG = "notes";
    public static final String NOTE_TAG = "note";

    DBService dbService;
    ArrayList<Note> notes;
    ArrayList<String> themes;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvDB = findViewById(R.id.tvDB);
        tvDB.setText(DBService.DATABASE_NAME);

        dbService = new DBService(this);
        themes = NotebookService.getThemes();
        notes = dbService.getNoteList();

        if (notes.isEmpty()) {
            notes = NotebookService.getNoteList();
            dbService.saveNoteList(notes);
        }

        setFragmentResultListeners();
        showNoteListFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbService.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFragmentResultListeners() {
        getSupportFragmentManager().setFragmentResultListener(
                "inputNodeRequest",
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String title = result.getString("title");
                        String theme = result.getString("theme");
                        String description = result.getString("description");
                        dbService.saveNote(new Note(title, theme, description));
                        notes = dbService.getNoteList();
                        showNoteListFragment();
                    }
                }
        );

        getSupportFragmentManager().setFragmentResultListener(
                "inputNoteNameRequest",
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String noteName = result.getString("noteName");
                        showNoteDetailsFragment(noteName);
                    }
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showNoteListFragment() {
        Bundle args = new Bundle();
        args.putSerializable(NOTE_NAME_LIST_TAG, NotebookService.getNoteNameList(notes));
        showCustomFragment(new NoteListFragment(), args);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showNoteDetailsFragment(String noteName) {
        Bundle args = new Bundle();
        args.putSerializable(NOTE_TAG, NotebookService.getNoteByName(notes, noteName));
        showCustomFragment(new NoteDetailsFragment(), args);
    }

    private void showNoteAddFragment() {
        Bundle args = new Bundle();
        args.putSerializable(THEME_LIST_TAG, themes);
        showCustomFragment(new NoteAddFragment(), args);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showNoteList:
                showNoteListFragment();
                break;
            case R.id.addNote:
                showNoteAddFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCustomFragment(Fragment fragment, Bundle args) {
        fragment.setArguments(args);
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frgContainerView, fragment)
                .commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnClose) {
            showNoteListFragment();
        }
    }
}