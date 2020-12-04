package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class BookmarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        ListView bookmarkList = findViewById(R.id.bookmarkList);

        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        try {
            FileInputStream fileInputStream = openFileInput("BOOKMARKS");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            bookmarks = (ArrayList<Bookmark>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        final ArrayList<Bookmark> finalBookmarkTitles = bookmarks;
        bookmarkList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return finalBookmarkTitles.size();
            }

            @Override
            public Object getItem(int position) {
                return finalBookmarkTitles.get(position);
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

                title.setText(finalBookmarkTitles.get(position).title);
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(finalBookmarkTitles.get(position).link), BookmarksActivity.this, BrowserActivity.class));
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
                                        finalBookmarkTitles.remove(position);
                                        notifyDataSetChanged();
                                        try {
                                            FileOutputStream fileOutputStream = openFileOutput("BOOKMARKS", Context.MODE_PRIVATE);
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                                            objectOutputStream.writeObject(finalBookmarkTitles);
                                            objectOutputStream.close();
                                            fileOutputStream.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
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