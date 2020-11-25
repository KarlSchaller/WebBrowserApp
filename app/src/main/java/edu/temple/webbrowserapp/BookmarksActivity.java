package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class BookmarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        final SharedPreferences sharedPreferences = getSharedPreferences("edu.temple.webbrowserapp.BOOKMARKS", Context.MODE_PRIVATE);
        final ArrayList<String> bookmarkTitles = new ArrayList<>(Objects.requireNonNull(sharedPreferences.getStringSet("bookmarkTitles", new HashSet<String>())));
        System.out.println(bookmarkTitles.toString());
        final ArrayList<String> bookmarkLinks = new ArrayList<>(Objects.requireNonNull(sharedPreferences.getStringSet("bookmarkLinks", new HashSet<String>())));
        System.out.println(bookmarkLinks.toString());
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

            @SuppressLint("InflateParams")
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater layoutInflater = getLayoutInflater();
                    convertView = layoutInflater.inflate(R.layout.bookmark_item, null, true);
                }
                TextView title = convertView.findViewById(R.id.content);
                ImageButton deleteButton = convertView.findViewById(R.id.imageButton);

                title.setText(bookmarkTitles.get(position));
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BookmarksActivity.this, BrowserActivity.class);
                        intent.putExtra("URL", bookmarkLinks.get(position));
                        startActivity(intent);
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(BookmarksActivity.this, R.style.Theme_AppCompat_Dialog_Alert)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Delete Bookmark")
                                .setMessage("Are you sure you want to delete this bookmark?")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        bookmarkTitles.remove(position);
                                        notifyDataSetChanged();
                                        HashSet<String> tempTitles = new HashSet<>(bookmarkTitles);
                                        sharedPreferences.edit().putStringSet("bookmarkTitles", tempTitles).apply();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .show();
                    }
                });

                return convertView;
            }
        });

        findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}