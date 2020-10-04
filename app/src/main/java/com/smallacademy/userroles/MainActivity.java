package com.smallacademy.userroles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // nav
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private NavigationView mNavigationView;
    // nav end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logout = findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
        //nav
        toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int title = menuItem.getItemId();
                switch (title){
                    case R.id.menu_channel : startActivity(new Intent(getApplicationContext(), channel_Doctor.class)); break;
                    case R.id.menu_RegisterVet : startActivity(new Intent(getApplicationContext(), RegisterVet.class)); break;
                    case R.id.menu_careCenter : startActivity(new Intent(getApplicationContext(), ViewCareCenter.class)); break;
                    case R.id.menu_viewVet : startActivity(new Intent(getApplicationContext(), ViewVet.class)); break;
                    case R.id.menu_viewPets : startActivity(new Intent(getApplicationContext(), PetManagment.class)); break;
                    case R.id.menu_viewChanneledDoc : startActivity(new Intent(getApplicationContext(), MyChannelDoctors.class)); break;
                    case R.id.menu_myPets : startActivity(new Intent(getApplicationContext(), mypet.class)); break;
                    case R.id.menu_myCareCenter : startActivity(new Intent(getApplicationContext(), MyCareCenter.class)); break;
                    case R.id.menu_editProfile : startActivity(new Intent(getApplicationContext(), EditProfile.class)); break;
                    case R.id.menu_logout : startActivity(new Intent(getApplicationContext(), MainActivity.class)); break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        //nav end
    }
    // nav
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // nav end
}