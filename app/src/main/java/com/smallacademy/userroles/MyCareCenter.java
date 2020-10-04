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

public class MyCareCenter extends AppCompatActivity {
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
        setContentView(R.layout.activity_my_care_center);

        fAuth       = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.mycarecneterview);

        FirebaseUser user = fAuth.getCurrentUser();


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

        //query
        CollectionReference docs = fStore.collection("CareCenter");
        Query query = docs.whereEqualTo("user", user.getUid());
        //recyle option
        FirestoreRecyclerOptions<CareCenterModel> options = new FirestoreRecyclerOptions.Builder<CareCenterModel>()
                .setQuery(query, new SnapshotParser<CareCenterModel>() {
                    @NonNull
                    @Override
                    public CareCenterModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        return new CareCenterModel(snapshot.getId(),snapshot.getString("CareCenter"),snapshot.getString("FirstName"), snapshot.getString("LastName"), snapshot.getString("Mobile"), snapshot.getString("Email"), snapshot.getString("Address"));
                    }
                })
                .build();

        adapter = new FirestoreRecyclerAdapter<CareCenterModel, MyCareCenter.CarecenterViewHolder>(options) {
            @NonNull
            @Override
            public MyCareCenter.CarecenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_carecenter_item, parent, false);

                return new MyCareCenter.CarecenterViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyCareCenter.CarecenterViewHolder holder, int position, @NonNull CareCenterModel model) {
                holder.model = model;
                holder.CenterName.setText(model.getCenterName());
                holder.FirstName.setText(model.getFirstName());
                holder.Mobile.setText(model.getMobile());
                holder.Address.setText(model.getAddress());




            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddCareCenter.class));
            }
        });

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

    private class CarecenterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CareCenterModel model;
        private TextView CenterName;;
        private TextView FirstName;
        private TextView Mobile;
        private TextView Address;


        public CarecenterViewHolder(@NonNull View itemView) {
            super(itemView);
            CenterName = itemView.findViewById(R.id.viewcentername);
            FirstName = itemView.findViewById(R.id.viewcarecenterfirstname);
            Mobile = itemView.findViewById(R.id.viewcarecentermobile);
            Address = itemView.findViewById(R.id.viewcarecentermobile);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Intent intent   = new Intent(getApplicationContext(), UpdateCareCenter.class);
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