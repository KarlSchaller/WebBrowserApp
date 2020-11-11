package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PagerFragment extends Fragment {

    PagerInterface parentActivity;
    ArrayList<PageViewerFragment> pageViewerFragments;
    ViewPager viewPager;
    FragmentStatePagerAdapter fragmentStatePagerAdapter;


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
        pageViewerFragments = new ArrayList<>();
        pageViewerFragments.add(new PageViewerFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        fragmentStatePagerAdapter = new FragmentStatePagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return pageViewerFragments.get(position);
            }

            @Override
            public int getCount() {
                return pageViewerFragments.size();
            }
        };

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

    public void setPage(int index) {
        if (index < pageViewerFragments.size())
            viewPager.setCurrentItem(index);
    }

    public void addPage() {
        pageViewerFragments.add(new PageViewerFragment());
        fragmentStatePagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(pageViewerFragments.size()-1);
    }

    public PageViewerFragment getPage() {
        return pageViewerFragments.get(viewPager.getCurrentItem());
    }

    public int getIndex() {
        return viewPager.getCurrentItem();
    }

    interface PagerInterface {
        void onPagerSelect(String url, String title);
    }
}