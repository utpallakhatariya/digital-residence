package com.example.digitalresidence.DashboardScreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.digitalresidence.Fragment.ContactFragment;
import com.example.digitalresidence.Fragment.FragmentAlbum;
import com.example.digitalresidence.Fragment.FragmentBill;
import com.example.digitalresidence.Fragment.FragmentBooking;
import com.example.digitalresidence.Fragment.FragmentDirectory;
import com.example.digitalresidence.Fragment.FragmentEvent;
import com.example.digitalresidence.Fragment.FragmentMeeting;
import com.example.digitalresidence.Fragment.Fragment_Complain;
import com.example.digitalresidence.Fragment.Fragment_Fund;
import com.example.digitalresidence.Fragment.Fragment_Home;
import com.example.digitalresidence.Fragment.Fragment_Profile;
import com.example.digitalresidence.Fragment.Fragment_notice;
import com.example.digitalresidence.Fragment.GuestDirectoryFragment;
import com.example.digitalresidence.Fragment.MemberDirectoryFragment;
import com.example.digitalresidence.Fragment.VendorDirectoryFragment;
import com.example.digitalresidence.Fragment.VisitorDirectoryFragment;
import com.example.digitalresidence.LoginScreen.LoginScreen;
import com.example.digitalresidence.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.navigation.NavigationView;

public class DashboardScreen extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;

    FragmentManager frag_manager;
    FragmentTransaction Frag_Transaction;
    Fragment fragment=null;
    public static Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.toolbar));

        setContentView(R.layout.activity_dashboard_screen);

        toolbar=findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);

        fragment=new Fragment_Home();
        frag_manager=getSupportFragmentManager();
        Frag_Transaction=frag_manager.beginTransaction();
        Frag_Transaction.replace(R.id.frame_layout,fragment);
        Frag_Transaction.commit();

        add();

    }



    @Override
    public void onBackPressed() {
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (fragment instanceof Fragment_Profile){
            fragment=new Fragment_Home();
            frag_manager=getSupportFragmentManager();
            Frag_Transaction=frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout,fragment);
            Frag_Transaction.commit();
        } else if (fragment instanceof Fragment_notice){
            fragment=new Fragment_Home();
            frag_manager=getSupportFragmentManager();
            Frag_Transaction=frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout,fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof FragmentAlbum){
            fragment=new Fragment_Home();
            frag_manager=getSupportFragmentManager();
            Frag_Transaction=frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout,fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof FragmentBill){
            fragment=new Fragment_Home();
            frag_manager=getSupportFragmentManager();
            Frag_Transaction=frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout,fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof FragmentBooking){
            fragment=new Fragment_Home();
            frag_manager=getSupportFragmentManager();
            Frag_Transaction=frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout,fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof FragmentDirectory){
            fragment=new Fragment_Home();
            frag_manager=getSupportFragmentManager();
            Frag_Transaction=frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout,fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof FragmentEvent){
            fragment=new Fragment_Home();
            frag_manager=getSupportFragmentManager();
            Frag_Transaction=frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout,fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof Fragment_Fund){
            fragment=new Fragment_Home();
            frag_manager=getSupportFragmentManager();
            Frag_Transaction=frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout,fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof FragmentMeeting){
            fragment=new Fragment_Home();
            frag_manager=getSupportFragmentManager();
            Frag_Transaction=frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout,fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof Fragment_Complain) {
            fragment = new Fragment_Home();
            frag_manager = getSupportFragmentManager();
            Frag_Transaction = frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout, fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof VisitorDirectoryFragment){
            fragment = new FragmentDirectory();
            frag_manager = getSupportFragmentManager();
            Frag_Transaction = frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout,fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof MemberDirectoryFragment) {
            fragment = new FragmentDirectory();
            frag_manager = getSupportFragmentManager();
            Frag_Transaction = frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout, fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof VendorDirectoryFragment) {
            fragment = new FragmentDirectory();
            frag_manager = getSupportFragmentManager();
            Frag_Transaction = frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout, fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof GuestDirectoryFragment) {
            fragment = new FragmentDirectory();
            frag_manager = getSupportFragmentManager();
            Frag_Transaction = frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout, fragment);
            Frag_Transaction.commit();
        }else if (fragment instanceof ContactFragment) {
            fragment = new Fragment_Home();
            frag_manager = getSupportFragmentManager();
            Frag_Transaction = frag_manager.beginTransaction();
            Frag_Transaction.replace(R.id.frame_layout, fragment);
            Frag_Transaction.commit();
        }else{
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Are you sure, you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }

    private void add() {
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        final DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)

        {
            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        fragment=new Fragment_Home();
                        break;

                    case R.id.nav_fund:
                        fragment=new Fragment_Fund();
                        break;

                    case R.id.nav_bill:
                        fragment=new FragmentBill();
                        break;
                    case R.id.nav_directory:
                        fragment=new FragmentDirectory();
                        break;
                    case R.id.nav_booking:
                        fragment=new FragmentBooking();
                        break;
                    case R.id.nav_Event:
                        fragment=new FragmentEvent();
                        break;
                    case R.id.nav_meeting:
                        fragment=new FragmentMeeting();
                        break;
                    case R.id.nav_album:
                        fragment=new FragmentAlbum();
                        break;
                    case R.id.nav_notice:
                        fragment=new Fragment_notice();
                        break;
                    case R.id.nav_complain:
                        fragment=new Fragment_Complain();
                        break;
                    case R.id.action_log_out:
                        Intent intent=new Intent(DashboardScreen.this, LoginScreen.class);
                        startActivity(intent);
                        finish();
                    case R.id.nav_profile:
                        fragment=new Fragment_Profile();
                        frag_manager=getSupportFragmentManager();
                        Frag_Transaction=frag_manager.beginTransaction();
                        DashboardScreen.toolbar.setTitle("Profile");
                        Frag_Transaction.replace(R.id.frame_layout,fragment);
                        Frag_Transaction.commit();
                }
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.frame_layout,fragment);
                ft.commit();
                toolbar.setTitle(menuItem.getTitle());
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

   public void SHARE(MenuItem item) {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"App Link");
        startActivity(Intent.createChooser(intent,"Share Via"));
    }
}