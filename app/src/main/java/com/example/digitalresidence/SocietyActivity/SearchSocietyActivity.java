package com.example.digitalresidence.SocietyActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitalresidence.R;
import com.example.digitalresidence.SQLiteDatabases.SocietyDatabase.SocietyDatabaseHelper;
import com.example.digitalresidence.SQLiteDatabases.SocietyDatabase.SocietyModel;
import com.example.digitalresidence.SQLiteDatabases.SocietyDatabase.SocietyRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SearchSocietyActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText searchET;
    private RecyclerView SocietyRecyclerView;
    private SocietyRecyclerAdapter societyRecyclerAdapter;
    private List<SocietyModel> societyList = new ArrayList<>();
    private Context context;
    private SocietyDatabaseHelper SocietyDb;
    Button add_society;
    ImageButton clearETBtn;
    TextView noListTV;
    EditText sName,sAddress;
    ImageView noListIMG;
    FloatingActionButton add;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_society);

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.toolbar));


        toolbar=findViewById(R.id.searchToolbar);
       searchET=findViewById(R.id.searchSociety);
       clearETBtn=findViewById(R.id.clearSearchET);

        context=SearchSocietyActivity.this;
        noListIMG=findViewById(R.id.noList);
        noListTV=findViewById(R.id.noListTXT);
        SocietyRecyclerView=findViewById(R.id.society_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        SocietyRecyclerView.setLayoutManager(linearLayoutManager);
        SocietyDb = new SocietyDatabaseHelper(context);
        societyList.addAll(SocietyDb.getAllSociety());
        societyRecyclerAdapter = new SocietyRecyclerAdapter(context, societyList);
        SocietyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        SocietyRecyclerView.setAdapter(societyRecyclerAdapter);
        toggleEmptySocieties();

        clearETBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchET.getText().clear();
            }
        });
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        add=findViewById(R.id.add_society_main_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder AddSocietyBuilder = new AlertDialog.Builder(context);
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog_society,
                        viewGroup, false);
                AddSocietyBuilder.setView(dialogView);
                final AlertDialog alertDialog = AddSocietyBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                SocietyDb=new SocietyDatabaseHelper(context);
                sName=dialogView.findViewById(R.id.society_title_editText);
                sAddress=dialogView.findViewById(R.id.society_add_editText);
                add_society=dialogView.findViewById(R.id.confirm_society_btn);
                add_society.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = SocietyDb.insertSociety(
                                sName.getText().toString(),
                                sAddress.getText().toString());
                        societyList.clear();
                        toggleEmptySocieties();
                        if (isInserted==true){
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                            SocietyRecyclerView.setLayoutManager(linearLayoutManager);
                            SocietyDb = new SocietyDatabaseHelper(context);
                            societyList.addAll(SocietyDb.getAllSociety());
                            societyRecyclerAdapter = new SocietyRecyclerAdapter(context, societyList);
                            SocietyRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            SocietyRecyclerView.setAdapter(societyRecyclerAdapter);
                            toggleEmptySocieties();
                            alertDialog.dismiss();
                            Toast.makeText(context,"Added",Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(context,"Not added",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<SocietyModel> filterNames = new ArrayList<>();

        //looping through existing elements
        for (SocietyModel s : societyList) {
            //if the existing elements contains the search input
            if (s.SocietyName.contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        societyRecyclerAdapter.filterList(filterNames);
    }
    private void toggleEmptySocieties() {

        if (societyList.size() > 0) {
            noListTV.setVisibility(View.GONE);
            noListIMG.setVisibility(View.GONE);
        } else {
            noListTV.setVisibility(View.VISIBLE);
            noListIMG.setVisibility(View.VISIBLE);
        }
    }
}
