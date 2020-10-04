package com.smallacademy.userroles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MyChannelDoctors extends AppCompatActivity {
    private FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;

    // nav
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private NavigationView mNavigationView;
    // nav end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_channel_doctors);

        fAuth       = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.doctorchanellview);

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

        FirebaseUser user = fAuth.getCurrentUser();
        CollectionReference docs = fStore.collection("ChannelDoctor");

        Query query = docs.whereEqualTo("user", user.getUid());
        //recyle option
        FirestoreRecyclerOptions<ChannelDoctorModel> options = new FirestoreRecyclerOptions.Builder<ChannelDoctorModel>()
                .setQuery(query, new SnapshotParser<ChannelDoctorModel>() {
                    @NonNull
                    @Override
                    public ChannelDoctorModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        return new ChannelDoctorModel(snapshot.getId(),snapshot.getString("doctorname"),snapshot.getString("docotrpet"), snapshot.getString("doctorbreed"), snapshot.getString("doctordescription"), snapshot.getString("docotornopet"));
                    }
                })
                .build();

        adapter = new FirestoreRecyclerAdapter<ChannelDoctorModel, MyChannelDoctors.DoctViewHolder>(options) {
            @NonNull
            @Override
            public MyChannelDoctors.DoctViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_doctors, parent, false);

                return new MyChannelDoctors.DoctViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DoctViewHolder holder, int position, @NonNull ChannelDoctorModel model) {
                holder.model = model;
                holder.doctorname.setText(model.getDoctorname());
                holder.docotrpet.setText(model.getDocotrpet());
                holder.docotornopet.setText( model.getDocotornopet());




            }
        };



        //view holder

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this ));
        mFirestoreList.setAdapter(adapter);




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

    private class DoctViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ChannelDoctorModel model;
        private TextView doctorname;;
        private TextView docotrpet;
        private TextView doctorbreed;
        private TextView doctordescription;
        private TextView docotornopet;



        public DoctViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorname = itemView.findViewById(R.id.doctorname);
            docotrpet = itemView.findViewById(R.id.docotrpet);
            docotornopet = itemView.findViewById(R.id.docotornopet);


        }

        @Override
        public void onClick(View view) {
            Intent intent   = new Intent(getApplicationContext(), MyChannelDoctors.class);
            intent.putExtra("model", model);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}