package com.example.digitalresidence.Fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.digitalresidence.DashboardScreen.DashboardScreen;
import com.example.digitalresidence.R;
import com.example.digitalresidence.SQLiteDatabases.VendorDatabase.VendorRecyclerAdapter;
import com.example.digitalresidence.SQLiteDatabases.VisitorDatabase.VisitorDatabaseHelper;
import com.example.digitalresidence.SQLiteDatabases.VisitorDatabase.VisitorModel;
import com.example.digitalresidence.SQLiteDatabases.VisitorDatabase.VisitorRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitorDirectoryFragment extends Fragment {
    FloatingActionButton AddVisitorFloating;

    private RecyclerView VisitorRecyclerView;
    private VisitorRecyclerAdapter visitorRecyclerAdapter;
    private List<VisitorModel> VisitorList = new ArrayList<>();
    private Context context;
    private VisitorDatabaseHelper VisitorDb;
    TextView noVisitor_TV,VisitorName_TV,VisitorAddress_TV,VisitorPurpose_TV,VisitorTime_TV;
    EditText VisitorName_ET,VisitorAddress_ET,VisitorPurpose_ET;
    Button VisitorSelectTimeBtn,ConfirmVisitorBtn;
    Calendar calendar;
    int mHour,mMinute;


    public VisitorDirectoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_visitor, container, false);

        noVisitor_TV=view.findViewById(R.id.noVisitorTXT);
        VisitorRecyclerView = view.findViewById(R.id.visitor_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        VisitorRecyclerView.setLayoutManager(linearLayoutManager);
        VisitorDb = new VisitorDatabaseHelper(getContext());
        VisitorList.addAll(VisitorDb.getAllVisitor());
        visitorRecyclerAdapter = new VisitorRecyclerAdapter(context,VisitorList);
        VisitorRecyclerView.setItemAnimator(new DefaultItemAnimator());
        VisitorRecyclerView.setAdapter(visitorRecyclerAdapter);
        toggleEmptyVisitors();
        VisitorRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                VisitorRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
        VisitorRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !AddVisitorFloating.isShown()){
                    AddVisitorFloating.show();
                }else if (dy > 0 && AddVisitorFloating.isShown()){
                    AddVisitorFloating.hide();
                }
            }
        });
        AddVisitorFloating=view.findViewById(R.id.visitor_add_floating);
        AddVisitorFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder AddVisitorBuilder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_visitor,
                        viewGroup,false);
                AddVisitorBuilder.setView(dialogView);
                final AlertDialog alertDialog = AddVisitorBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                VisitorDb=new VisitorDatabaseHelper(getActivity());
                VisitorName_TV=dialogView.findViewById(R.id.Visitor_name_TXT);
                VisitorAddress_TV=dialogView.findViewById(R.id.Visitor_address_TXT);
                VisitorPurpose_TV=dialogView.findViewById(R.id.Visitor_purpose_TXT);
                VisitorTime_TV=dialogView.findViewById(R.id.visitor_time_txt);
                VisitorName_ET=dialogView.findViewById(R.id.VisitorName_EditText);
                VisitorAddress_ET=dialogView.findViewById(R.id.Visitor_address_editText);
                VisitorPurpose_ET=dialogView.findViewById(R.id.Visitor_purpose_editText);
                VisitorSelectTimeBtn=dialogView.findViewById(R.id.visitor_time_btn);
                ConfirmVisitorBtn=dialogView.findViewById(R.id.confirm_visitor_btn);

                VisitorSelectTimeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);


                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                R.style.DialogTheme,new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                Time time=new Time(hourOfDay,minute,0);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mma");
                                String s=simpleDateFormat.format(time);
                                VisitorTime_TV.setText(s);
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });
                ConfirmVisitorBtn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (VisitorName_ET.getText().toString().equalsIgnoreCase("")){
                            VisitorName_ET.setError("Enter Visitor Name.");
                        }else if (VisitorAddress_ET.getText().toString().equalsIgnoreCase("")){
                            VisitorAddress_ET.setError("Enter Visitor Address.");
                        }else if (VisitorPurpose_ET.getText().toString().equalsIgnoreCase("")){
                            VisitorPurpose_ET.setError("Enter Purpose of Visiting.");
                        }else if (VisitorTime_TV.length()==0){
                            Toast.makeText(getContext(),"Select Time",Toast.LENGTH_SHORT).show();
                        }else {
                            boolean isInserted= VisitorDb.insertVisitor(VisitorName_ET.getText().toString(),
                                    VisitorAddress_ET.getText().toString(),
                                    VisitorPurpose_ET.getText().toString(),
                                    VisitorTime_TV.getText().toString());
                            VisitorList.clear();
                            toggleEmptyVisitors();
                            if (isInserted==true){
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                VisitorRecyclerView.setLayoutManager(linearLayoutManager);
                                VisitorDb = new VisitorDatabaseHelper(getContext());
                                VisitorList.addAll(VisitorDb.getAllVisitor());
                                visitorRecyclerAdapter = new VisitorRecyclerAdapter(context,VisitorList);
                                VisitorRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                VisitorRecyclerView.setAdapter(visitorRecyclerAdapter);
                                toggleEmptyVisitors();
                                alertDialog.dismiss();
                                VisitorNotification();
                                Toast.makeText(getActivity(), "Visitor Added", Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(getActivity(),"Visitor Not Added",Toast.LENGTH_SHORT).show();
                            }
                        }
                });
                alertDialog.show();
            }
        });

        DashboardScreen.toolbar.setTitle("Visitor");
        return view;
    }

    public void deleteVisitors(final int position){
        VisitorDb.deleteVisitor(VisitorList.get(position));
        VisitorList.remove(position);
        visitorRecyclerAdapter.notifyItemRemoved(position);
        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
        toggleEmptyVisitors();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{Html.fromHtml("<font color='#000000'>Edit</font>"), Html.fromHtml("<font color='#FF0000'>Delete</font>")};

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    dialog.cancel();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to delete?")
                            .setCancelable(false)
                            .setPositiveButton(Html.fromHtml("<font color='#000000'>Cancel</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton(Html.fromHtml("<font color='#FF0000'>Yes</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteVisitors(position);
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    // deleteComplaint(position);
                }
            }
        });
        builder.show();
    }

    private void toggleEmptyVisitors() {

        if (VisitorList.size() > 0) {
            noVisitor_TV.setVisibility(View.GONE);
        } else {
            noVisitor_TV.setVisibility(View.VISIBLE);
        }
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void VisitorNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        Intent i = new Intent(getContext(), DashboardScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("New Visitor");
        mBuilder.setContentText("Tap to see visitor");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);


        NotificationManager mNotificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "01";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Digital Residence Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        mNotificationManager.notify(0, mBuilder.build());
    }
}
