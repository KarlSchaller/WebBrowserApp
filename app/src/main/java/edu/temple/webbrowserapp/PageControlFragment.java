package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageControlFragment extends Fragment {

    ButtonClickInterface parentActivity;
    EditText editText;

    public PageControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ButtonClickInterface)
            parentActivity = (ButtonClickInterface) context;
        else
            throw new RuntimeException("You must implement the ButtonClickInterface interface to attach this fragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_control, container, false);
        editText = view.findViewById(R.id.editTextURL);

        view.findViewById(R.id.imageButtonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.onBackClick();
            }
        });
        view.findViewById(R.id.imageButtonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.onNextClick();
            }
        });
        view.findViewById(R.id.imageButtonGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.onGoClick(editText.getText().toString());
            }
        });

        return view;
    }

    public void setText(String url) {
        editText.setText(url);
    }

    interface ButtonClickInterface {
        void onGoClick(String url);
        void onNextClick();
        void onBackClick();
    }
}