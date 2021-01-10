package com.example.digitalresidence.SocietyActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.digitalresidence.LoginScreen.LoginScreen;
import com.example.digitalresidence.R;
import com.example.digitalresidence.SignUpScreen.SignUpScreen;

public class JoinSocietyActivity extends AppCompatActivity {
    ProgressDialog progress;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_society);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.join_activity_status_bar));

    }

    public void joinSocBtn(View view) {


        progress = new ProgressDialog(JoinSocietyActivity.this);
        //progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progress.setMessage("Please wait while loading...");
        progress.show();
        progress.setContentView(R.layout.progress_bar_dialog);
        //progress.setIndeterminate(true);

        //progress.show();
        progress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        new Thread() {

            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(JoinSocietyActivity.this, SearchSocietyActivity.class);
                startActivity(intent);
                progress.dismiss();
            }
        }.start();

    }

    public void registerSocBtn(View view) {
        Intent intent=new Intent(JoinSocietyActivity.this, SignUpScreen.class);
        startActivity(intent);
    }

}