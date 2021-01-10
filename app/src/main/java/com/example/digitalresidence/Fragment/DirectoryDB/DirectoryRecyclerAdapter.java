package com.example.digitalresidence.Fragment.DirectoryDB;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalresidence.R;

public class DirectoryRecyclerAdapter extends RecyclerView.Adapter<DirectoryRecyclerAdapter.DirectoryViewHolder> {

    DirectoryModel[] directoryList;

    public DirectoryRecyclerAdapter(DirectoryModel[] directoryList) {
        this.directoryList = directoryList;
    }

    @NonNull
    @Override
    public DirectoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.directory_recycler_row,parent,false);
        return new DirectoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectoryViewHolder holder, int position) {
        final DirectoryModel directoryModel = directoryList[position];
        holder.textView.setText(directoryList[position].getDirectoryTitle());
        holder.imageView.setImageResource(directoryList[position].getDImage());
    }

    @Override
    public int getItemCount() {
        return directoryList.length;
    }

    public class DirectoryViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;

        public DirectoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.directoryListImages);
            textView=itemView.findViewById(R.id.directoryListNames);
        }
    }
}
