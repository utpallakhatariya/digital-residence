package com.example.digitalresidence.SQLiteDatabases.BookingDatabase;

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

public class BookingRecyclerAdapter extends RecyclerView.Adapter<BookingRecyclerAdapter.BookingViewHolder> {

    private List<BookingModel> bookingModelArrayList;
    private Context context;

    public BookingRecyclerAdapter(Context context, List<BookingModel> bookingModelArrayList) {
        this.context = context;
        this.bookingModelArrayList = bookingModelArrayList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_recycler_row,parent,false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        holder.ID.setText(Html.fromHtml("&#8226"));
        holder.BookingOf.setText(bookingModelArrayList.get(position).getBookingOf());
        holder.BookingFor.setText(bookingModelArrayList.get(position).getBookingFor());
        holder.BookingDescription.setText(bookingModelArrayList.get(position).getBookingDescription());
        holder.BookingDate.setText(bookingModelArrayList.get(position).getBookingDate());
        holder.BookingTimeFrom.setText(bookingModelArrayList.get(position).getBookingTimeFrom());
        holder.BookingTimeTo.setText(bookingModelArrayList.get(position).getBookingTimeTo());
        holder.BookingTimeStamp.setText(formatDate(bookingModelArrayList.get(position).getBookingTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return bookingModelArrayList.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        public TextView ID;
        public TextView BookingOf;
        public TextView BookingFor;
        public TextView BookingDescription;
        public TextView BookingDate;
        public TextView BookingTimeFrom;
        public TextView BookingTimeTo;
        public TextView BookingTimeStamp;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.BookingshowIDTXT);
            BookingOf = itemView.findViewById(R.id.textViewBookingOf);
            BookingFor = itemView.findViewById(R.id.textViewBookingFor);
            BookingDescription = itemView.findViewById(R.id.textViewBookingDescription);
            BookingDate = itemView.findViewById(R.id.textViewBookingDate);
            BookingTimeFrom = itemView.findViewById(R.id.textViewBookingTimeFrom);
            BookingTimeTo = itemView.findViewById(R.id.textViewBookingTimeTo);
            BookingTimeStamp = itemView.findViewById(R.id.BookingTimeStampTEXTVIEW);

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

