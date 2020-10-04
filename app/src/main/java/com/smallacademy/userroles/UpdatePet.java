package com.smallacademy.userroles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdatePet extends AppCompatActivity {
    Button Updatemypet,deletemypet;
    boolean valid = true;
    EditText name,pet,breed,appearance;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private PetModel petm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pet);

        petm = (PetModel) getIntent().getSerializableExtra("model");

        Updatemypet = findViewById(R.id.editpetupdatebutton);
        name        = findViewById(R.id.editpetname);
        pet         = findViewById(R.id.editpetpet);
        breed       = findViewById(R.id.editpetbreed);
        appearance  = findViewById(R.id.editpetappearance);
        deletemypet = findViewById(R.id.deletemypet);
        fAuth       = FirebaseAuth.getInstance();
        fStore      = FirebaseFirestore.getInstance();


        name.setText(petm.getName());
        pet.setText(petm.getPet());
        breed.setText(petm.getBreed());
        appearance.setText(petm.getAppearance());




        Updatemypet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(name);
                checkField(pet);
                checkField(breed);
                checkField(appearance);



                if(valid){

                    PetModel pets = new PetModel(
                            petm.getId(),name.getText().toString(),pet.getText().toString(),breed.getText().toString(),appearance.getText().toString()
                    );


                    FirebaseUser user = fAuth.getCurrentUser();
                    fStore.collection("MyPets").document(petm.getId())
                            .set(pets)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(UpdatePet.this,"Pet Updated",Toast.LENGTH_LONG).show();
                                }
                            });

                    startActivity(new Intent(getApplicationContext(), PetManagment.class));
                }


            }
        });

        deletemypet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("MyPets").document(petm.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdatePet.this,"Pet Deleted",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), PetManagment.class));
                            }
                        });

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