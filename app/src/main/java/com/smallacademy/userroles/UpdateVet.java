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

public class UpdateVet extends AppCompatActivity {
    Button UpdateVet, RemoveVet;
    boolean valid = true;
    EditText VetName,VetAddress,VetEmail,VetPhone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private VetModel petm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vet);

        petm = (VetModel) getIntent().getSerializableExtra("model");

        UpdateVet   = findViewById(R.id.editvetupdate);
        RemoveVet   = findViewById(R.id.editremovebtn);
        VetName        = findViewById(R.id.editvetname);
        VetAddress         = findViewById(R.id.editvetaddress);
        VetEmail       = findViewById(R.id.editveremail);
        VetPhone  = findViewById(R.id.editvetphone);
        fAuth       = FirebaseAuth.getInstance();
        fStore      = FirebaseFirestore.getInstance();


        VetName.setText(petm.getVetName());
        VetAddress.setText(petm.getVetAddress());
        VetEmail.setText(petm.getVetEmail());
        VetPhone.setText(petm.getVetPhone());




        UpdateVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(VetName);
                checkField(VetAddress);
                checkField(VetEmail);
                checkField(VetPhone);



                if(valid){

                    PetModel pets = new PetModel(
                            petm.getId(),VetName.getText().toString(),VetAddress.getText().toString(),VetEmail.getText().toString(),VetPhone.getText().toString()
                    );

                    FirebaseUser user = fAuth.getCurrentUser();
                    fStore.collection("Veterinarian").document(petm.getId())
                            .set(pets)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(UpdateVet.this,"Veterinarian Updated",Toast.LENGTH_LONG);
                                }
                            });

                    startActivity(new Intent(getApplicationContext(), ViewVet.class));
                }


            }
        });
        RemoveVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("Veterinarian").document(petm.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateVet.this,"Veterinarian Deleted",Toast.LENGTH_LONG).show();
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