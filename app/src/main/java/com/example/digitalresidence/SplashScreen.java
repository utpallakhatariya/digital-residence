package com.example.digitalresidence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.digitalresidence.LoginScreen.LoginScreen;
import com.example.digitalresidence.SocietyActivity.JoinSocietyActivity;

public class SplashScreen extends AppCompatActivity {
    TextView splash_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splash_txt=findViewById(R.id.splash_text);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashScreen.this, LoginScreen.class);
                startActivity(i);
                finish();

            }
        },500);

    }
}
