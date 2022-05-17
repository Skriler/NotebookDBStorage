package edu.itstep.notebookdbstorage.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.itstep.notebookdbstorage.R;
import edu.itstep.notebookdbstorage.entities.Note;

public class NotebookService {
    public static ArrayList<String> getThemes() {
        ArrayList<String> themes = new ArrayList<>();

        themes.add("Music");
        themes.add("Fashion");
        themes.add("Games");
        themes.add("Comedy");
        themes.add("Travel");
        themes.add("Competition");
        themes.add("Influence");
        themes.add("Life");
        themes.add("Art");

        return themes;
    }

    public static int getThemeImage(String theme) {
        int img = 0;
        switch (theme) {
            case "Music":
                img = R.drawable.music;
                break;
            case "Fashion":
                img = R.drawable.fashion;
                break;
            case "Games":
                img = R.drawable.games;
                break;
            case "Comedy":
                img = R.drawable.comedy;
                break;
            case "Travel":
                img = R.drawable.travel;
                break;
            case "Competition":
                img = R.drawable.competition;
                break;
            case "Influence":
                img = R.drawable.influence;
                break;
            case "Life":
                img = R.drawable.life;
                break;
            case "Art":
                img = R.drawable.art;
                break;
        }
        return img;
    }

    public static ArrayList<Note> getNoteList() {
        ArrayList<Note> notes = new ArrayList<>();

        notes.add(new Note("First custom note", "Travel", "First custom description"));
        notes.add(new Note("Second custom note", "Comedy", "Second custom description"));
        notes.add(new Note("Third custom note", "Life", "Third custom description"));
        notes.add(new Note("Fourth custom note", "Influence", "Fourth custom description"));
        notes.add(new Note("Fifth custom note", "Music", "Fifth custom description"));

        return notes;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<String> getNoteNameList(ArrayList<Note> notes) {
        List<String> noteNameList = notes
                .stream()
                .map(Note::getTitle)
                .collect(Collectors.toList());

        return  (ArrayList<String>) noteNameList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Note getNoteByName(ArrayList<Note> notes, String noteName) {
        return notes
                .stream()
                .filter(n -> n.getTitle().equals(noteName))
                .findFirst()
                .orElse(null);
    }
}
