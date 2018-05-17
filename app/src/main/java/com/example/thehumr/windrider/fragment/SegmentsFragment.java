package com.example.thehumr.windrider.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thehumr.windrider.R;

public class SegmentsFragment extends android.support.v4.app.Fragment {

    public SegmentsFragment() {
        // Required empty public constructor
    }

    public static SegmentsFragment newInstance() {
        SegmentsFragment fragment = new SegmentsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_segments, container, false);
    }
}
