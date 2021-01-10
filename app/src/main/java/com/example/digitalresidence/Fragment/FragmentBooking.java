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
import com.example.digitalresidence.SQLiteDatabases.BookingDatabase.BookingDatabaseHelper;
import com.example.digitalresidence.SQLiteDatabases.BookingDatabase.BookingModel;
import com.example.digitalresidence.SQLiteDatabases.BookingDatabase.BookingRecyclerAdapter;
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
public class FragmentBooking extends Fragment {
    private RecyclerView BookingRecyclerView;
    private BookingRecyclerAdapter bookingRecyclerAdapter;
    private List<BookingModel> BookingList = new ArrayList<>();
    private Context context;
    private BookingDatabaseHelper BookingDb;
    TextView noBookingTV;
    TextView BookingOf_TV,BookingFor_TV,BookingDescription_TV,BookingDateTV,BookingTimeFromTV,BookingTimeToTV;
    EditText BookingOf_ET,BookingFor_ET,BookingDescription_ET;
    Button BookingSelectDateBtn,BookingSelectTimeFromBtn,BookingSelectTimeToBtn,ConfirmBookingBtn;
    FloatingActionButton BookNowBtn;

    public FragmentBooking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fragment_booking, container, false);

        noBookingTV=view.findViewById(R.id.no_booking_text);

        BookingRecyclerView = view.findViewById(R.id.bookingRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        BookingRecyclerView.setLayoutManager(linearLayoutManager);
        BookingDb = new BookingDatabaseHelper(getContext());
        BookingList.addAll(BookingDb.getAllBooking());
        bookingRecyclerAdapter = new BookingRecyclerAdapter(context,BookingList);
        BookingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        BookingRecyclerView.setAdapter(bookingRecyclerAdapter);
        toggleEmptyBookings();
        BookingRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                BookingRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

        BookingRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView,newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !BookNowBtn.isShown()){
                    BookNowBtn.show();
                }else if (dy > 0 && BookNowBtn.isShown()){
                    BookNowBtn.hide();
                }
            }
        });

        BookNowBtn=view.findViewById(R.id.book_now_btn);
        BookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder bookingBuilder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_booking,
                        viewGroup,false);
                bookingBuilder.setView(dialogView);
                final AlertDialog alertDialog=bookingBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                BookingDb=new BookingDatabaseHelper(getActivity());
                BookingOf_TV=dialogView.findViewById(R.id.booking_of_TXT);
                BookingFor_TV=dialogView.findViewById(R.id.booking_for_TXT);
                BookingDescription_TV=dialogView.findViewById(R.id.booking_description_TXT);
                BookingDateTV=dialogView.findViewById(R.id.booking_date_txt);
                BookingTimeFromTV=dialogView.findViewById(R.id.booking_time_from_txt);
                BookingTimeToTV=dialogView.findViewById(R.id.booking_time_to_txt);
                BookingOf_ET=dialogView.findViewById(R.id.booking_of_editText);
                BookingFor_ET=dialogView.findViewById(R.id.booking_for_editText);
                BookingDescription_ET=dialogView.findViewById(R.id.booking_description_editText);
                BookingSelectDateBtn=dialogView.findViewById(R.id.booking_date_btn);
                BookingSelectTimeFromBtn=dialogView.findViewById(R.id.booking_time_from_btn);
                BookingSelectTimeToBtn=dialogView.findViewById(R.id.booking_time_to_btn);
                ConfirmBookingBtn=dialogView.findViewById(R.id.confirm_booking_btn);

                BookingSelectDateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar= Calendar.getInstance();
                        int year= calendar.get(Calendar.YEAR);
                        int month= calendar.get(Calendar.MONTH);
                        int day= calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity(),
                                R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month,
                                                  int dayOfMonth) {
                                BookingDateTV.setText(dayOfMonth + "/" + (month + 1) +"/"+ year );
                            }
                        },year,month,day);
                        datePickerDialog.show();
                    }
                });
                BookingSelectTimeFromBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        int mHour = c.get(Calendar.HOUR_OF_DAY);
                        int mMinute = c.get(Calendar.MINUTE);


                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                R.style.DialogTheme,new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                Time time=new Time(hourOfDay,minute,0);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mma");
                                String s=simpleDateFormat.format(time);
                                BookingTimeFromTV.setText(s);
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });
                BookingSelectTimeToBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        int mHour = c.get(Calendar.HOUR_OF_DAY);
                        int mMinute = c.get(Calendar.MINUTE);


                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                R.style.DialogTheme,new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                Time time=new Time(hourOfDay,minute,0);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mma");
                                String s=simpleDateFormat.format(time);
                                BookingTimeToTV.setText(s);
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });
                ConfirmBookingBtn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (BookingOf_ET.getText().toString().equalsIgnoreCase("")){
                            BookingOf_ET.setError("Field can't be empty");
                        }else if (BookingFor_ET.getText().toString().equalsIgnoreCase("")){
                            BookingFor_ET.setError("Field can't be empty");
                        }else if (BookingDescription_ET.getText().toString().equalsIgnoreCase("")){
                            BookingDescription_ET.setError("Field can't be empty");
                        }else if (BookingDateTV.length()==0){
                            Toast.makeText(getContext(),"Select Date",Toast.LENGTH_SHORT).show();
                        }else if (BookingTimeFromTV.length()==0){
                            Toast.makeText(getContext(),"Select Time",Toast.LENGTH_SHORT).show();
                        }else if (BookingTimeToTV.length()==0){
                            Toast.makeText(getContext(),"Select Time",Toast.LENGTH_SHORT).show();
                        }else {
                        boolean isInserted=BookingDb.insertBooking(BookingOf_ET.getText().toString(),
                                BookingFor_ET.getText().toString(),
                                BookingDescription_ET.getText().toString(),
                                BookingDateTV.getText().toString(),
                                BookingTimeFromTV.getText().toString(),
                                BookingTimeToTV.getText().toString());
                        BookingList.clear();
                        toggleEmptyBookings();
                        if (isInserted==true) {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            BookingRecyclerView.setLayoutManager(linearLayoutManager);
                            BookingDb = new BookingDatabaseHelper(getContext());
                            BookingList.addAll(BookingDb.getAllBooking());
                            bookingRecyclerAdapter = new BookingRecyclerAdapter(context,BookingList);
                            BookingRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            BookingRecyclerView.setAdapter(bookingRecyclerAdapter);
                            toggleEmptyBookings();
                            alertDialog.dismiss();
                            bookingNotification();
                            Toast.makeText(getActivity(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getActivity(),"Booking Not Confirmed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.show();
            }
        });
        return view;
    }

    public void deleteBookings(final int position){
        BookingDb.deleteBooking(BookingList.get(position));
        BookingList.remove(position);
        bookingRecyclerAdapter.notifyItemRemoved(position);
        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
        toggleEmptyBookings();
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
                                    deleteBookings(position);
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        builder.show();
    }

    private void toggleEmptyBookings() {

        if (BookingList.size() > 0) {
            noBookingTV.setVisibility(View.GONE);
        } else {
            noBookingTV.setVisibility(View.VISIBLE);
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
    private void bookingNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        Intent i = new Intent(getContext(), DashboardScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("New Booking");
        mBuilder.setContentText("Tap to see booking");
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
