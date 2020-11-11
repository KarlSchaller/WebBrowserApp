package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.PageControlClickInterface, PageViewerFragment.LinkClickInterface, BrowserControlFragment.BrowserControlClickInterface, PageListFragment.PageListClickInterface, PagerFragment.PagerSwipeInterface {

    PageControlFragment pageControlFragment;
//    PageViewerFragment pageViewerFragment;
    PagerFragment pagerFragment;
    BrowserControlFragment browserControlFragment;
    PageListFragment pageListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            pageControlFragment = new PageControlFragment();
//            pageViewerFragment = new PageViewerFragment();
            pagerFragment = new PagerFragment();
            browserControlFragment = new BrowserControlFragment();
            pageListFragment = PageListFragment.newInstance(new String[]{"page1", "page2", "page3"});
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
//                    .add(R.id.page_display, pageViewerFragment)
                    .add(R.id.page_display, pagerFragment)
                    .add(R.id.browser_control, browserControlFragment)
                    .add(R.id.page_list, pageListFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            pageControlFragment = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control);
//            pageViewerFragment = (PageViewerFragment) getSupportFragmentManager().findFragmentById(R.id.page_display);
            pagerFragment = (PagerFragment) getSupportFragmentManager().findFragmentById(R.id.page_display);
            browserControlFragment = (BrowserControlFragment) getSupportFragmentManager().findFragmentById(R.id.browser_control);
            pageListFragment = (PageListFragment) getSupportFragmentManager().findFragmentById(R.id.page_list);
        }
    }

    @Override
    public void onGoClick(String url) {
        pagerFragment.go(url);
    }

    @Override
    public void onNextClick() {
        pagerFragment.next();
    }

    @Override
    public void onBackClick() {
        pagerFragment.back();
    }

    @Override
    public void onLinkClick(String url) {
        pageControlFragment.setText(url);
    }

    @Override
    public void onNewPageClick() {
        pagerFragment.addPage();
    }

    @Override
    public void onListClick(View view) {

    }

    @Override
    public void onSwipe() {

    }
}