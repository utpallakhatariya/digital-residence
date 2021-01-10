package com.example.digitalresidence.Fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.digitalresidence.DashboardScreen.DashboardScreen;
import com.example.digitalresidence.R;
import com.example.digitalresidence.SQLiteDatabases.ContactDatabase.ContactModel;

import java.sql.DataTruncation;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBill extends Fragment {
    View view;

    public FragmentBill() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fragment_bill, container, false);

        Button notification = view.findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                billNotification();
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void billNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        Intent i = new Intent(getContext(), DashboardScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("This is a notification");
        mBuilder.setContentText("Your notification is ready.");
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
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        mNotificationManager.notify(0, mBuilder.build());
        }
    }
