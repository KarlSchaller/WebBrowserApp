package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PageViewerFragment extends Fragment implements Parcelable {

    public static final Creator<PageViewerFragment> CREATOR = new Creator<PageViewerFragment>() {
        @Override
        public PageViewerFragment createFromParcel(Parcel in) {
            return new PageViewerFragment(in);
        }

        @Override
        public PageViewerFragment[] newArray(int size) {
            return new PageViewerFragment[size];
        }
    };
    private WebView webView;
    private Bundle webViewBundle;
    private PageViewerInterface parentActivity;

    public PageViewerFragment() {
        // Required empty public constructor
    }

    protected PageViewerFragment(Parcel in) {
        webViewBundle = in.readBundle();
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
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                parentActivity.onPageLoad(PageViewerFragment.this, view.getUrl(), view.getTitle());
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
        if (webViewBundle != null)
            webView.restoreState(webViewBundle);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        webView.saveState(bundle);
        dest.writeBundle(bundle);
    }

    interface PageViewerInterface {
        void onPageLoad(PageViewerFragment pageViewerFragment, String url, String title);
    }
}