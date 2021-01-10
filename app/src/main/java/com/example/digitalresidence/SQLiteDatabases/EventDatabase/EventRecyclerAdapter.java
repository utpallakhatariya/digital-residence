package com.example.digitalresidence.SQLiteDatabases.EventDatabase;

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

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ModelViewHolder> {

   private List<EventModel> eventModelArrayList;
    private Context context;

    public EventRecyclerAdapter( Context context, List<EventModel> eventModelArrayList) {
        this.context = context;
        this.eventModelArrayList = eventModelArrayList;
    }

    @NonNull
    @Override
    public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_recycler_row,parent,false);
    return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
        holder.ID.setText(Html.fromHtml("&#8226")/*eventModelArrayList.get(position).getId()*/);
        holder.Name.setText(eventModelArrayList.get(position).getName());
        holder.Place.setText(eventModelArrayList.get(position).getPlace());
        holder.Description.setText(eventModelArrayList.get(position).getDescription());
        holder.Date.setText(eventModelArrayList.get(position).getDate());
        holder.TimeFrom.setText(eventModelArrayList.get(position).getTimeFrom());
        holder.TimeTo.setText(eventModelArrayList.get(position).getTimeTo());
        holder.TimeStamp.setText(formatDate(eventModelArrayList.get(position).getTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return eventModelArrayList.size();
    }

    public class ModelViewHolder extends RecyclerView.ViewHolder{
        public TextView ID;
        public TextView Name;
        public TextView Place;
        public TextView Description;
        public TextView Date;
        public TextView TimeFrom;
        public TextView TimeTo;
        public TextView TimeStamp;

        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.showIDTXT);
            Name=itemView.findViewById(R.id.textViewEventType);
            Place=itemView.findViewById(R.id.textViewPlace);
            Description=itemView.findViewById(R.id.textViewDescription);
            Date=itemView.findViewById(R.id.textViewDate);
            TimeFrom=itemView.findViewById(R.id.textViewTimeFrom);
            TimeTo=itemView.findViewById(R.id.textViewTimeTo);
            TimeStamp=itemView.findViewById(R.id.timeStampTEXTVIEW);

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


