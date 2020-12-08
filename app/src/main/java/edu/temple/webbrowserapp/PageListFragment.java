package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PageListFragment extends Fragment {

    private ListView listView;
    private MyAdapter baseAdapter;
    private PageListInterface parentActivity;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_page_list, container, false);
        listView = view.findViewById(R.id.listView);
        baseAdapter = new MyAdapter(view.getContext());

        listView.setAdapter(baseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parentActivity.onListClick(position);
            }
        });

        String url = getActivity().getIntent().getDataString();
        if (url != null)
            addTitle("New Page");

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelable("listView", listView.onSaveInstanceState());
        outState.putStringArrayList("titleList", baseAdapter.pageTitles);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
//            listView.onRestoreInstanceState(savedInstanceState.getParcelable("listView"));
            baseAdapter.pageTitles = savedInstanceState.getStringArrayList("titleList");
        }
    }

    public void addTitle(String title) {
        baseAdapter.addTitle(title);
    }

    public void setTitle(int index, String title) {
        baseAdapter.setTitle(index, title);
    }

    interface PageListInterface {
        void onListClick(int position);
    }

    private class MyAdapter extends BaseAdapter {

        Context context;
        ArrayList<String> pageTitles;

        public MyAdapter(Context context) {
            this.context = context;
            pageTitles = new ArrayList<>();
            pageTitles.add("New Page");
        }

        @Override
        public int getCount() {
            return pageTitles.size();
        }

        @Override
        public Object getItem(int position) {
            return pageTitles.get(position);
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
            } else
                textView = (TextView) convertView;
            textView.setText(pageTitles.get(position));

            return textView;
        }

        public void addTitle(String title) {
            pageTitles.add(title);
            baseAdapter.notifyDataSetChanged();
        }

        public void setTitle(int index, String title) {
            pageTitles.set(index, title);
            this.notifyDataSetChanged();
        }
    }
}