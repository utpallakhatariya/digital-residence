package com.example.digitalresidence.SQLiteDatabases.MeetingDatabase;

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

public class MeetingRecyclerAdapter extends RecyclerView.Adapter<MeetingRecyclerAdapter.MeetingViewHolder> {

    private List<MeetingModel> meetingModelArrayList;
    private Context context;

    public MeetingRecyclerAdapter( Context context,List<MeetingModel> meetingModelArrayList) {
        this.meetingModelArrayList = meetingModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_recycler_row,parent,false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        holder.ID.setText(Html.fromHtml("&#8226"));
        holder.MeetingTitle.setText(meetingModelArrayList.get(position).getMeetingTitle());
        holder.MeetingPlace.setText(meetingModelArrayList.get(position).getMeetingPlace());
        holder.MeetingDescription.setText(meetingModelArrayList.get(position).getMeetingDescription());
        holder.MeetingDate.setText(meetingModelArrayList.get(position).getMeetingDate());
        holder.MeetingTime.setText(meetingModelArrayList.get(position).getMeetingTime());
        holder.MeetingTimeStamp.setText(formatDate(meetingModelArrayList.get(position).getMeetingTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return meetingModelArrayList.size();
    }

    public class MeetingViewHolder extends RecyclerView.ViewHolder{
        public TextView ID;
        public TextView MeetingTitle;
        public TextView MeetingPlace;
        public TextView MeetingDescription;
        public TextView MeetingDate;
        public TextView MeetingTime;
        public TextView MeetingTimeStamp;

        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.MeetingShowIDTXT);
            MeetingTitle=itemView.findViewById(R.id.MeetingTextViewMeetingTitle);
            MeetingPlace=itemView.findViewById(R.id.MeetingTextViewPlace);
            MeetingDescription=itemView.findViewById(R.id.MeetingTextViewDescription);
            MeetingDate=itemView.findViewById(R.id.MeetingTextViewDate);
            MeetingTime=itemView.findViewById(R.id.MeetingTextViewTime);
            MeetingTimeStamp=itemView.findViewById(R.id.MeetingTimeStampTEXTVIEW);

        }
    }
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d yyyy");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}
