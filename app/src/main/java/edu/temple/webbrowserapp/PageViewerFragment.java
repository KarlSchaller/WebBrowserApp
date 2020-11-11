package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageViewerFragment extends Fragment {

    PageViewerInterface parentActivity;
    WebView webView;

    public PageViewerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PageViewerInterface)
            parentActivity = (PageViewerInterface) context;
        else
            throw new RuntimeException("You must implement the PageViewerInterface interface to attach this fragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                parentActivity.onPageLoad(PageViewerFragment.this, url, view.getTitle());
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                parentActivity.onPageLoad(PageViewerFragment.this, url, view.getTitle());
                super.onPageFinished(view, url);
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
            webView.restoreState(savedInstanceState);
    }

    public void go(String url) {
        webView.loadUrl(url);
    }

    public void back() {
        webView.goBack();
    }

    public void next() {
        webView.goForward();
    }

    public String getUrl() {
        return webView.getUrl();
    }

    public String getTitle() {
        return webView.getTitle();
    }

    interface PageViewerInterface {
        void onPageLoad(PageViewerFragment pageViewerFragment, String url, String title);
    }
}