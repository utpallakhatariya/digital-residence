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
import com.example.digitalresidence.SQLiteDatabases.EventDatabase.EventDatabaseHelper;
import com.example.digitalresidence.SQLiteDatabases.EventDatabase.EventModel;
import com.example.digitalresidence.SQLiteDatabases.EventDatabase.EventRecyclerAdapter;
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
public class FragmentEvent extends Fragment {
    FloatingActionButton AddEventFloating;

    private RecyclerView EventRecyclerView;
    private EventRecyclerAdapter adapter;
    private List<EventModel> EventList = new ArrayList<>();
    private Context context;
    private EventModel model;
    private EventDatabaseHelper EventDb;
    private TextView event_dateText,eventFrom_timeText,eventTo_timeText,noEventView,EventTypeTextView;
    private Button event_selectDateBtn,eventFrom_selectTimeBtn,eventTo_selectTimeBtn,ADD_EVENT_BTN,UpdateEventBtn;
    private EditText event_placeTextInput,event_descriptionTextInput,EventTypeEditText;
    ImageView noEventIV;
    DatePickerDialog datePickerDialog;
    int year,month,day;
    Calendar calendar;
    int mHour,mMinute;

    public FragmentEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fragment_event, container, false);

        noEventView = view.findViewById(R.id.noEventTXT);
        noEventIV = view.findViewById(R.id.noEventImage);

        EventRecyclerView = view.findViewById(R.id.event_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        EventRecyclerView.setLayoutManager(linearLayoutManager);
        EventDb = new EventDatabaseHelper(getContext());
        EventList.addAll(EventDb.getAllData());
        adapter = new EventRecyclerAdapter(context,EventList);
        EventRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        //recyclerView.addItemDecoration(dividerItemDecoration);
        EventRecyclerView.setAdapter(adapter);
        toggleEmptyComplains();
        EventRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                EventRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

        EventRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !AddEventFloating.isShown()){
                    AddEventFloating.show();
                }else if (dy > 0 && AddEventFloating.isShown()){
                    AddEventFloating.hide();
                }
            }
        });

        AddEventFloating=view.findViewById(R.id.event_add_floating);

        AddEventFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder AddEventBuilder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_event,
                        viewGroup, false);
                AddEventBuilder.setView(dialogView);
                final AlertDialog alertDialog = AddEventBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                EventDb=new EventDatabaseHelper(getActivity());
                event_placeTextInput= dialogView.findViewById(R.id.event_place_textInput);
                event_descriptionTextInput=dialogView.findViewById(R.id.event_description_textInput);
                event_dateText=dialogView.findViewById(R.id.event_date_txt);
                eventFrom_timeText=dialogView.findViewById(R.id.event_time_from_txt);
                eventTo_timeText=dialogView.findViewById(R.id.event_time_to_txt);
                event_selectDateBtn=dialogView.findViewById(R.id.event_date_btn);
                eventFrom_selectTimeBtn=dialogView.findViewById(R.id.event_time_from_btn);
                eventTo_selectTimeBtn=dialogView.findViewById(R.id.event_time_to_btn);
                EventTypeEditText=dialogView.findViewById(R.id.EventTypeEditText);
                EventTypeTextView=dialogView.findViewById(R.id.EventType_TXT);
                ADD_EVENT_BTN=dialogView.findViewById(R.id.confirm_event_btn);

                event_selectDateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar= Calendar.getInstance();
                        year= calendar.get(Calendar.YEAR);
                        month= calendar.get(Calendar.MONTH);
                        day= calendar.get(Calendar.DAY_OF_MONTH);
                        datePickerDialog= new DatePickerDialog(getActivity(),
                                R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int year, int month,
                                                          int dayOfMonth) {
                                        event_dateText.setText(dayOfMonth + "/" + (month + 1) +"/"+ year );
                                    }
                                },year,month,day);
                        datePickerDialog.show();
                    }
                });

                eventFrom_selectTimeBtn.setOnClickListener(new View.OnClickListener() {
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
                                        eventFrom_timeText.setText(s);
//                                        String AM_PM ;
//                                        if(hourOfDay < 12) {
//                                            AM_PM = "am";
//                                        } else {
//                                            AM_PM = "pm";
//                                        }
//                                        timeEdt.setText(hourOfDay + ":" + minute + " " + AM_PM);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });
                eventTo_selectTimeBtn.setOnClickListener(new View.OnClickListener() {
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
                                        eventTo_timeText.setText(s);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                ADD_EVENT_BTN.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        if (EventTypeEditText.getText().toString().equalsIgnoreCase("")){
                            EventTypeEditText.setError("Enter Event Type.");
                        }else if (event_placeTextInput.getText().toString().equalsIgnoreCase("")){
                            event_placeTextInput.setError("Enter Place.");
                        }else if (event_descriptionTextInput.getText().toString().equalsIgnoreCase("")){
                            event_descriptionTextInput.setError("Enter Description.");
                        }else if (event_dateText.length()==0){
                            Toast.makeText(getContext(),"Select Date",Toast.LENGTH_SHORT).show();
                        }else if (eventFrom_timeText.length()==0){
                            Toast.makeText(getContext(),"Select Time",Toast.LENGTH_SHORT).show();
                        }else if (eventTo_timeText.length()==0){
                            Toast.makeText(getContext(),"Select Time",Toast.LENGTH_SHORT).show();
                        }else {
                        boolean isInserted=EventDb.insertData(EventTypeEditText.getText().toString(),
                                event_placeTextInput.getText().toString(),
                                event_descriptionTextInput.getText().toString(),
                                event_dateText.getText().toString(),
                                eventFrom_timeText.getText().toString(),
                                eventTo_timeText.getText().toString());
                        EventList.clear();
                        toggleEmptyComplains();
                        if (isInserted==true) {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            EventRecyclerView.setLayoutManager(linearLayoutManager);
                            EventDb = new EventDatabaseHelper(getContext());
                            EventList.addAll(EventDb.getAllData());
                            adapter = new EventRecyclerAdapter(context,EventList);
                            EventRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            EventRecyclerView.setAdapter(adapter);
                            toggleEmptyComplains();
                            alertDialog.dismiss();
                            eventNotification();
                            Toast.makeText(getActivity(), "Event Added", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getActivity(),"Event Not Added",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.show();
            }
        });
    return view;
    }

    public void deleteEvents(final int position){
        EventDb.deleteEvent(EventList.get(position));
        EventList.remove(position);
        adapter.notifyItemRemoved(position);
        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
        toggleEmptyComplains();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{Html.fromHtml("<font color='#000000'>Edit</font>"),Html.fromHtml("<font color='#FF0000'>Delete</font>")};

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
                                    dialog.cancel();                                }
                            })
                            .setNegativeButton(Html.fromHtml("<font color='#FF0000'>Yes</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteEvents(position);
                                }
                            });
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                    // deleteComplaint(position);
                }
            }
        });
        builder.show();
/*
        AlertDialog.Builder OptionBuilder = new AlertDialog.Builder(getContext());
        OptionBuilder.setMessage("Choose option");
        OptionBuilder.setPositiveButton(Html.fromHtml("<font color='#000000'>Edit</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showUpdateDialog();
            }
        });
        OptionBuilder.setNegativeButton(Html.fromHtml("<font color='#FF0000'>Delete</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder DeleteBuilder = new AlertDialog.Builder(getContext());
                DeleteBuilder.setMessage("Are you sure you want to delete?");
                DeleteBuilder.setPositiveButton(Html.fromHtml("<font color='#000000'>NO</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                DeleteBuilder.setNegativeButton(Html.fromHtml("<font color='#FF0000'>YES</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                               deleteEvents(position);
                    }
                });
                DeleteBuilder.create().show();

            }
        });
        OptionBuilder.create().show();
*/
    }

    public void showUpdateDialog(final int position){
        AlertDialog.Builder AddEventBuilder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog_event,null);
        AddEventBuilder.setView(dialogView);
        final AlertDialog UpdateAlertDialog = AddEventBuilder.create();
        EventDb=new EventDatabaseHelper(getActivity());
        event_placeTextInput= dialogView.findViewById(R.id.event_place_textInput);
        event_descriptionTextInput=dialogView.findViewById(R.id.event_description_textInput);
        event_dateText=dialogView.findViewById(R.id.event_date_txt);
        eventFrom_timeText=dialogView.findViewById(R.id.event_time_from_txt);
        eventTo_timeText=dialogView.findViewById(R.id.event_time_to_txt);
        event_selectDateBtn=dialogView.findViewById(R.id.event_date_btn);
        eventFrom_selectTimeBtn=dialogView.findViewById(R.id.event_time_from_btn);
        eventTo_selectTimeBtn=dialogView.findViewById(R.id.event_time_to_btn);
        EventTypeEditText=dialogView.findViewById(R.id.EventTypeEditText);
        EventTypeTextView=dialogView.findViewById(R.id.EventType_TXT);
        UpdateEventBtn = dialogView.findViewById(R.id.confirm_event_btn);
        UpdateEventBtn.setText(R.string.update_event);
        TextView dialogTitle = dialogView.findViewById(R.id.event_main_tv);
        dialogTitle.setText(R.string.update_event);
        event_selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar= Calendar.getInstance();
                year= calendar.get(Calendar.YEAR);
                month= calendar.get(Calendar.MONTH);
                day= calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog= new DatePickerDialog(getActivity(),
                        R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month,
                                          int dayOfMonth) {
                        event_dateText.setText(dayOfMonth + "/" + (month + 1) +"/"+ year );
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        eventFrom_selectTimeBtn.setOnClickListener(new View.OnClickListener() {
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
                        eventFrom_timeText.setText(s);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        eventTo_selectTimeBtn.setOnClickListener(new View.OnClickListener() {
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
                        eventTo_timeText.setText(s);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        UpdateEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateAlertDialog.dismiss();
            }
        });
            UpdateAlertDialog.show();
    }

    private void toggleEmptyComplains() {

        if (EventList.size() > 0) {
            noEventView.setVisibility(View.GONE);
            noEventIV.setVisibility(View.GONE);
        } else {
            noEventView.setVisibility(View.VISIBLE);
            noEventIV.setVisibility(View.VISIBLE);
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
    private void eventNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        Intent i = new Intent(getContext(), DashboardScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("New Event");
        mBuilder.setContentText("Tap to see event");
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
