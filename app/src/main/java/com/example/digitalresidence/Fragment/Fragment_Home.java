package com.example.digitalresidence.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.digitalresidence.DashboardScreen.DashboardScreen;
import com.example.digitalresidence.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Home extends Fragment  {

    public class CustomAdapter extends BaseAdapter {
        private final Context context;
        private final String[] web;
        private ViewHolder holder;
        private int logos[];

        LayoutInflater inflater;

        public CustomAdapter(Context context, String[] web , ViewHolder holder,int[] logos){
            this.web=web;
            this.context = context;
            this. logos= logos;
            this.holder=holder;
            inflater = (LayoutInflater.from(context));
        }

        public CustomAdapter(Context context, String[] web, int[] logos /*Context context1, String[] web1*/) {
            this.context = context;
            this.web = web;
            this.logos=logos;

        }

        @Override
        public int getCount() {
            return logos.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            if(convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview, viewGroup, false);
                holder.textView = convertView.findViewById(R.id.gridText);
                holder.imageView = convertView.findViewById(R.id.icon1);
                //imageView.setImageResource(logos[position]);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
           // Log.d("TAG", "getItem: "+arrayList(position).get(Integer.parseInt("item")));
            holder.imageView.setImageResource(logos[position]);
            holder.textView.setText(web[position]);
            return convertView;
        }
        public class ViewHolder{

            public ImageView imageView;
            public TextView textView;
        }
    }





    GridView simpleGrid;

    String[] web={"Notice","Album","Maintenance Bill","Booking","Directory","Event","Fund","Meeting","Complaint Box","Contacts"};

    int logos[] = {R.drawable.ic_announcement_black_24dp, R.drawable.photo_album, R.drawable.money,
            R.drawable.tablet, R.drawable.directory, R.drawable.event, R.drawable.piggy_bank,
            R.drawable.meeting,R.drawable.complaint1,R.drawable.ic_contact_black_24dp};





    public Fragment_Home() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view;

        view = inflater.inflate(R.layout.fragment_fragment__home, container, false);




        simpleGrid= view.findViewById(R.id.gridView);
        CustomAdapter customAdapter = new CustomAdapter(getContext(),web,logos);
        simpleGrid.setAdapter(customAdapter);
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();


                if (position==0)
                {
                    DashboardScreen.toolbar.setTitle("Notice");
                    Fragment fragment=new Fragment_notice();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
                }
                if (position==1)
                {
                    DashboardScreen.toolbar.setTitle("Album");
                    Fragment fragment=new FragmentAlbum();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
                }
                if (position==2)
                {
                    DashboardScreen.toolbar.setTitle("Maintenance Bill");
                    Fragment fragment=new FragmentBill();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
                }
                if (position==3)
                {
                    DashboardScreen.toolbar.setTitle("Booking");
                    Fragment fragment=new FragmentBooking();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
                }
                if (position==4)
                {
                    DashboardScreen.toolbar.setTitle("Directory");
                    Fragment fragment=new FragmentDirectory();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
                }
                if (position==5)
                {
                    DashboardScreen.toolbar.setTitle("Event");
                    Fragment fragment=new FragmentEvent();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
                }
                if (position==6)
                {
                    DashboardScreen.toolbar.setTitle("Fund");
                    Fragment fragment=new Fragment_Fund();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
                }
                if (position==7)
                {
                    DashboardScreen.toolbar.setTitle("Meeting");
                    Fragment fragment=new FragmentMeeting();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
                }
//                if (position==8)
//                {
//                    DashboardScreen.toolbar.setTitle("Vendor");
//                    Fragment fragment=new FragmentVendor();
//                    activity.getSupportFragmentManager().beginTransaction().
//                            replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
//                }
                if (position==8)
                {
                    DashboardScreen.toolbar.setTitle("Complaint Box");
                    Fragment fragment=new Fragment_Complain();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
                }
                if (position==9)
                {
                    DashboardScreen.toolbar.setTitle("Contacts");
                    Fragment fragment=new ContactFragment();
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
                }

            }
        });


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        DashboardScreen.toolbar.setTitle("Home");
       // DashboardScreen.toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4F4887")));


    }
}
