package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.Serializable;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.ButtonClickInterface, PageViewerFragment.LinkClickInterface {

    PageControlFragment pageControlFragment;
    PageViewerFragment pageViewerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            pageControlFragment = new PageControlFragment();
            pageViewerFragment = new PageViewerFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .add(R.id.page_viewer, pageViewerFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            pageControlFragment = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control);
            pageViewerFragment = (PageViewerFragment) getSupportFragmentManager().findFragmentById(R.id.page_viewer);
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
}