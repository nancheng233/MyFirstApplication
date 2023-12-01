package com.jnu.student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomClockView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomClockView extends Fragment {


    public CustomClockView() {
        // Required empty public constructor
    }

    public static CustomClockView newInstance(String param1, String param2) {
        CustomClockView fragment = new CustomClockView();
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
        View rootView = inflater.inflate(R.layout.fragment_custom_clock_view, container, false);

        return rootView;
    }
}