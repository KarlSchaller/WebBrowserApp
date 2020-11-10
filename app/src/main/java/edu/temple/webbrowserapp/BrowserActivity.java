package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.PageControlClickInterface, PageViewerFragment.LinkClickInterface, BrowserControlFragment.BrowserControlClickInterface {

    PageControlFragment pageControlFragment;
    PageViewerFragment pageViewerFragment;
    BrowserControlFragment browserControlFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            pageControlFragment = new PageControlFragment();
            pageViewerFragment = new PageViewerFragment();
            browserControlFragment = new BrowserControlFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .add(R.id.page_viewer, pageViewerFragment)
                    .add(R.id.browser_control, browserControlFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            pageControlFragment = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control);
            pageViewerFragment = (PageViewerFragment) getSupportFragmentManager().findFragmentById(R.id.page_viewer);
            browserControlFragment = (BrowserControlFragment) getSupportFragmentManager().findFragmentById(R.id.browser_control);
        }
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

    @Override
    public void onNewPageClick() {

    }
}