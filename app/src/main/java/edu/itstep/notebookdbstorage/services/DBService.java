package edu.itstep.notebookdbstorage.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.itstep.notebookdbstorage.entities.Note;

public class DBService {
    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_THEME = "theme";
    private static final String COLUMN_DESCRIPTION = "description";

    SQLiteDatabase db;

    public DBService(Context context) {
        db = context.openOrCreateDatabase("notebook.db", context.MODE_PRIVATE, null);
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (title TEXT, theme TEXT, description TEXT)"
        );
    }

    public ArrayList<Note> getNoteList() {
        ArrayList<Note> notes = new ArrayList<>();

        if(!db.isOpen())
            return notes;

        Cursor query = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + ";",
                null
        );

        if (query.moveToFirst()) {
            do {
                String title =  query.getString(0);
                String theme =  query.getString(1);
                String description =  query.getString(2);
                notes.add(new Note(title, theme, description));
            } while (query.moveToNext());
        }
        query.close();

        return notes;
    }

    public void saveNote(Note note) {
        if(!db.isOpen())
            return;

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, note.getTitle());
        cv.put(COLUMN_THEME, note.getTheme());
        cv.put(COLUMN_DESCRIPTION, note.getDescription());

        db.insert(TABLE_NAME, null, cv);
    }

    public void saveNoteList(ArrayList<Note> notes) {
        for (Note note : notes) {
            saveNote(note);
        }
    }

    public void close() {
        db.close();
    }
}
