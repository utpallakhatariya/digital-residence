package com.example.digitalresidence.SQLiteDatabases.SocietyDatabase;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalresidence.R;

import java.util.ArrayList;
import java.util.List;

public class SocietyRecyclerAdapter extends RecyclerView.Adapter<SocietyRecyclerAdapter.SocietyViewHolder> {

    private List<SocietyModel> societyModelArrayList;
    private Context context;

    public SocietyRecyclerAdapter(Context context, List<SocietyModel> societyModelArrayList) {
        this.societyModelArrayList = societyModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SocietyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.society_recycler_row,parent,false);
        return new SocietyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SocietyViewHolder holder, int position) {
        holder.Society_id.setText(Html.fromHtml("&#8226"));
        holder.Society_name.setText(societyModelArrayList.get(position).getSocietyName());
        //holder.Society_address.setText(societyModelArrayList.get(position).getSocietySecretaryName());
    }

    @Override
    public int getItemCount() {
        return societyModelArrayList.size();
    }

    public class SocietyViewHolder extends RecyclerView.ViewHolder{
        public TextView Society_id;
        public TextView Society_name,Society_address;


        public SocietyViewHolder(@NonNull View itemView) {
            super(itemView);
            Society_id=itemView.findViewById(R.id.SocietyShowIDTXT);
            Society_name=itemView.findViewById(R.id.textViewSocietyName);
          //  Society_address=itemView.findViewById(R.id.textViewSocietyAddress);
        }
    }
    public void filterList(ArrayList<SocietyModel> filterNames){
        this.societyModelArrayList=filterNames;
        notifyDataSetChanged();
    }
}
