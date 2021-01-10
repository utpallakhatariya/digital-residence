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
import com.example.digitalresidence.SQLiteDatabases.GuestDatabase.GuestDatabaseHelper;
import com.example.digitalresidence.SQLiteDatabases.GuestDatabase.GuestModel;
import com.example.digitalresidence.SQLiteDatabases.GuestDatabase.GuestRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestDirectoryFragment extends Fragment {

    FloatingActionButton AddGuestFloating;

    private RecyclerView GuestRecyclerView;
    private GuestRecyclerAdapter guestRecyclerAdapter;
    private List<GuestModel> GuestList = new ArrayList<>();
    private Context context;
    private GuestDatabaseHelper GuestDb;
    private TextView GuestName_TV,GuestOwnerAddress_TV,GuestPurpose_TV,GuestCount_TV,GuestTime_TV,noGuestView;
    private EditText GuestName_ET,GuestOwnerAddress_ET,GuestPurpose_ET,GuestCount_ET;
    private Button GuestSelectTimeBtn,ConfirmGuestBtn;

    Calendar calendar;
    int mHour,mMinute;

    public GuestDirectoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_guest, container, false);

        noGuestView = v.findViewById(R.id.noGuestTXT);

        GuestRecyclerView = v.findViewById(R.id.guest_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        GuestRecyclerView.setLayoutManager(linearLayoutManager);
        GuestDb = new GuestDatabaseHelper(getContext());
        GuestList.addAll(GuestDb.getAllGuest());
        guestRecyclerAdapter = new GuestRecyclerAdapter(context, GuestList);
        GuestRecyclerView.setItemAnimator(new DefaultItemAnimator());
        GuestRecyclerView.setAdapter(guestRecyclerAdapter);
        toggleEmptyGuests();
        GuestRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                GuestRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
        GuestRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !AddGuestFloating.isShown()){
                    AddGuestFloating.show();
                }else if (dy > 0 && AddGuestFloating.isShown()){
                    AddGuestFloating.hide();
                }
            }
        });
        AddGuestFloating = v.findViewById(R.id.guest_add_floating);
        AddGuestFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        AlertDialog.Builder AddGuestBuilder = new AlertDialog.Builder(getContext());
        ViewGroup viewGroup = v.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_guest,
                viewGroup, false);
        AddGuestBuilder.setView(dialogView);
        final AlertDialog alertDialog = AddGuestBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        GuestDb = new GuestDatabaseHelper(getActivity());
        GuestName_TV = dialogView.findViewById(R.id.Guest_Name_TXT);
        GuestOwnerAddress_TV = dialogView.findViewById(R.id.GuestAddress_TXT);
        GuestPurpose_TV = dialogView.findViewById(R.id.GuestPurpose_TXT);
        GuestCount_TV = dialogView.findViewById(R.id.Guest_Count_TXT);
        GuestTime_TV = dialogView.findViewById(R.id.guest_time_txt);
        GuestName_ET = dialogView.findViewById(R.id.Guest_name_editText);
        GuestOwnerAddress_ET = dialogView.findViewById(R.id.GuestAddressEditText);
        GuestPurpose_ET = dialogView.findViewById(R.id.GuestPurposeEditText);
        GuestCount_ET = dialogView.findViewById(R.id.Guest_count_editText);
        GuestSelectTimeBtn = dialogView.findViewById(R.id.guest_time_btn);
        ConfirmGuestBtn = dialogView.findViewById(R.id.confirm_guest_btn);

        GuestSelectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        Time time = new Time(hourOfDay, minute, 0);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mma");
                        String s = simpleDateFormat.format(time);
                        GuestTime_TV.setText(s);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        ConfirmGuestBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (GuestName_ET.getText().toString().equalsIgnoreCase("")) {
                    GuestName_ET.setError("Enter Guest Name.");
                } else if (GuestOwnerAddress_ET.getText().toString().equalsIgnoreCase("")) {
                    GuestOwnerAddress_ET.setError("Enter Owner's Address.");
                } else if (GuestPurpose_ET.getText().toString().equalsIgnoreCase("")) {
                    GuestPurpose_ET.setError("Enter Purpose Of Visiting");
                } else if (GuestCount_ET.getText().toString().equalsIgnoreCase("")) {
                    GuestCount_ET.setError("Enter Number of Guest.");
                } else if (GuestTime_TV.length() == 0) {
                    Toast.makeText(getContext(), "Select Time", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = GuestDb.insertGuest(GuestName_ET.getText().toString(),
                            GuestOwnerAddress_ET.getText().toString(),
                            GuestPurpose_ET.getText().toString(),
                            GuestCount_ET.getText().toString(),
                            GuestTime_TV.getText().toString());
                    GuestList.clear();
                    toggleEmptyGuests();
                    if (isInserted == true) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        GuestRecyclerView.setLayoutManager(linearLayoutManager);
                        GuestDb = new GuestDatabaseHelper(getContext());
                        GuestList.addAll(GuestDb.getAllGuest());
                        guestRecyclerAdapter = new GuestRecyclerAdapter(context, GuestList);
                        GuestRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        GuestRecyclerView.setAdapter(guestRecyclerAdapter);
                        toggleEmptyGuests();
                        alertDialog.dismiss();
                        guestNotification();
                        Toast.makeText(getActivity(), "Guest Added", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), "Guest Not Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
            }
        });

        DashboardScreen.toolbar.setTitle("Guest Survey");
        return v;
    }

    public void deleteGuests(final int position){
        GuestDb.deleteGuest(GuestList.get(position));
        GuestList.remove(position);
        guestRecyclerAdapter.notifyItemRemoved(position);
        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
        toggleEmptyGuests();
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
                                    deleteGuests(position);
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


    private void toggleEmptyGuests() {

        if (GuestList.size() > 0) {
            noGuestView.setVisibility(View.GONE);
        } else {
            noGuestView.setVisibility(View.VISIBLE);

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
    private void guestNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        Intent i = new Intent(getContext(), DashboardScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("New Guest Arrived");
        mBuilder.setContentText("Tap to see guest");
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
