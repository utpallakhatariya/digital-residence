package com.example.digitalresidence.Fragment;


import android.app.DatePickerDialog;
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
import android.text.Html;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalresidence.DashboardScreen.DashboardScreen;
import com.example.digitalresidence.SQLiteDatabases.MeetingDatabase.MeetingDatabaseHelper;
import com.example.digitalresidence.SQLiteDatabases.MeetingDatabase.MeetingModel;
import com.example.digitalresidence.SQLiteDatabases.MeetingDatabase.MeetingRecyclerAdapter;
import com.example.digitalresidence.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMeeting extends Fragment {
    private RecyclerView MeetingRecyclerView;
    private MeetingRecyclerAdapter meetingRecyclerAdapter;
    private List<MeetingModel> MeetingList = new ArrayList<>();
    private Context context;
    private MeetingDatabaseHelper MeetingDb;
    TextView MeetingMainTitleTV,MeetingTitle_TV,MeetingPlace_TV,MeetingDescription_TV,MeetingDate_TV,MeetingTime_TV;
    EditText MeetingTitle_ET,MeetingPlace_ET,MeetingDescription_ET;
    TextView noMeetingTV;
    ImageView noMeetingIV;
    Button MeetingSelectDateBtn,MeetingSelectTimeBtn,ConfirmMeetingBtn;
    FloatingActionButton ArrangeMeetingBtn;
    DatePickerDialog MeetingdatePickerDialog;
    int year,month,day;
    Calendar calendar;
    int mHour,mMinute;


    public FragmentMeeting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fragment_meeting, container, false);

        noMeetingTV= view.findViewById(R.id.no_meeting_txt);
        noMeetingIV= view.findViewById(R.id.noMeetingImage);

        MeetingRecyclerView=view.findViewById(R.id.meetingRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        MeetingRecyclerView.setLayoutManager(linearLayoutManager);
        MeetingDb = new MeetingDatabaseHelper(getContext());
        MeetingList.addAll(MeetingDb.getAllMeeting());
        meetingRecyclerAdapter = new MeetingRecyclerAdapter(context,MeetingList);
        MeetingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        MeetingRecyclerView.setAdapter(meetingRecyclerAdapter);
        toggleEmptyComplains();
        MeetingRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                MeetingRecyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        showActionsDialog(position);
                    }
                }));

        MeetingRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !ArrangeMeetingBtn.isShown()){
                    ArrangeMeetingBtn.show();
                }else if (dy > 0 && ArrangeMeetingBtn.isShown()){
                    ArrangeMeetingBtn.hide();
                }
            }
        });

        ArrangeMeetingBtn=view.findViewById(R.id.arrange_meeting_btn);
        ArrangeMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_meeting,
                        viewGroup, false);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                MeetingDb=new MeetingDatabaseHelper(getActivity());
                MeetingMainTitleTV=dialogView.findViewById(R.id.meeting_main_tv);
                MeetingTitle_TV=dialogView.findViewById(R.id.meeting_title_TXT);
                MeetingTitle_ET=dialogView.findViewById(R.id.meeting_title_editText);
                MeetingPlace_TV=dialogView.findViewById(R.id.meeting_place_TXT);
                MeetingPlace_ET=dialogView.findViewById(R.id.meeting_place_editText);
                MeetingDescription_TV=dialogView.findViewById(R.id.meeting_description_TXT);
                MeetingDescription_ET=dialogView.findViewById(R.id.meeting_description_editText);
                MeetingDate_TV=dialogView.findViewById(R.id.date_txt_meeting);
                MeetingTime_TV=dialogView.findViewById(R.id.time_txt_meeting);
                MeetingSelectDateBtn=dialogView.findViewById(R.id.meeting_date_btn);
                MeetingSelectTimeBtn=dialogView.findViewById(R.id.meeting_time_btn);
                ConfirmMeetingBtn=dialogView.findViewById(R.id.confirm_meeting_btn);

                MeetingSelectDateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar= Calendar.getInstance();
                        year= calendar.get(Calendar.YEAR);
                        month= calendar.get(Calendar.MONTH);
                        day= calendar.get(Calendar.DAY_OF_MONTH);
                        MeetingdatePickerDialog= new DatePickerDialog(getActivity(),
                                R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int year, int month,
                                                          int dayOfMonth) {
                                        MeetingDate_TV.setText(dayOfMonth + "/" + (month + 1) +"/"+ year );
                                    }
                                },year,month,day);
                        MeetingdatePickerDialog.show();
                    }
                });

                MeetingSelectTimeBtn.setOnClickListener(new View.OnClickListener() {
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
                                        MeetingTime_TV.setText(s);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });
                ConfirmMeetingBtn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (MeetingTitle_ET.getText().toString().equalsIgnoreCase("")) {
                            MeetingTitle_ET.setError("Enter Meeting Title");
                        } else if (MeetingPlace_ET.getText().toString().equalsIgnoreCase("")) {
                            MeetingPlace_ET.setError("Enter Meeting Place");
                        } else if (MeetingDescription_ET.getText().toString().equalsIgnoreCase("")) {
                            MeetingDescription_ET.setError("Enter Meeting Description");
                        } else if (MeetingDate_TV.length() == 0) {
                            Toast.makeText(getContext(),"Select Date",Toast.LENGTH_SHORT).show();
                        } else if (MeetingTime_TV.length() == 0) {
                            Toast.makeText(getContext(),"Select Time",Toast.LENGTH_SHORT).show();
                        } else {
                            boolean isInserted = MeetingDb.insertMeeting(MeetingTitle_ET.getText().toString(),
                                    MeetingPlace_ET.getText().toString(),
                                    MeetingDescription_ET.getText().toString(),
                                    MeetingDate_TV.getText().toString(),
                                    MeetingTime_TV.getText().toString());
                            MeetingList.clear();
                            toggleEmptyComplains();
                            if (isInserted == true) {
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                MeetingRecyclerView.setLayoutManager(linearLayoutManager);
                                MeetingDb = new MeetingDatabaseHelper(getContext());
                                MeetingList.addAll(MeetingDb.getAllMeeting());
                                meetingRecyclerAdapter = new MeetingRecyclerAdapter(context, MeetingList);
                                MeetingRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                MeetingRecyclerView.setAdapter(meetingRecyclerAdapter);
                                toggleEmptyComplains();
                                alertDialog.dismiss();
                                meetingNotification();
                                Toast.makeText(getActivity(), "Meeting Confirmed", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getActivity(), "Meeting Not Added", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                alertDialog.show();
            }
        });
        return view;
    }

    public void deleteMeetings(final int position){
        MeetingDb.deleteMeeting(MeetingList.get(position));
        MeetingList.remove(position);
        meetingRecyclerAdapter.notifyItemRemoved(position);
        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
        toggleEmptyComplains();
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
                                    deleteMeetings(position);
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        builder.show();
    }

    private void toggleEmptyComplains() {

        if (MeetingList.size() > 0) {
            noMeetingIV.setVisibility(View.GONE);
            noMeetingTV.setVisibility(View.GONE);
        } else {
            noMeetingIV.setVisibility(View.VISIBLE);
            noMeetingTV.setVisibility(View.VISIBLE);
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
    private void meetingNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        Intent i = new Intent(getContext(), DashboardScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("New Meeting");
        mBuilder.setContentText("Tap to see meeting");
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
