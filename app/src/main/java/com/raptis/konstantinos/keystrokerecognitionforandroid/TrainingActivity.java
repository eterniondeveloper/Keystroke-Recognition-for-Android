package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by konstantinos on 13/5/2016.
 */
public class TrainingActivity extends FragmentActivity {

    // variables
    public static final int TRAINING_COUNT = 10;
    private TrainingViewPagerAdapter trainingViewPagerAdapter;
    private ViewPager viewPager;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        trainingViewPagerAdapter = new TrainingViewPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.trainingViewPager);
        viewPager.setAdapter(trainingViewPagerAdapter);
    }

    // adapter
    public static class TrainingViewPagerAdapter extends FragmentStatePagerAdapter {

        // constructor
        public TrainingViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new TrainingObjectFragment();
            Bundle args = new Bundle();
            args.putInt(TrainingObjectFragment.ARG_OBJECT, position + 1); // Our object is just an integer :-P
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public int getCount() {
            return TRAINING_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    // fragment
    public static class TrainingObjectFragment extends Fragment {

        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_training_object, container, false);
            Bundle args = getArguments();



            ((TextView) rootView.findViewById(R.id.numberTextView)).setText(Integer.toString(args.getInt(ARG_OBJECT)) + "/" + TRAINING_COUNT);

            return rootView;
        }

    }
}
