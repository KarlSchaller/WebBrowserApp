package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
        }
        else {
            pageControlFragment = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control);
            pagerFragment = (PagerFragment) getSupportFragmentManager().findFragmentById(R.id.page_display);
            browserControlFragment = (BrowserControlFragment) getSupportFragmentManager().findFragmentById(R.id.browser_control);
            pageListFragment = (PageListFragment) getSupportFragmentManager().findFragmentById(R.id.page_list);
        }
    }

    @Override
    public void onGoClick(String url) {
        pagerFragment.getPage().go(url);
    }

    @Override
    public void onNextClick() {
        pagerFragment.getPage().next();
    }

    @Override
    public void onBackClick() {
        pagerFragment.getPage().back();
    }

    @Override
    public void onNewPageClick() {
        pageListFragment.addTitle("New Page");
        pagerFragment.addPage();
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