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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageListFragment extends Fragment {

    private static final String ARG_PAGE_TITLES = "page-titles";
    private String[] mPageTitles;

    PageListInterface parentActivity;

    public PageListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pageTitles String Array of page titles currently being displayed by a PageViewerFragment
     * @return A new instance of fragment PageListFragment.
     */
    public static PageListFragment newInstance(String[] pageTitles) {
        PageListFragment fragment = new PageListFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PAGE_TITLES, pageTitles);
        fragment.setArguments(args);
        return fragment;
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
        if (getArguments() != null) {
            mPageTitles = getArguments().getStringArray(ARG_PAGE_TITLES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_page_list, container, false);

        final ListView listView = view.findViewById(R.id.listView);
        BaseAdapter baseAdapter = new BaseAdapter() {
            Context context = view.getContext();

            @Override
            public int getCount() {
                return mPageTitles.length;
            }

            @Override
            public Object getItem(int position) {
                return mPageTitles[position];
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
                textView.setText(mPageTitles[position]);

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

    interface PageListInterface {
        void onListClick(int position);
    }
}