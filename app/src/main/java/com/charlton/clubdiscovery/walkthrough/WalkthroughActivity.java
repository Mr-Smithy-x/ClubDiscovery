package com.charlton.clubdiscovery.walkthrough;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.charlton.clubdiscovery.R;
import com.charlton.clubdiscovery.walkthrough.adapters.WalkThroughAdapter;
import com.charlton.clubdiscovery.walkthrough.fragments.WalkThroughFragment;

public class WalkthroughActivity extends AppCompatActivity implements WalkThroughFragment.OnFragmentInteractionListener {

    private ViewPager viewPager;
    private WalkThroughAdapter walkThroughAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walkthrough_activity);
    }

    @Override
    public void onFragmentInteraction(int action, String value) {

    }
}
