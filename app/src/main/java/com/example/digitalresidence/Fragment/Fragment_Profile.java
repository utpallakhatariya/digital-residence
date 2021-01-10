package com.example.digitalresidence.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.digitalresidence.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Fragment_Profile extends Fragment {

    public Fragment_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment__profile, container, false);
        return view;
    }
}
