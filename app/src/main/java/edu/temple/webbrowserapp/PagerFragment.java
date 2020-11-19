package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Objects;

public class PagerFragment extends Fragment {

    ViewPager viewPager;
    private MyAdapter fragmentStatePagerAdapter;
    private PagerInterface parentActivity;
    ArrayList<PageViewerFragment> pageViewerFragments;

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
        outState.putParcelableArrayList("list", pageViewerFragments);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            viewPager.onRestoreInstanceState(savedInstanceState.getParcelable("pager"));
            fragmentStatePagerAdapter.restoreState(savedInstanceState.getParcelable("adapter"), null);
            pageViewerFragments = savedInstanceState.getParcelableArrayList("list");
//            fragmentStatePagerAdapter.pageViewerFragments.clear();
//            fragmentStatePagerAdapter.pageViewerFragments.addAll(Objects.requireNonNull(savedInstanceState.<PageViewerFragment>getParcelableArrayList("list")));
//            fragmentStatePagerAdapter.pageViewerFragments.addAll(getChildFragmentManager().getFragments());
            fragmentStatePagerAdapter.notifyDataSetChanged();
        }
    }

    public void addPage() {
        pageViewerFragments.add(new PageViewerFragment());
        fragmentStatePagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(fragmentStatePagerAdapter.getCount() - 1);
    }

    public PageViewerFragment getPage() {
        return pageViewerFragments.get(getIndex());
    }

    public void setPage(int index) {
        if (index < fragmentStatePagerAdapter.getCount())
            viewPager.setCurrentItem(index);
    }

    public int getIndex() {
        return viewPager.getCurrentItem();
    }

    interface PagerInterface {
        void onPagerSelect(String url, String title);
    }

    private class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            pageViewerFragments = new ArrayList<>();
            pageViewerFragments.add(new PageViewerFragment());
            notifyDataSetChanged();
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

        @Override
        public int getItemPosition(@NonNull Object object) {
            if (pageViewerFragments.contains(object))
                return pageViewerFragments.indexOf(object);
            else
                return POSITION_NONE;
        }
    }
}