package com.example.digitalresidence.SQLiteDatabases.GuestDatabase;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalresidence.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GuestRecyclerAdapter extends RecyclerView.Adapter<GuestRecyclerAdapter.GuestViewHolder> {

    private List<GuestModel> guestModelArrayList;
    private Context context;

    public GuestRecyclerAdapter(Context context,List<GuestModel> guestModelArrayList) {
        this.guestModelArrayList = guestModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guest_recycler_row,parent,false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        holder.GuestId.setText(Html.fromHtml("&#8226"));
        holder.GuestName.setText(guestModelArrayList.get(position).getGuestName());
        holder.GuestAddress.setText(guestModelArrayList.get(position).getGuestOwnerAddress());
        holder.GuestPurpose.setText(guestModelArrayList.get(position).getGuestPurpose());
        holder.GuestPersonCount.setText(guestModelArrayList.get(position).getGuestCount());
        holder.GuestTime.setText(guestModelArrayList.get(position).getGuestTime());
        holder.GuestTimeStamp.setText(formatDate(guestModelArrayList.get(position).getGuestTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return guestModelArrayList.size();
    }

    public class GuestViewHolder extends RecyclerView.ViewHolder{
        public TextView GuestId;
        public TextView GuestName;
        public TextView GuestAddress;
        public TextView GuestPurpose;
        public TextView GuestPersonCount;
        public TextView GuestTime;
        public TextView GuestTimeStamp;

        public GuestViewHolder(@NonNull View itemView) {
            super(itemView);
            GuestId=itemView.findViewById(R.id.GuestShowIDTXT);
            GuestName=itemView.findViewById(R.id.textViewGuestName);
            GuestAddress=itemView.findViewById(R.id.textViewGuestComingAt);
            GuestPurpose=itemView.findViewById(R.id.textViewGuestPurpose);
            GuestPersonCount=itemView.findViewById(R.id.textViewGuestCountTime);
            GuestTime=itemView.findViewById(R.id.textViewGuestTime);
            GuestTimeStamp=itemView.findViewById(R.id.GuestTimeStampTEXTVIEW);
        }
    }
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d yyyy");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}
