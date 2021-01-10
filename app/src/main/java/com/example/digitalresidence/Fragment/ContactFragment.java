package com.example.digitalresidence.Fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalresidence.DashboardScreen.DashboardScreen;
import com.example.digitalresidence.R;
import com.example.digitalresidence.SQLiteDatabases.ContactDatabase.ContactDatabaseHelper;
import com.example.digitalresidence.SQLiteDatabases.ContactDatabase.ContactModel;
import com.example.digitalresidence.SQLiteDatabases.ContactDatabase.ContactRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements SearchView.OnQueryTextListener {
    private static final String TAG = "ContactFragment";
    private RecyclerView contactRecyclerView;
    private ContactRecyclerAdapter contactRecyclerAdapter;
    private List<ContactModel> ContactList = new ArrayList<>();
    private Context context;
    private ContactDatabaseHelper ContactDb;
    TextView noContactView,ContactName_TV,ContactNumber_TV;
    EditText ContactName_ET,ContactNumber_ET;
    Button ConfirmContactBtn;
    FloatingActionButton contactFloatingActionButton;
    ContactModel contactModel;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view= inflater.inflate(R.layout.fragment_contact, container, false);
        setHasOptionsMenu(true);
        noContactView=view.findViewById(R.id.no_contact_text);
        context=getActivity();
        contactRecyclerView = view.findViewById(R.id.contactRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        contactRecyclerView.setLayoutManager(linearLayoutManager);
        ContactDb = new ContactDatabaseHelper(getContext());
        ContactList.addAll(ContactDb.getAllContact());
        contactRecyclerAdapter = new ContactRecyclerAdapter(context,ContactList);
        contactRecyclerView.setItemAnimator(new DefaultItemAnimator());
        contactRecyclerView.setAdapter(contactRecyclerAdapter);
        toggleEmptyContacts();
        contactRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                contactRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

        contactRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView,newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !contactFloatingActionButton.isShown()){
                    contactFloatingActionButton.show();
                }else if (dy > 0 && contactFloatingActionButton.isShown()){
                    contactFloatingActionButton.hide();
                }
            }
        });
        contactFloatingActionButton=view.findViewById(R.id.contact_floating_btn);
        contactFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_contact,
                        viewGroup, false);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ContactNumber_TV=dialogView.findViewById(R.id.contact_name_TXT);
                ContactNumber_TV=dialogView.findViewById(R.id.contact_number_TXT);
                ContactName_ET=dialogView.findViewById(R.id.contact_name_EditText);
                ContactNumber_ET=dialogView.findViewById(R.id.contact_number_editText);
                ConfirmContactBtn=dialogView.findViewById(R.id.add_contact_button);
                ConfirmContactBtn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (ContactName_ET.getText().toString().equalsIgnoreCase("")) {
                            ContactName_ET.setError("Enter Contact Name.");
                        } else if (ContactNumber_ET.getText().toString().equalsIgnoreCase("")) {
                            ContactNumber_ET.setError("Enter Valid Contact Number");
                        }else {
                            boolean isInserted = ContactDb.insertContact(ContactName_ET.getText().toString(),
                                    ContactNumber_ET.getText().toString());
                            ContactList.clear();
                            toggleEmptyContacts();
                            if (isInserted==true){
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                contactRecyclerView.setLayoutManager(linearLayoutManager);
                                ContactDb = new ContactDatabaseHelper(getContext());
                                ContactList.addAll(ContactDb.getAllContact());
                                contactRecyclerAdapter = new ContactRecyclerAdapter(context,ContactList);
                                contactRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                contactRecyclerView.setAdapter(contactRecyclerAdapter);
                                toggleEmptyContacts();
                                alertDialog.dismiss();
                                contactNotification();
                                Toast.makeText(getActivity(), "Contact Added", Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(getActivity(),"Contact Not Added",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.contact_menu,menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Enter Name");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<ContactModel> newList = new ArrayList<>();
        for (ContactModel contactModelSearch : ContactList){
            String name = contactModelSearch.getContactName().toLowerCase();
            if (name.contains(newText)){
                newList.add(contactModelSearch);
            }
        }
        contactRecyclerAdapter.setFilter(newList);
        return true;
    }


    public void deleteContacts(final int position){
        ContactDb.deleteContact(ContactList.get(position));
        ContactList.remove(position);
        contactRecyclerAdapter.notifyItemRemoved(position);
        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
        toggleEmptyContacts();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{Html.fromHtml("<font color='#000000'>Edit</font>"), Html.fromHtml("<font color='#FF0000'>Delete</font>")};

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    ViewGroup viewGroup = getView().findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout.custom_dialog_contact,
                            viewGroup, false);
                    builder.setView(dialogView);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ContactNumber_TV=dialogView.findViewById(R.id.contact_name_TXT);
                    ContactNumber_TV=dialogView.findViewById(R.id.contact_number_TXT);
                    ContactName_ET=dialogView.findViewById(R.id.contact_name_EditText);
                    ContactNumber_ET=dialogView.findViewById(R.id.contact_number_editText);
                    ConfirmContactBtn=dialogView.findViewById(R.id.add_contact_button);
                    ConfirmContactBtn.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(View v) {
                            if (ContactName_ET.getText().toString().equalsIgnoreCase("")) {
                                ContactName_ET.setError("Enter Contact Name.");
                            } else if (ContactNumber_ET.getText().toString().equalsIgnoreCase("")) {
                                ContactNumber_ET.setError("Enter Valid Contact Number");
                            }else {
                                boolean isUpdated = ContactDb.updateContact(ContactList.get(position).getContactId(),ContactName_ET.getText().toString(),
                                        ContactNumber_ET.getText().toString());
                                Log.e(TAG,"Update: "+ContactList.get(position).getContactId());
                                ContactList.clear();
                                toggleEmptyContacts();
                                if (isUpdated==true){
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                    contactRecyclerView.setLayoutManager(linearLayoutManager);
                                    ContactDb = new ContactDatabaseHelper(getContext());
                                    ContactList.addAll(ContactDb.getAllContact());
                                    contactRecyclerAdapter = new ContactRecyclerAdapter(context,ContactList);
                                    contactRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                    contactRecyclerView.setAdapter(contactRecyclerAdapter);
                                    toggleEmptyContacts();
                                    alertDialog.dismiss();
                                    contactNotification();
                                    Toast.makeText(getActivity(), "Contact Updated", Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(getActivity(),"Contact Updated",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertDialog.show();
                    //dialog.cancel();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to delete?")
                            .setCancelable(false)
                            .setPositiveButton(Html.fromHtml("<font color='#000000'>Cancel</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton(Html.fromHtml("<font color='#FF0000'>Yes</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteContacts(position);
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        builder.show();
    }

    private void toggleEmptyContacts() {

        if (ContactList.size() > 0) {
            noContactView.setVisibility(View.GONE);
        } else {
            noContactView.setVisibility(View.VISIBLE);
        }
    }

    //touchListener for recyclerView
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        public interface ClickListener {
            void onClick(View view, int position);

            void onLongClick(View view, int position);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void contactNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        Intent i = new Intent(getContext(), DashboardScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("New Contact");
        mBuilder.setContentText("Tap to see contact");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "01";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Digital Residence Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        mNotificationManager.notify(0, mBuilder.build());
    }
}

