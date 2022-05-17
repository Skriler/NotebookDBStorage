package edu.itstep.notebookdbstorage.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.itstep.notebookdbstorage.R;
import edu.itstep.notebookdbstorage.activities.MainActivity;
import edu.itstep.notebookdbstorage.entities.Note;
import edu.itstep.notebookdbstorage.services.NotebookService;

public class NoteDetailsFragment extends Fragment {
    Note note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable(MainActivity.NOTE_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_details, container, false);

        Button btnClose = view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener((MainActivity)this.getActivity());

        if (note != null) {
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            TextView tvTheme = view.findViewById(R.id.tvTheme);
            TextView tvDescription = view.findViewById(R.id.tvDescription);
            ImageView imgTheme = view.findViewById(R.id.imgTheme);

            tvTitle.setText(String.format("%s: %s", tvTitle.getText(), note.getTitle()));
            tvTheme.setText(String.format("%s: %s", tvTheme.getText(), note.getTheme()));
            tvDescription.setText(String.format("%s: %s", tvDescription.getText(), note.getDescription()));
            imgTheme.setImageResource(NotebookService.getThemeImage(note.getTheme()));
        }

        return view;
    }
}