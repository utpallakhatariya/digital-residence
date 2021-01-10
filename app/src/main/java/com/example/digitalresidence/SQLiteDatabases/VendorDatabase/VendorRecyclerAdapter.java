package com.example.digitalresidence.SQLiteDatabases.VendorDatabase;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalresidence.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VendorRecyclerAdapter extends RecyclerView.Adapter<VendorRecyclerAdapter.VendorViewHolder> {

    private List<VendorModel> VendorModelArrayList;
    private Context context;

    public VendorRecyclerAdapter(Context context,List<VendorModel> vendorModelArrayList) {
        VendorModelArrayList = vendorModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public VendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vendor_recycler_row,parent,false);
        return new VendorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VendorViewHolder holder, int position) {
        holder.Vendor_ID.setText(Html.fromHtml("&#8226"));
        holder.VendorCategory.setText(VendorModelArrayList.get(position).getVendorCategory());
        holder.VendorName.setText(VendorModelArrayList.get(position).getVendorName());
        holder.VendorTime.setText(VendorModelArrayList.get(position).getVendorTime());
        holder.VendorTimeStamp.setText(formatDate(VendorModelArrayList.get(position).getVendorTimeStamp()));
        holder.ArrivedGoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ArrivedGoneBtn.setText("Gone");
                holder.ArrivedGoneBtn.setBackgroundColor(context.getResources().getColor(R.color.complain_red_color_btn));
            }
        });
    }

    @Override
    public int getItemCount() {
        return VendorModelArrayList.size();
    }

    public class VendorViewHolder extends RecyclerView.ViewHolder {
        public TextView Vendor_ID;
        public TextView VendorCategory;
        public TextView VendorName;
        public TextView VendorTime;
        public TextView VendorTimeStamp;
        public Button ArrivedGoneBtn;

        public VendorViewHolder(@NonNull View itemView) {
            super(itemView);
            Vendor_ID=itemView.findViewById(R.id.VendorShowIDTXT);
            VendorCategory=itemView.findViewById(R.id.textViewVendorCategory);
            VendorName=itemView.findViewById(R.id.textViewVendorName);
            VendorTime=itemView.findViewById(R.id.textViewVendorTime);
            VendorTimeStamp=itemView.findViewById(R.id.VendorTimeStampTEXTVIEW);
            ArrivedGoneBtn=itemView.findViewById(R.id.ArrivedGoneButton);
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
