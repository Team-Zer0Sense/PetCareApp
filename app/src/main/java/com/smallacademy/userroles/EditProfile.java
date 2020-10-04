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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    // nav
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private NavigationView mNavigationView;
    // nav end

    private Button btnDeleteAccount, btnUpdate;
    private EditText txtUserName,txtPassword,txtConfirmPassword, txtPhone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnDeleteAccount = (Button) findViewById(R.id.deleteAccountBtn);
        btnUpdate = (Button) findViewById(R.id.profileupdatebutton);
        txtUserName = (EditText) findViewById(R.id.Username);
        txtPassword = (EditText) findViewById(R.id.profilepassword);
        txtConfirmPassword = (EditText) findViewById(R.id.profilerepassword);
        txtPhone = (EditText) findViewById(R.id.profileaddress);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = txtUserName.getText().toString().trim();
                String Password = txtPassword.getText().toString().trim();
                String ConfirmPassword = txtConfirmPassword.getText().toString().trim();
                String Phone = txtPhone.getText().toString().trim();

                if(UserName.length() < 1 || Password.length() < 6 ||Phone.length() < 1) {
                    new AlertDialog.Builder(EditProfile.this)
                            .setTitle("Error In Fields")
                            .setMessage("Values entered are invalid or empty.")
                            .setNegativeButton(android.R.string.ok, null)
                            .show();
                    return;
                }
                if(!Password.matches(ConfirmPassword)) {
                    new AlertDialog.Builder(EditProfile.this)
                            .setTitle("Password Error")
                            .setMessage("Passwords do not match")
                            .setNegativeButton(android.R.string.ok, null)
                            .show();
                    return;
                }

                FirebaseUser user = fAuth.getCurrentUser();
                assert user != null;
                DocumentReference df = fStore.collection("Users").document(user.getUid());
                Map<String,Object> userInfo = new HashMap<>();
                userInfo.put("FullName", UserName );
                userInfo.put("PhoneNumber", Phone);
                df.update(userInfo);
                user.updatePassword(Password).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        fAuth.signOut();
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();
                    }
                });
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(EditProfile.this)
                        .setTitle("Delete Account")
                        .setMessage("Do you want to delete the account ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUser user = fAuth.getCurrentUser();
                                assert user != null;
                                DocumentReference df = fStore.collection("Users").document(user.getUid());
                                df.delete();
                                user.delete();
                                startActivity(new Intent(getApplicationContext(),Login.class));
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
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
}