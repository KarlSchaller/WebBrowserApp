package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    PagerSwipeInterface parentActivity;
    ArrayList<PageViewerFragment> pageViewerFragments;
    ViewPager viewPager;
    FragmentStatePagerAdapter fragmentStatePagerAdapter;


    public PagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PagerFragment newInstance(String param1, String param2) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PagerSwipeInterface)
            parentActivity = (PagerSwipeInterface) context;
        else
            throw new RuntimeException("You must implement the PagerSwipeInterface interface to attach this fragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewerFragments = new ArrayList<>();
        pageViewerFragments.add(new PageViewerFragment());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                parentActivity.onSwipe();
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

    public void go(String url) {
        pageViewerFragments.get(viewPager.getCurrentItem()).go(url);
    }

    public void back() {
        pageViewerFragments.get(viewPager.getCurrentItem()).back();
    }

    public void next() {
        pageViewerFragments.get(viewPager.getCurrentItem()).next();
    }

    interface PagerSwipeInterface {
        void onSwipe();
    }
}