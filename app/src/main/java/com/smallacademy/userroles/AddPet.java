package com.smallacademy.userroles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddPet extends AppCompatActivity {

    Button addmypet;
    boolean valid = true;
    EditText name,pet,breed,appearance;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

            addmypet    = findViewById(R.id.addpetbutton);
            name        = findViewById(R.id.addpetname);
            pet         = findViewById(R.id.addpet);
            breed       = findViewById(R.id.addbreed);
            appearance  = findViewById(R.id.addappearance);
            fAuth       = FirebaseAuth.getInstance();
            fStore      = FirebaseFirestore.getInstance();


        addmypet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(name);
                checkField(pet);
                checkField(breed);
                checkField(appearance);



                if(valid){

                    Toast.makeText(AddPet.this, "My Pet Added", Toast.LENGTH_LONG).show();
                    FirebaseUser user = fAuth.getCurrentUser();
                    CollectionReference df = fStore.collection("MyPets");
                    Map<String,Object> petinfo = new HashMap<>();
                    petinfo.put("name", name.getText().toString() );
                    petinfo.put("pet", pet.getText().toString());
                    petinfo.put("breed", breed.getText().toString());
                    petinfo.put("appearance", appearance.getText().toString());
                    petinfo.put("user",user.getUid());

                    df.add(petinfo);
                    startActivity(new Intent(getApplicationContext(), PetManagment.class));
                    finish();

                }


            }
        });


    }


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