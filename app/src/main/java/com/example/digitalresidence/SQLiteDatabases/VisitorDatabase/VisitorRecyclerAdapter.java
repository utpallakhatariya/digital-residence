package com.example.digitalresidence.SQLiteDatabases.VisitorDatabase;

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

public class VisitorRecyclerAdapter extends RecyclerView.Adapter<VisitorRecyclerAdapter.VisitorViewHolder> {

    private List<VisitorModel> visitorModelArrayList;
    private Context context;

    public VisitorRecyclerAdapter(Context context,List<VisitorModel> visitorModelArrayList) {
        this.visitorModelArrayList = visitorModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitor_recycler_row,parent,false);
        return new VisitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        holder.VISITOR_ID.setText(Html.fromHtml("&#8226"));
        holder.VISITOR_Name.setText(visitorModelArrayList.get(position).getVisitorName());
        holder.VISITOR_ADDRESS.setText(visitorModelArrayList.get(position).getVisitorAddress());
        holder.VISITOR_PURPOSE.setText(visitorModelArrayList.get(position).getVisitorPurpose());
        holder.VISITOR_TIME.setText(visitorModelArrayList.get(position).getVisitorTime());
        holder.VISITOR_TIMESTAMP.setText(formatDate(visitorModelArrayList.get(position).getVisitorTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return visitorModelArrayList.size();
    }

    public class VisitorViewHolder extends RecyclerView.ViewHolder{
        public TextView VISITOR_ID;
        public TextView VISITOR_Name;
        public TextView VISITOR_ADDRESS;
        public TextView VISITOR_PURPOSE;
        public TextView VISITOR_TIME;
        public TextView VISITOR_TIMESTAMP;

        public VisitorViewHolder(@NonNull View itemView) {
            super(itemView);
            VISITOR_ID=itemView.findViewById(R.id.VisitorShowIDTXT);
            VISITOR_Name=itemView.findViewById(R.id.textViewVisitorName);
            VISITOR_ADDRESS=itemView.findViewById(R.id.textViewVisitorAddress);
            VISITOR_PURPOSE=itemView.findViewById(R.id.textViewVisitorPurpose);
            VISITOR_TIME=itemView.findViewById(R.id.textViewVisitorTime);
            VISITOR_TIMESTAMP=itemView.findViewById(R.id.VisitorTimeStampTEXTVIEW);
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
