package edu.itstep.notebookdbstorage.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.itstep.notebookdbstorage.R;
import edu.itstep.notebookdbstorage.activities.MainActivity;

public class NoteListFragment extends Fragment {
    ArrayList<String> noteNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteNames = (ArrayList<String>) getArguments().getSerializable(MainActivity.NOTE_NAME_LIST_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        ArrayAdapter<String> adapterNotes = new ArrayAdapter<>(
                this.getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                noteNames
        );
        ListView lvNotes = view.findViewById(R.id.lvNotes);
        lvNotes.setAdapter(adapterNotes);

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putString("noteName", noteNames.get(position));
                getParentFragmentManager().setFragmentResult("inputNoteNameRequest", args);
            }
        });

        return view;
    }
}