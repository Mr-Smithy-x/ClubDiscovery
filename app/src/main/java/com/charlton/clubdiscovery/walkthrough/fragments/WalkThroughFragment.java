package com.charlton.clubdiscovery.walkthrough.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlton.clubdiscovery.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WalkThroughFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WalkThroughFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalkThroughFragment extends Fragment {

    public static final String PARAM_TYPE = "TYPE";

    public static final int ADD_SCHOOL = 301;
    public static final int ADD_MAJOR = 302;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({PARAM_TYPE})
    public @interface WalkThrough{}

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ADD_MAJOR, ADD_SCHOOL})
    public @interface WalkThroughValue{}

    private OnFragmentInteractionListener mListener;

    public WalkThroughFragment() {
        // Required empty public constructor
    }

    public static WalkThroughFragment newInstance(@WalkThrough String key, @WalkThroughValue int value) {
        WalkThroughFragment fragment = new WalkThroughFragment();
        Bundle args = new Bundle();
        args.putInt(key, value);
        fragment.setArguments(args);
        return fragment;
    }

    int value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            value = getArguments().getInt(PARAM_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_walk_through, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int action, String value);
    }

}
