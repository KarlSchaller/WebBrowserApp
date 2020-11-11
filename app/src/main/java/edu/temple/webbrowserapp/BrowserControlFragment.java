package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BrowserControlFragment extends Fragment {

    BrowserControlInterface parentActivity;

    public BrowserControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BrowserControlInterface)
            parentActivity = (BrowserControlInterface) context;
        else
            throw new RuntimeException("You must implement the BrowserControlInterface interface to attach this fragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browser_control, container, false);

        view.findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.onNewPageClick();
            }
        });

        return view;
    }

    interface BrowserControlInterface {
        void onNewPageClick();
    }
}