package com.example.digitalresidence.Fragment;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.digitalresidence.SQLiteDatabases.NoticeDatabase.NoticeDatabaseHelper;
import com.example.digitalresidence.SQLiteDatabases.NoticeDatabase.NoticeModel;
import com.example.digitalresidence.SQLiteDatabases.NoticeDatabase.NoticeRecyclerAdapter;
import com.example.digitalresidence.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_notice extends Fragment {
    private RecyclerView NoticeRecyclerView;
    private NoticeRecyclerAdapter noticeRecyclerAdapter;
    private List<NoticeModel> noticeList = new ArrayList<>();
    private Context context;
    private NoticeDatabaseHelper NoticeDb;
    FloatingActionButton floatingActionButton;
    TextView MainTxt,NoticeTitle_TV,NoticeApplicableTo_TV,NoticeDescription_TV,noNoticeTV;
EditText NoticeTitle_ET,NoticeApplicableTo_ET,NoticeDescription_ET;
Button AddNoticeBtn;
    public Fragment_notice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fragment_notice, container, false);

        noNoticeTV=view.findViewById(R.id.noNotice_text);
        NoticeRecyclerView=view.findViewById(R.id.notice_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        NoticeRecyclerView.setLayoutManager(linearLayoutManager);
        NoticeDb = new NoticeDatabaseHelper(getContext());
        noticeList.addAll(NoticeDb.getAllNotice());
        noticeRecyclerAdapter = new NoticeRecyclerAdapter(context, noticeList);
        NoticeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        NoticeRecyclerView.setAdapter(noticeRecyclerAdapter);
        toggleEmptyNotices();
        NoticeRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                NoticeRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));


        NoticeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView,newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !floatingActionButton.isShown()){
                    floatingActionButton.show();
                }else if (dy > 0 && floatingActionButton.isShown()){
                    floatingActionButton.hide();
                }
            }
        });

        floatingActionButton=view.findViewById(R.id.notice_floating_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_notice,
                        viewGroup, false);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                MainTxt = dialogView.findViewById(R.id.notice_main_tv);
                NoticeTitle_TV = dialogView.findViewById(R.id.notice_title_TXT);
                NoticeApplicableTo_TV = dialogView.findViewById(R.id.applicableTo_TXT);
                NoticeDescription_TV = dialogView.findViewById(R.id.notice_description_TXT);
                NoticeTitle_ET = dialogView.findViewById(R.id.notice_title_editText);
                NoticeApplicableTo_ET = dialogView.findViewById(R.id.applicableTo_editText);
                NoticeDescription_ET = dialogView.findViewById(R.id.notice_description_editText);
                AddNoticeBtn = dialogView.findViewById(R.id.confirm_notice_btn);
                AddNoticeBtn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (NoticeTitle_ET.getText().toString().equalsIgnoreCase("")){
                            NoticeTitle_ET.setError("Enter Notice Title.");
                        }else if (NoticeApplicableTo_ET.getText().toString().equalsIgnoreCase("")){
                            NoticeApplicableTo_ET.setError("Field can't be empty.");
                        }else if (NoticeDescription_ET.getText().toString().equalsIgnoreCase("")){
                            NoticeDescription_ET.setError("Enter Description.");
                        }else {
                            boolean isInserted = NoticeDb.insertNotice(
                                    NoticeTitle_ET.getText().toString(),
                                    NoticeApplicableTo_ET.getText().toString(),
                                    NoticeDescription_ET.getText().toString());
                            noticeList.clear();
                            toggleEmptyNotices();
                            if (isInserted == true) {
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                NoticeRecyclerView.setLayoutManager(linearLayoutManager);
                                NoticeDb = new NoticeDatabaseHelper(getContext());
                                noticeList.addAll(NoticeDb.getAllNotice());
                                noticeRecyclerAdapter = new NoticeRecyclerAdapter(context, noticeList);
                                NoticeRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                NoticeRecyclerView.setAdapter(noticeRecyclerAdapter);
                                toggleEmptyNotices();
                                alertDialog.dismiss();
                                noticeNotification();
                                Toast.makeText(getActivity(), "Complaint Added", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getActivity(), "Complaint Not Added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.show();
            }
        });

        return view;
    }

    public void deleteNotices(final int position) {
        NoticeDb.deleteNotice(noticeList.get(position));
        noticeList.remove(position);
        noticeRecyclerAdapter.notifyItemRemoved(position);
        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
        toggleEmptyNotices();
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
                                    deleteNotices(position);
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        builder.show();
    }
    private void toggleEmptyNotices() {

        if (noticeList.size() > 0) {
            noNoticeTV.setVisibility(View.GONE);
        } else {
            noNoticeTV.setVisibility(View.VISIBLE);
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
    private void noticeNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        Intent i = new Intent(getContext(), DashboardScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.warning);
        mBuilder.setContentTitle("New Notice");
        mBuilder.setContentText(""+NoticeTitle_ET.getText().toString());
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
