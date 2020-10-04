package com.smallacademy.userroles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class channel_Doctor extends AppCompatActivity {
    Button adddoc;
    boolean valid = true;
    EditText doctorname,docotrpet,doctorbreed,doctordescription,docotornopet;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    // nav
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private NavigationView mNavigationView;
    // nav end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel__doctor);

        adddoc            = findViewById(R.id.doctorchannelbutton);
        doctorname        = findViewById(R.id.doctorname);
        docotrpet         = findViewById(R.id.docotrpet);
        doctorbreed       = findViewById(R.id.doctorbreed);
        doctordescription = findViewById(R.id.doctordescription);
        docotornopet      = findViewById(R.id.docotornopet);
        fAuth       = FirebaseAuth.getInstance();
        fStore      = FirebaseFirestore.getInstance();

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

        adddoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(doctorname);
                checkField(docotrpet);
                checkField(doctorbreed);
                checkField(doctordescription);
                checkField(docotornopet);



                if(valid){


                    Toast.makeText(channel_Doctor.this, "Doctor channeled", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder mydialog = new AlertDialog.Builder(channel_Doctor.this);
                    int tot  = Integer.parseInt(docotornopet.getText().toString());
                    int total = 100 * tot;


                    mydialog.setTitle("The Payment for channeling Doctor is LKR: " +total);
                    mydialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseUser user = fAuth.getCurrentUser();
                                    CollectionReference df = fStore.collection("ChannelDoctor");
                                    Map<String,Object> docinfo = new HashMap<>();
                                    docinfo.put("doctorname", doctorname.getText().toString() );
                                    docinfo.put("docotrpet", docotrpet.getText().toString());
                                    docinfo.put("doctorbreed", doctorbreed.getText().toString());
                                    docinfo.put("doctordescription", doctordescription.getText().toString());
                                    docinfo.put("docotornopet", docotornopet.getText().toString());
                                    docinfo.put("user",user.getUid());

                                    df.add(docinfo);
                                    startActivity(new Intent(getApplicationContext(), MyChannelDoctors.class));
                                    finish();

                                }
                            });

                    mydialog.show();

                }


            }
        });


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

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
}