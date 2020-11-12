package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PagerFragment extends Fragment {

    PagerInterface parentActivity;
    ViewPager viewPager;
    MyAdapter fragmentStatePagerAdapter;


    public PagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PagerInterface)
            parentActivity = (PagerInterface) context;
        else
            throw new RuntimeException("You must implement the PagerInterface interface to attach this fragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        fragmentStatePagerAdapter = new MyAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(fragmentStatePagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                parentActivity.onPagerSelect(getPage().getUrl(), getPage().getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pager", viewPager.onSaveInstanceState());
        outState.putParcelable("adapter", fragmentStatePagerAdapter.saveState());
        outState.putParcelableArrayList("list", fragmentStatePagerAdapter.pageViewerFragments);
        System.out.println("========== FRAGMENT LIST ==============" + fragmentStatePagerAdapter.getCount());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            viewPager.onRestoreInstanceState(savedInstanceState.getParcelable("pager"));
            fragmentStatePagerAdapter.restoreState(savedInstanceState.getParcelable("adapter"), null);
            fragmentStatePagerAdapter.pageViewerFragments = savedInstanceState.getParcelableArrayList("list");
            fragmentStatePagerAdapter.notifyDataSetChanged();
        }
        System.out.println("========== FRAGMENT LIST ==============" + fragmentStatePagerAdapter.getCount());
    }

    public void setPage(int index) {
        if (index < fragmentStatePagerAdapter.getCount())
            viewPager.setCurrentItem(index);
    }

    public void addPage() {
        fragmentStatePagerAdapter.addPage();
        viewPager.setCurrentItem(fragmentStatePagerAdapter.getCount()-1);
    }

    public PageViewerFragment getPage() {
        return fragmentStatePagerAdapter.pageViewerFragments.get(getIndex());
    }

    public int getIndex() {
        return viewPager.getCurrentItem();
    }

    interface PagerInterface {
        void onPagerSelect(String url, String title);
    }

    private class MyAdapter extends FragmentStatePagerAdapter {

        public ArrayList<PageViewerFragment> pageViewerFragments;

        public MyAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            pageViewerFragments = new ArrayList<>();
            pageViewerFragments.add(new PageViewerFragment());
            this.notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return pageViewerFragments.get(position);
        }

        @Override
        public int getCount() {
            return pageViewerFragments.size();
        }

        public void addPage() {
            pageViewerFragments.add(new PageViewerFragment());
            this.notifyDataSetChanged();
        }
    }
}