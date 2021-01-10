package com.example.digitalresidence.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalresidence.DashboardScreen.DashboardScreen;
import com.example.digitalresidence.Fragment.DirectoryDB.DirectoryModel;
import com.example.digitalresidence.Fragment.DirectoryDB.DirectoryRecyclerAdapter;
import com.example.digitalresidence.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDirectory extends Fragment {

    public FragmentDirectory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fragment_directory, container, false);

        DirectoryModel[] directoryModels = new DirectoryModel[]{
//            new DirectoryModel("Society Member",R.drawable.ic_group_black_24dp),
            new DirectoryModel("Guest Survey",R.drawable.guest_survey_svg_thumb),
            new DirectoryModel("Visitor",R.drawable.ic_person_profile_default_24dp),
            new DirectoryModel("Vendor",R.drawable.vendor),
        };
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.directoryRecyclerView);
        DirectoryRecyclerAdapter adapter = new DirectoryRecyclerAdapter(directoryModels);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

  /*              if (position==0){
                    Fragment fragment=new MemberDirectoryFragment();
                    FragmentManager frag_manager=getFragmentManager();
                    FragmentTransaction Frag_Transaction=frag_manager.beginTransaction();
                    Frag_Transaction.replace(R.id.frame_layout,fragment);
                    Frag_Transaction.commit();
                }*/
                if (position==0){
                    Fragment fragment=new GuestDirectoryFragment();
                    FragmentManager frag_manager=getFragmentManager();
                    FragmentTransaction Frag_Transaction=frag_manager.beginTransaction();
                    Frag_Transaction.replace(R.id.frame_layout,fragment);
                    Frag_Transaction.commit();
                }
                if (position==1){
                    Fragment fragment=new VisitorDirectoryFragment();
                    FragmentManager frag_manager=getFragmentManager();
                    FragmentTransaction Frag_Transaction=frag_manager.beginTransaction();
                    Frag_Transaction.replace(R.id.frame_layout,fragment);
                    Frag_Transaction.commit();
                }
                if (position==2){
                    Fragment fragment=new VendorDirectoryFragment();
                    FragmentManager frag_manager=getFragmentManager();
                    FragmentTransaction Frag_Transaction=frag_manager.beginTransaction();
                    Frag_Transaction.replace(R.id.frame_layout,fragment);
                    Frag_Transaction.commit();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    return view;
    }

    @Override
    public void onResume() {
        DashboardScreen.toolbar.setTitle("Directory");
        super.onResume();
    }

    //touchListener for recyclerView
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        public interface ClickListener {
            void onClick(View view, int position);

            void onLongClick(View view, int position);
        }
    }
}
