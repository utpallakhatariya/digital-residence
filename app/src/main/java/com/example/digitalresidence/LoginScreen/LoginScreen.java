package com.example.digitalresidence.LoginScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.digitalresidence.R;
import com.example.digitalresidence.DashboardScreen.DashboardScreen;
import com.example.digitalresidence.SignUpScreen.SignUpScreen;
import com.example.digitalresidence.SocietyActivity.JoinSocietyActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginScreen extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    CircleImageView profilePhoto;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=1;
    String TAG="###";

    Button log_button,sign_button,log_out;
    TextView log_text,or_text;
    EditText user_edit,pass_edit;
    View v1,v2;
    Spinner spinner;

    /*private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
*/
    String emailPattern="[a-zA-Z0_9]+@[a-z]+\\.+[a-z]+";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusbar_login));



       /* spinner = findViewById(R.id.spinner);
        List<String> categories = new ArrayList<>();
        categories.add("Select Category:-");
        categories.add("Secretory");
        categories.add("Owner");
        categories.add("Tenants");
        categories.add("Security Guard");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
*/
        // Initialize Firebase Auth
        log_button = findViewById(R.id.loginbutton);
        sign_button = findViewById(R.id.signupbutton);
        user_edit = findViewById(R.id.login_ed);
        pass_edit = findViewById(R.id.password_ed);
        profilePhoto = findViewById(R.id.imageViewP);


    }

        @Override
    public void onBackPressed() {

        if (backPressedTime+2000>System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else {
            backToast=  Toast.makeText(getBaseContext(),"Press back again to Exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime=System.currentTimeMillis();
    }

    public void login(View view) {

        if(user_edit.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
        }else if (pass_edit.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
        }else {
            if (user_edit.getText().toString().trim().matches(emailPattern)) {
                    Intent j = new Intent(LoginScreen.this, DashboardScreen.class);
                    startActivity(j);
                    finish();
                    Toast toast = Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    public void SignUp(View view) {
        Intent j=new Intent(LoginScreen.this, JoinSocietyActivity.class);
        startActivity(j);
    }
}