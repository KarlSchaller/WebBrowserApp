package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.ButtonClickInterface, PageViewerFragment.LinkClickInterface {

    PageControlFragment pageControlFragment;
    PageViewerFragment pageViewerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pageControlFragment = new PageControlFragment();
        pageViewerFragment = new PageViewerFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.page_control, pageControlFragment)
                .add(R.id.page_viewer, pageViewerFragment)
                .commit();
    }

    @Override
    public void onGoClick(String url) {
        pageViewerFragment.go(url);
    }

    @Override
    public void onNextClick() {
        pageViewerFragment.next();
    }

    @Override
    public void onBackClick() {
        pageViewerFragment.back();
    }

    @Override
    public void onLinkClick(String url) {
        pageControlFragment.setText(url);
    }
}