package com.example.digitalresidence.SQLiteDatabases.NoticeDatabase;

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

public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.NoticeViewHolder> {

    private List<NoticeModel> noticeModelArrayList;
    private Context context;

    public NoticeRecyclerAdapter(Context context,List<NoticeModel> noticeModelArrayList) {
        this.noticeModelArrayList = noticeModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_recycler_row,parent,false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        holder.ID.setText(Html.fromHtml("&#8226"));
        holder.NoticeTitle.setText(noticeModelArrayList.get(position).getNoticeTitle());
        holder.NoticeApplicableTo.setText(noticeModelArrayList.get(position).getNoticeApplicableTo());
        holder.NoticeDescription.setText(noticeModelArrayList.get(position).getNoticeDescription());
        holder.NoticeTimeStamp.setText(formatDate(noticeModelArrayList.get(position).getNoticeTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return noticeModelArrayList.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder{
        public TextView ID;
        public TextView NoticeTitle;
        public TextView NoticeApplicableTo;
        public TextView NoticeDescription;
        public TextView NoticeTimeStamp;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.NoticeShowIDTXT);
            NoticeTitle=itemView.findViewById(R.id.NoticeTextViewNoticeTitle);
            NoticeApplicableTo=itemView.findViewById(R.id.NoticeTextViewApplicableTo);
            NoticeDescription=itemView.findViewById(R.id.NoticeTextViewDescription);
            NoticeTimeStamp=itemView.findViewById(R.id.NoticeTimeStampTEXTVIEW);

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
