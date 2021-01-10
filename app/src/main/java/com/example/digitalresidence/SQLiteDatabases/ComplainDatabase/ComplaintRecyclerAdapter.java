package com.example.digitalresidence.SQLiteDatabases.ComplainDatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalresidence.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ComplaintRecyclerAdapter extends RecyclerView.Adapter<ComplaintRecyclerAdapter.ComplaintViewHolder> {

    private List<ComplaintModel> complaintModelArrayList;
    private Context context;
    


    public ComplaintRecyclerAdapter(Context context, List<ComplaintModel> complaintModelArrayList) {
        this.context = context;
        this.complaintModelArrayList = complaintModelArrayList;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.complaint_recycler_row,parent,false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ComplaintViewHolder holder,int position) {
        holder.ID.setText(Html.fromHtml("&#8226"));
        holder.ComplainTitle.setText(complaintModelArrayList.get(position).getComplaintTitle());
        holder.ComplainDescription.setText(complaintModelArrayList.get(position).getComplaintDescription());
        holder.ComplainTimeStamp.setText(formatDate(complaintModelArrayList.get(position).getComplaintTimeStamp()));
        holder.PendingBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceType", "SetTextI18n"})
            @Override
            public void onClick(View v) {
                holder.PendingBtn.setText("Solved");
                holder.PendingBtn.setBackgroundColor(context.getResources().getColor(R.color.complain_green_color_btn));
            }
        });
    }

    @Override
    public int getItemCount() {
        return complaintModelArrayList.size();
    }


    public class ComplaintViewHolder extends RecyclerView.ViewHolder{
        public TextView ID;
        public TextView ComplainTitle;
        public TextView ComplainDescription;
        public TextView ComplainTimeStamp;
        public Button PendingBtn;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.ComplaintShowIDTXT);
            ComplainTitle=itemView.findViewById(R.id.ComplaintTextViewComplaintTitle);
            ComplainDescription=itemView.findViewById(R.id.ComplaintTextViewDescription);
            ComplainTimeStamp=itemView.findViewById(R.id.ComplaintTimeStampTEXTVIEW);
            PendingBtn=itemView.findViewById(R.id.pendingButton);


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
