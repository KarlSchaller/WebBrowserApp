package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

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
        SharedPreferences sharedPreferences = getSharedPreferences("edu.temple.webbrowserapp.BOOKMARKS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear().commit();

        HashSet<String> bookmarkTitles = (HashSet<String>) sharedPreferences.getStringSet("bookmarkTitles", new HashSet<String>());
        assert bookmarkTitles != null;
        bookmarkTitles.add(getTitle().toString());
        editor.putStringSet("bookmarkTitles", bookmarkTitles);

        HashSet<String> bookmarkLinks = (HashSet<String>) sharedPreferences.getStringSet("bookmarkLinks", new HashSet<String>());
        assert bookmarkLinks != null;
        bookmarkLinks.add(pagerFragment.getPage().getUrl());
        editor.putStringSet("bookmarkLinks", bookmarkLinks);

        editor.apply();
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void onBookmarkListClick() {
        SharedPreferences sharedPreferences = getSharedPreferences("edu.temple.webbrowserapp.BOOKMARKS", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("count", sharedPreferences.getInt("count", 0)).commit();

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