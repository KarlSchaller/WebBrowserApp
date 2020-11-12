package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PageControlFragment extends Fragment {

    private EditText editText;
    private PageControlInterface parentActivity;

    public PageControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PageControlInterface)
            parentActivity = (PageControlInterface) context;
        else
            throw new RuntimeException("You must implement the PageControlInterface interface to attach this fragment");
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
                String url = editText.getText().toString();
                url = URLUtil.guessUrl(url);
//                editText.setText(url);
                parentActivity.onGoClick(url);
            }
        });

        return view;
    }

    public void setText(String url) {
        editText.setText(url);
    }

    interface PageControlInterface {
        void onGoClick(String url);

        void onNextClick();

        void onBackClick();
    }
}