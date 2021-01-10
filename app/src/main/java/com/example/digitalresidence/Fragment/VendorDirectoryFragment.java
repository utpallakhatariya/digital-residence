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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.digitalresidence.SQLiteDatabases.VendorDatabase.VendorDatabaseHelper;
import com.example.digitalresidence.SQLiteDatabases.VendorDatabase.VendorModel;
import com.example.digitalresidence.SQLiteDatabases.VendorDatabase.VendorRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorDirectoryFragment extends Fragment {

    FloatingActionButton AddNewVendorFloatingBtn;
    private RecyclerView VendorRecyclerView;
    private VendorRecyclerAdapter vendorRecyclerAdapter;
    private List<VendorModel> VendorList = new ArrayList<>();
    private Context context;
    private VendorDatabaseHelper VendorDb;
    TextView VendorCategory_TV,VendorName_TV,VendorTime_TV;
    TextView noVendorView;
    EditText VendorCategory_ET,VendorName_ET;
    Button VendorSelectTimeBtn,ConfirmVendorBtn;

    int year,month,day;
    Calendar calendar;
    int mHour,mMinute;



    public VendorDirectoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
         v=inflater.inflate(R.layout.fragment_vendor_direcory, container, false);

        noVendorView=v.findViewById(R.id.noVendorTXT);

        context=getActivity();
        VendorRecyclerView = v.findViewById(R.id.vendorDirectory_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        VendorRecyclerView.setLayoutManager(linearLayoutManager);
        VendorDb = new VendorDatabaseHelper(getContext());
        VendorList.addAll(VendorDb.getAllVendor());
        vendorRecyclerAdapter = new VendorRecyclerAdapter(context,VendorList);
        VendorRecyclerView.setItemAnimator(new DefaultItemAnimator());
        VendorRecyclerView.setAdapter(vendorRecyclerAdapter);
        toggleEmptyVendors();
        VendorRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                VendorRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
        VendorRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !AddNewVendorFloatingBtn.isShown()){
                    AddNewVendorFloatingBtn.show();
                }else if (dy > 0 && AddNewVendorFloatingBtn.isShown()){
                    AddNewVendorFloatingBtn.hide();
                }
            }
        });
        AddNewVendorFloatingBtn=v.findViewById(R.id.vendor_floating);
        AddNewVendorFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder AddEventBuilder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_vendor,
                        viewGroup, false);
                AddEventBuilder.setView(dialogView);
                final AlertDialog alertDialog = AddEventBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                VendorDb=new VendorDatabaseHelper(getActivity());
                VendorCategory_TV=dialogView.findViewById(R.id.VendorCategory_TXT);
                VendorName_TV=dialogView.findViewById(R.id.Vendor_Name_TXT);
                VendorTime_TV=dialogView.findViewById(R.id.vendor_time_txt);
                VendorCategory_ET=dialogView.findViewById(R.id.VendorCategoryEditText);
                VendorName_ET=dialogView.findViewById(R.id.Vendor_name_editText);
                VendorSelectTimeBtn=dialogView.findViewById(R.id.vendor_time_btn);
                ConfirmVendorBtn=dialogView.findViewById(R.id.confirm_vendor_btn);
                VendorSelectTimeBtn.setOnClickListener(new View.OnClickListener() {
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
                                VendorTime_TV.setText(s);
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });
                ConfirmVendorBtn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (VendorCategory_ET.getText().toString().equalsIgnoreCase("")){
                            VendorCategory_ET.setError("Field can't be empty");
                        }else if (VendorName_ET.getText().toString().equalsIgnoreCase("")){
                            VendorName_ET.setError("Field can't be empty");
                        }else if (VendorTime_TV.length()==0){
                            Toast.makeText(getContext(),"Select time",Toast.LENGTH_SHORT).show();
                        }else {
                            boolean isInserted = VendorDb.insertVendor(VendorCategory_ET.getText().toString(),
                                    VendorName_ET.getText().toString(),
                                    VendorTime_TV.getText().toString());
                            VendorList.clear();
                            toggleEmptyVendors();
                            if (isInserted==true){
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                VendorRecyclerView.setLayoutManager(linearLayoutManager);
                                VendorDb = new VendorDatabaseHelper(getContext());
                                VendorList.addAll(VendorDb.getAllVendor());
                                vendorRecyclerAdapter = new VendorRecyclerAdapter(context,VendorList);
                                VendorRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                VendorRecyclerView.setAdapter(vendorRecyclerAdapter);
                                toggleEmptyVendors();
                                alertDialog.dismiss();
                                VendorNotification();
                                Toast.makeText(getActivity(), "Vendor Added", Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(getActivity(),"Vendor Not Added",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                alertDialog.show();
            }
        });

        DashboardScreen.toolbar.setTitle("Vendor");
         return v;
    }

    public void deleteVendors(final int position){
        VendorDb.deleteVendor(VendorList.get(position));
        VendorList.remove(position);
        vendorRecyclerAdapter.notifyItemRemoved(position);
        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
        toggleEmptyVendors();
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
                                    deleteVendors(position);
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        builder.show();
    }

    private void toggleEmptyVendors() {

        if (VendorList.size() > 0) {
            noVendorView.setVisibility(View.GONE);
        } else {
            noVendorView.setVisibility(View.VISIBLE);
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
    private void VendorNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        Intent i = new Intent(getContext(), DashboardScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("New Vendor");
        mBuilder.setContentText("Tap to see vendor");
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
