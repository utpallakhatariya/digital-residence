package com.example.digitalresidence.SignUpScreen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalresidence.LoginScreen.LoginScreen;
import com.example.digitalresidence.R;
import com.example.digitalresidence.SQLiteDatabases.SocietyDatabase.SocietyDatabaseHelper;
import com.example.digitalresidence.SQLiteDatabases.SocietyDatabase.SocietyModel;
import com.example.digitalresidence.SQLiteDatabases.SocietyDatabase.SocietyRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class SignUpScreen extends AppCompatActivity {
    private RecyclerView SocietyRecyclerView;
    private SocietyRecyclerAdapter societyRecyclerAdapter;
    private List<SocietyModel> societyList = new ArrayList<>();
    private Context context;
    private SocietyDatabaseHelper SocietyDb;
    Button reg_btn,AddRegButton;
    EditText edit_societyName, edit_fullName,edit_age, edit_MobileNumber, edit_emailId,  edit_FlatNumber, edit_city, edit_pincode;
    EditText StateET,CountryET;
    Spinner gender_Spinner;

    TextView noListTV;
    EditText sName,sAddress;
    ImageView noListIMG;

    String[] states = new String[]{"Andra Pradesh","Arunachal Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh","Dadra and Nagar Haveli",
            "Daman and Diu","Delhi","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Ladakh",
            "Lakshadeep","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Orissa","Pondicherry","Punjab","Rajasthan",
            "Sikkim","TamilNadu","Telagana","Tripura","Uttaranchal","Uttar Pradesh","West Bengal"};
    String[] countries = new String[]{"Afghanistan","Albania","Algeria","Andorra","Angola","Antigua and Barbuda","Argentina","Armenia",
            "Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bhutan",
            "Bolivia","Bosnia and Herzegovina","Botswana","Brazil","Brunei","Bulgaria","Burkina Faso","Burundi","Côte d'Ivoire","Cabo Verde",
            "Cambodia","Cameroon","Canada","Central African Republic","Chad","Chile","China","Colombia","Comoros","Congo","Costa Rica","Croatia",
            "Cuba","Cyprus","Czechia (Czech Republic)","Democratic Republic of the Congo","Denmark","Djibouti","Dominica","Dominican Republic",
            "Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Eswatini","Ethiopia","Fiji","Finland","France","Gabon",
            "Gambia","Georgia","Germany","Ghana","Greece","Grenada","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","Holy See","Honduras",
            "Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya",
            "Kiribati","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg",
            "Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia",
            "Moldova","Monaco","Mongolia","Montenegro","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepal","Netherlands","New Zealand",
            "Nicaragua","Niger","Nigeria","North Korea","North Macedonia","Norway","Oman","Pakistan","Palau","Palestine State","Panama",
            "Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Qatar","Romania","Russia","Rwanda","Saint Kitts and Nevis",
            "Saint Lucia","Saint Vincent and the Grenadines","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia",
            "Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Korea","South Sudan",
            "Spain","Sri Lanka","Sudan","Suriname","Sweden","Switzerland","Syria","Tajikistan","Tanzania","Thailand","Timor-Leste","Togo",
            "Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom",
            "United States of America","Uruguay","Uzbekistan","Vanuatu","Venezuela","Vietnam","Yemen","Zambia","Zimbabwe"};

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    String emailPattern = "[a-zA-Z0_9]+@[a-z]+\\.+[a-z]+";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusbar_login));
        context = SignUpScreen.this;
        noListIMG = findViewById(R.id.noList);
        noListTV = findViewById(R.id.noListTXT);
        edit_fullName = findViewById(R.id.fullNameEdt);
        gender_Spinner = findViewById(R.id.genderSpinner);
        edit_age = findViewById(R.id.ageET);
        edit_MobileNumber = findViewById(R.id.regMobileNumberET);
        edit_emailId = findViewById(R.id.regEmailET);
        edit_societyName = findViewById(R.id.regSocietyNameEditText);
        edit_FlatNumber = findViewById(R.id.regFlatEditText);
        edit_city = findViewById(R.id.regCityEditText);
        edit_pincode = findViewById(R.id.regPinCodeEditText);
        StateET = findViewById(R.id.regStateEditText);
        CountryET = findViewById(R.id.regCountryEditText);
        reg_btn = findViewById(R.id.regbutton1);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFullName() | !validateGender() | !validateAge() | !validateMobileNumber() | !validateEmail() | !validateSocietyName() |
                        !validateFlat() | !validateCity() | !validatePincode() | !validateState() | !validateCountry()) {
                    shakeItBaby();
                    return;
                } else {
                    AlertDialog.Builder signUpDialogBuilder = new AlertDialog.Builder(context);
                    //SocietyDb = new SocietyDatabaseHelper(context);
                    signUpDialogBuilder.setMessage("Request has been sent to the admin for verification and you will get notified via text message" +
                            " or email with login credentials").setCancelable(false)
                            .setTitle("Confirm registration?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), "Thank you for registration", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = signUpDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        final ArrayList<String> genderSpinnerArray = new ArrayList<>();
        genderSpinnerArray.add("Select");
        genderSpinnerArray.add("Male");
        genderSpinnerArray.add("Female");
        genderSpinnerArray.add("Transgender");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, genderSpinnerArray);
        gender_Spinner.setAdapter(genderAdapter);

    }

    /*private void toggleEmptySocieties() {

        if (societyList.size() > 0) {
            noListTV.setVisibility(View.GONE);
            noListIMG.setVisibility(View.GONE);
        } else {
            noListTV.setVisibility(View.VISIBLE);
            noListIMG.setVisibility(View.VISIBLE);
        }
    }*/
    private boolean validateFullName() {
        String FullNameInput = edit_fullName.getText().toString().trim();

        if (FullNameInput.isEmpty()) {
            edit_fullName.setError("Field can't be empty");
            return false;
        } else {
            edit_fullName.setError(null);
            return true;
        }
    }
    private boolean validateGender(){
        String gender=gender_Spinner.getSelectedItem().toString();

        if(gender.equalsIgnoreCase("Select")){
            ((TextView)gender_Spinner.getSelectedView()).setError("Not Selected");
            return false;
        }else {
            ((TextView)gender_Spinner.getSelectedView()).setError(null);
            return true;
        }
    }

    private boolean validateAge(){
        String age_Input = edit_age.getText().toString().trim();

        if (age_Input.isEmpty()){
            edit_age.setError("Field can't be empty");
            return false;
        }else {
            edit_age.setError(null);
            return true;
        }
    }

    private boolean validateMobileNumber(){
        String mobile_numberInput=edit_MobileNumber.getText().toString().trim();

        if (mobile_numberInput.isEmpty()){
            edit_MobileNumber.setError("Field cant't be empty");
            return false;
        } else if (mobile_numberInput.length()>10){
            edit_MobileNumber.setError("Enter valid number");
            return false;
        }else if (mobile_numberInput.length()<10){
            edit_MobileNumber.setError("Enter valid number");
            return false;
        } else {
            edit_MobileNumber.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = edit_emailId.getText().toString().trim();

        if (emailInput.isEmpty()) {
            edit_emailId.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            edit_emailId.setError("Please enter a valid email address");
            return false;
        } else {
            edit_emailId.setError(null);
            return true;
        }
    }

    private boolean validateSocietyName(){
        String societyName_input = edit_societyName.getText().toString().trim();

        if (societyName_input.isEmpty()){
            edit_societyName.setError("Field can't be empty");
            return false;
        }else {
            edit_societyName.setError(null);
            return true;
        }
    }

    private boolean validateFlat(){
        String flat_input = edit_FlatNumber.getText().toString().trim();

        if (flat_input.isEmpty()){
            edit_FlatNumber.setError("Field can't be empty");
            return false;
        }else {
            edit_FlatNumber.setError(null);
            return true;
        }
    }
    private boolean validateCity(){
        String city_input = edit_city.getText().toString().trim();

        if (city_input.isEmpty()){
            edit_city.setError("Field can't be empty");
            return false;
        }else {
            edit_city.setError(null);
            return true;
        }
    }

    private boolean validatePincode(){
        String pinCode_input = edit_pincode.getText().toString().trim();

        if (pinCode_input.isEmpty()){
            edit_pincode.setError("Field can't be empty");
            return false;
        }else {
            edit_pincode.setError(null);
            return true;
        }
    }
    private boolean validateState(){
        String state_input = StateET.getText().toString().trim();

        if (state_input.isEmpty()){
            StateET.setError("Field can't be empty");
            return false;
        }else {
            StateET.setError(null);
            return true;
        }
    }
    private boolean validateCountry(){
        String country_input = CountryET.getText().toString().trim();

        if (country_input.isEmpty()){
            CountryET.setError("Field can't be empty");
            return false;
        }else {
            CountryET.setError(null);
            return true;
        }
    }

    private void shakeItBaby(){
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 100, 100};
        vibrator.vibrate(pattern,-1);
    }
    /*private boolean validatePassword() {
        String password=text_pass.getEditText().getText().toString().trim();
        String con_password=text_con_pass.getEditText().getText().toString().trim();

         if (password.isEmpty()){
                text_pass.setError("Field can't be empty");
                return false;
         }     else if (con_password.isEmpty()) {
                text_con_pass.setError("Field can't be empty");
                return false;
         }   else if (!con_password.equals(password)){
                  text_con_pass.setError("Password do not match");
                  return false;
         }   else {
                text_con_pass.setError(null);
                return true;
        }
    }*/
    }