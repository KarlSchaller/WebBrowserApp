package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BookmarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        SharedPreferences sharedPreferences = getSharedPreferences("edu.temple.webbrowserapp.BOOKMARKS", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final int count = sharedPreferences.getInt("count", 0);
        final ArrayList<String> bookmarkTitles = new ArrayList<>(Objects.requireNonNull(sharedPreferences.getStringSet("bookmarkTitles", new HashSet<String>())));
        final ArrayList<String> bookmarkLinks = new ArrayList<>(Objects.requireNonNull(sharedPreferences.getStringSet("bookmarkLinks", new HashSet<String>())));
        ListView bookmarkList = findViewById(R.id.bookmarkList);

        bookmarkList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return bookmarkTitles.size();
            }

            @Override
            public Object getItem(int position) {
                return bookmarkTitles.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                Context context = getBaseContext();
//                TextView textView;
//                if (convertView == null) {
//                    textView = new TextView(context);
//                    textView.setTextSize(20);
//                    textView.setPadding(8, 8, 8, 8);
//                    textView.setHeight(150);
//                }
//                else
//                    textView = (TextView) convertView;
//                textView.setText(bookmarkTitles[position]);
//
//                return textView;

                LayoutInflater layoutInflater = getLayoutInflater();
                View linearLayout = layoutInflater.inflate(R.layout.bookmark_item, null, true);
                TextView title = linearLayout.findViewById(R.id.content);
                ImageButton deleteButton = linearLayout.findViewById(R.id.imageButton);

                title.setText(bookmarkTitles.get(position));
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookmarkTitles.remove(position);
                        notifyDataSetChanged();
                        HashSet<String> tempTitles = new HashSet<>(bookmarkTitles);
                        editor.putStringSet("bookmarkTitles", tempTitles).apply();
                    }
                });

                return linearLayout;
            }
        });

        ListView deleteList = findViewById(R.id.deleteList);

        findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}