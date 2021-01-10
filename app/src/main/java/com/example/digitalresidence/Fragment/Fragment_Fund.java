package com.example.digitalresidence.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.digitalresidence.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Fund extends Fragment {



    public Fragment_Fund() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fragment__fund, container, false);

    return view;
    }

}
