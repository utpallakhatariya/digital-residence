package com.example.digitalresidence.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digitalresidence.DashboardScreen.DashboardScreen;
import com.example.digitalresidence.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberDirectoryFragment extends Fragment {

    public MemberDirectoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v= inflater.inflate(R.layout.fragment_member, container, false);
        DashboardScreen.toolbar.setTitle("Society Member");
        return v;
    }
}
