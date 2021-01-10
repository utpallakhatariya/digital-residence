package com.example.digitalresidence.Fragment;

import android.annotation.SuppressLint;
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
import com.example.digitalresidence.SQLiteDatabases.ComplainDatabase.ComplainDatabaseHelper;
import com.example.digitalresidence.SQLiteDatabases.ComplainDatabase.ComplaintModel;
import com.example.digitalresidence.SQLiteDatabases.ComplainDatabase.ComplaintRecyclerAdapter;

import com.example.digitalresidence.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Complain extends Fragment {
    FloatingActionButton AddComplaintFloating;
    private RecyclerView ComplainRecyclerView;
    private ComplaintRecyclerAdapter complaintRecyclerAdapter;
    private List<ComplaintModel> complaintList = new ArrayList<>();
    private Context context;
    private ComplainDatabaseHelper complainDb;
    TextView Complain_Title_TV, Complain_Description_TV, AddComplaintTitle_TV,noComplainView;
    EditText ComplainTitle, ComplainDescription;
    Button ComplainNowBtn,PendingSolveBtn;

    public Fragment_Complain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_complain, container, false);

        context=getActivity();
        noComplainView= view.findViewById(R.id.noComplaintTXT);
        ComplainRecyclerView = view.findViewById(R.id.complaint_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ComplainRecyclerView.setLayoutManager(linearLayoutManager);
        complainDb = new ComplainDatabaseHelper(getContext());
        complaintList.addAll(complainDb.getAllComplain());
        complaintRecyclerAdapter = new ComplaintRecyclerAdapter(context, complaintList);
        ComplainRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ComplainRecyclerView.setAdapter(complaintRecyclerAdapter);
        PendingSolveBtn=view.findViewById(R.id.pendingButton);
        toggleEmptyComplains();
        ComplainRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                ComplainRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

        ComplainRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView,newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !AddComplaintFloating.isShown()){
                    AddComplaintFloating.show();
                }else if (dy > 0 && AddComplaintFloating.isShown()){
                    AddComplaintFloating.hide();
                }
            }
        });

        AddComplaintFloating = view.findViewById(R.id.complain_floating);
        AddComplaintFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_complain,
                        viewGroup, false);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                AddComplaintTitle_TV = dialogView.findViewById(R.id.complain_main_tv);
                Complain_Title_TV = dialogView.findViewById(R.id.complain_title_TXT);
                Complain_Description_TV = dialogView.findViewById(R.id.complain_description_TXT);
                ComplainTitle = dialogView.findViewById(R.id.complain_title_editText);
                ComplainDescription = dialogView.findViewById(R.id.complain_description_editText);
                ComplainNowBtn = dialogView.findViewById(R.id.confirm_complain_btn);

                ComplainNowBtn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (ComplainTitle.getText().toString().equalsIgnoreCase("")) {
                            ComplainTitle.setError("Enter Complain Title");
                        } else if (ComplainDescription.getText().toString().equalsIgnoreCase("")) {
                            ComplainDescription.setError("Enter Complain Description");
                        } else {
                            boolean isInserted = complainDb.insertComplain(
                                    ComplainTitle.getText().toString(),
                                    ComplainDescription.getText().toString());
                            complaintList.clear();
                            toggleEmptyComplains();
                            if (isInserted == true) {
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                ComplainRecyclerView.setLayoutManager(linearLayoutManager);
                                complainDb = new ComplainDatabaseHelper(getContext());
                                complaintList.addAll(complainDb.getAllComplain());
                                complaintRecyclerAdapter = new ComplaintRecyclerAdapter(context, complaintList);
                                ComplainRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                ComplainRecyclerView.setAdapter(complaintRecyclerAdapter);
                                toggleEmptyComplains();
                                alertDialog.dismiss();
                                complaintNotification();

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


    public void deleteComplains(final int position) {
        complainDb.deleteComplaint(complaintList.get(position));
        complaintList.remove(position);
        complaintRecyclerAdapter.notifyItemRemoved(position);
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
                                    deleteComplains(position);
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

        if (complaintList.size() > 0) {
            noComplainView.setVisibility(View.GONE);
        } else {
            noComplainView.setVisibility(View.VISIBLE);
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
    public void complaintNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        Intent i = new Intent(getContext(), DashboardScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("New Complaint");
        mBuilder.setContentText("Tap to see complaint");
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