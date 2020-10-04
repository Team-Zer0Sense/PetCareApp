package com.smallacademy.userroles;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PetManagment extends AppCompatActivity {

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
        setContentView(R.layout.activity_pet_managment2);

        fAuth       = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.recview);

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

        //query
        Query query = fStore.collection("MyPets");
        //recyle option
        FirestoreRecyclerOptions<PetModel> options = new FirestoreRecyclerOptions.Builder<PetModel>()
                .setQuery(query, new SnapshotParser<PetModel>() {
                    @NonNull
                    @Override
                    public PetModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                      return new PetModel(snapshot.getId(),snapshot.getString("name"),snapshot.getString("pet"), snapshot.getString("breed"), snapshot.getString("appearance") );
                    }
                })
                .build();

        adapter = new FirestoreRecyclerAdapter<PetModel, PetViewHolder>(options) {
            @NonNull
            @Override
            public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pet_item, parent, false);

                return new PetViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PetViewHolder holder, int position, @NonNull PetModel model) {
                holder.model = model;
                holder.list_name.setText(model.getName());
                holder.list_pet.setText(model.getPet());
                holder.list_breed.setText(model.getBreed());
                holder.list_appearance.setText(model.getAppearance());
            }
        };

        //view holder
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this ));
        mFirestoreList.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddPet.class));
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

    private class PetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private PetModel model;
        private TextView list_name;
        private TextView list_pet;
        private TextView list_breed;
        private TextView list_appearance;


        public PetViewHolder (@NonNull View itemView){
            super(itemView);

            list_name = itemView.findViewById(R.id.viewpetname);
            list_pet = itemView.findViewById(R.id.viewpetbreed);
            list_breed = itemView.findViewById(R.id.viewpetbreed);
            list_appearance = itemView.findViewById(R.id.viewpetappearance);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Intent intent   = new Intent(getApplicationContext(), UpdatePet.class);
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