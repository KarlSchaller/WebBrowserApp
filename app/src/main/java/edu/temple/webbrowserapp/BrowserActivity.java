package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.webkit.ClientCertRequest;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.ClickInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PageControlFragment pageControlFragment = new PageControlFragment();
        PageViewerFragment pageViewerFragment = new PageViewerFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.page_control, pageControlFragment)
                .add(R.id.page_viewer, pageViewerFragment)
                .commit();
    }

    @Override
    public void goClick() {

    }

    @Override
    public void nextClick() {

    }

    @Override
    public void backClick() {

    }
}