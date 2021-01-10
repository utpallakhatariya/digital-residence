package com.example.digitalresidence.SQLiteDatabases.ContactDatabase;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalresidence.R;

import java.util.ArrayList;
import java.util.List;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ContactViewHolder> {

    private List<ContactModel> contactArrayModelList;
    private Context context;

    public ContactRecyclerAdapter(Context context, List<ContactModel> contactArrayModelList) {
        this.contactArrayModelList = contactArrayModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactRecyclerAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_recycler_row,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactRecyclerAdapter.ContactViewHolder holder, final int position) {
        holder.ContactID.setText(Html.fromHtml("&#8226"));
        holder.ContactName.setText(contactArrayModelList.get(position).getContactName());
        holder.ContactNumber.setText(contactArrayModelList.get(position).getContactNumber());
        holder.ContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ContactNumber.setText(contactArrayModelList.get(position).getContactNumber());
                Log.e("number",contactArrayModelList.get(position).getContactNumber());
                Intent CallIntent = new Intent(Intent.ACTION_DIAL);
                CallIntent.setData(Uri.parse("tel:"+contactArrayModelList.get(position).getContactNumber()));
                try {
                    context.startActivity(CallIntent);
                    Log.e("Finished call", "");
                }catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(context,"Call failed,Please try again later",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactArrayModelList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        public TextView ContactID;
        public TextView ContactName;
        public TextView ContactNumber;
        public Button ContactBtn;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            ContactID=itemView.findViewById(R.id.ContactShowIDTXT);
            ContactName=itemView.findViewById(R.id.textViewContactName);
            ContactNumber=itemView.findViewById(R.id.textViewContactMobileNumber);
            ContactBtn=itemView.findViewById(R.id.ContactButton);
        }
    }
    public void setFilter(ArrayList<ContactModel> newList){
        contactArrayModelList = new ArrayList<>();
        contactArrayModelList.addAll(newList);
        notifyDataSetChanged();
    }

}