package edu.itstep.notebookdbstorage.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import edu.itstep.notebookdbstorage.R;
import edu.itstep.notebookdbstorage.activities.MainActivity;

public class NoteAddFragment extends Fragment {
    private ArrayList<String> themes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            themes = (ArrayList<String>) getArguments().getSerializable(MainActivity.THEME_LIST_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_add, container, false);

        EditText etTitle = view.findViewById(R.id.etTitle);
        Spinner spThemes = view.findViewById(R.id.spThemes);
        EditText etDescription = view.findViewById(R.id.etDescription);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        Button btnClose = view.findViewById(R.id.btnClose);

        if (themes != null) {
            ArrayAdapter<String> adapterThemes = new ArrayAdapter<>(this.getContext(), R.layout.custom_spinner_item, themes);
            spThemes.setAdapter(adapterThemes);
        }

        btnClose.setOnClickListener((MainActivity)this.getActivity());
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();

                args.putString("title", etTitle.getText().toString());
                args.putString("theme", spThemes.getSelectedItem().toString());
                args.putString("description", etDescription.getText().toString());

                getParentFragmentManager().setFragmentResult("inputNodeRequest", args);
            }
        });

        return view;
    }
}