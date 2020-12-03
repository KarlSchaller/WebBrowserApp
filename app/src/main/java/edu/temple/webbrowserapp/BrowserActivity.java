package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.PageControlInterface, BrowserControlFragment.BrowserControlInterface, PageListFragment.PageListInterface, PagerFragment.PagerInterface, PageViewerFragment.PageViewerInterface {

    PageControlFragment pageControlFragment;
    PagerFragment pagerFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            pageControlFragment = new PageControlFragment();
            pagerFragment = new PagerFragment();
            browserControlFragment = new BrowserControlFragment();
            pageListFragment = new PageListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .add(R.id.page_display, pagerFragment)
                    .add(R.id.browser_control, browserControlFragment)
                    .add(R.id.page_list, pageListFragment)
                    .addToBackStack(null)
                    .commit();
            setTitle("New Page");
        } else {
            pageControlFragment = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control);
            pagerFragment = (PagerFragment) getSupportFragmentManager().findFragmentById(R.id.page_display);
            browserControlFragment = (BrowserControlFragment) getSupportFragmentManager().findFragmentById(R.id.browser_control);
            pageListFragment = (PageListFragment) getSupportFragmentManager().findFragmentById(R.id.page_list);
            setTitle(savedInstanceState.getCharSequence("title"));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String url = intent.getStringExtra("URL");
        if (url != null) {
            onNewPageClick();
            onGoClick(url);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("title", getTitle());
    }

    @Override
    public void onGoClick(String url) {
        if (pagerFragment.viewPager.getAdapter() != null)
            pagerFragment.viewPager.getAdapter().notifyDataSetChanged();
        pagerFragment.getPage().go(url);
    }

    @Override
    public void onNextClick() {
        if (pagerFragment.viewPager.getAdapter() != null)
            pagerFragment.viewPager.getAdapter().notifyDataSetChanged();
        pagerFragment.getPage().next();
    }

    @Override
    public void onBackClick() {
        if (pagerFragment.viewPager.getAdapter() != null)
            pagerFragment.viewPager.getAdapter().notifyDataSetChanged();
        pagerFragment.getPage().back();
    }

    @Override
    public void onNewPageClick() {
        pageListFragment.addTitle("New Page");
        pagerFragment.addPage();
    }

    @Override
    public void onBookmarkClick() {
        if (pagerFragment.getPage().getUrl() != null) {
            ArrayList<Bookmark> bookmarks = new ArrayList<>();
            // GET
            try {
                FileInputStream fileInputStream = openFileInput("BOOKMARKS");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                bookmarks = (ArrayList<Bookmark>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            // UPDATE
            bookmarks.add(new Bookmark(getTitle().toString(), pagerFragment.getPage().getUrl()));
            // STORE
            try {
                FileOutputStream fileOutputStream = openFileOutput("BOOKMARKS", Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(bookmarks);
                objectOutputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void onBookmarkListClick() {
        Intent intent = new Intent(this, BookmarksActivity.class);
        startActivity(intent);
    }

    @Override
    public void onListClick(int position) {
        pagerFragment.setPage(position);
    }

    @Override
    public void onPagerSelect(String url, String title) {
        if (url == null)
            setTitle("New Page");
        else
            setTitle(title);
        pageControlFragment.setText(url);

    }

    @Override
    public void onPageLoad(PageViewerFragment pageViewerFragment, String url, String title) {
        if (pageViewerFragment == pagerFragment.getPage()) {
            setTitle(title);
            pageControlFragment.setText(url);
            pageListFragment.setTitle(pagerFragment.getIndex(), title);
        }
    }
}