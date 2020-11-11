package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PageListFragment extends Fragment {

    PageListInterface parentActivity;
    ArrayList<String> mPageTitles;
    ListView listView;
    BaseAdapter baseAdapter;

    public PageListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PageListInterface)
            parentActivity = (PageListInterface) context;
        else
            throw new RuntimeException("You must implement the PageListInterface interface to attach this fragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageTitles = new ArrayList<>();
        mPageTitles.add("New Page");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_page_list, container, false);

        listView = view.findViewById(R.id.listView);
        baseAdapter = new BaseAdapter() {
            Context context = view.getContext();

            @Override
            public int getCount() {
                return mPageTitles.size();
            }

            @Override
            public Object getItem(int position) {
                return mPageTitles.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView;
                if (convertView == null) {
                    textView = new TextView(context);
                    textView.setTextSize(15);
                    textView.setPadding(5, 5, 5, 5);
                    textView.setHeight(100);
                }
                else
                    textView = (TextView) convertView;
                textView.setText(mPageTitles.get(position));

                return textView;
            }
        };
        listView.setAdapter(baseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parentActivity.onListClick(position);
            }
        });

        return view;
    }

    public void addTitle(String title) {
        mPageTitles.add(title);
        baseAdapter.notifyDataSetChanged();
    }

    public void setTitle(int index, String title) {
        mPageTitles.set(index, title);
        baseAdapter.notifyDataSetChanged();
    }

    interface PageListInterface {
        void onListClick(int position);
    }
}