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

public class UpdateCareCenter extends AppCompatActivity {
    Button Updatecenter,deleteBtn;
    boolean valid = true;
    EditText CenterName,FirstName,LastName,Mobile,Email,Address;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private CareCenterModel petm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_care_center);


        petm = (CareCenterModel) getIntent().getSerializableExtra("model");

        Updatecenter  = findViewById(R.id.editcarecenteredit);
        CenterName   = findViewById(R.id.editcarecentername);
        FirstName   = findViewById(R.id.editcarecenterfirstname);
        LastName    = findViewById(R.id.editcarecenterlastname);
        Mobile      = findViewById(R.id.editcarecentermobile);
        Email       = findViewById(R.id.editcarecentermail);
        Address     = findViewById(R.id.editcarecenteraddress);
        deleteBtn   = findViewById(R.id.button3);
        fAuth       = FirebaseAuth.getInstance();
        fStore      = FirebaseFirestore.getInstance();



        CenterName.setText(petm.getCenterName());
        FirstName.setText(petm.getFirstName());
        LastName.setText(petm.getLastName());
        Mobile.setText(petm.getMobile());
        Email.setText(petm.getEmail());
        Address.setText(petm.getAddress());




        Updatecenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(CenterName);
                checkField(FirstName);
                checkField(LastName);
                checkField(Mobile);
                checkField(Email);
                checkField(Address);



                if(valid){

                    CareCenterModel pets = new CareCenterModel(
                            petm.getId(),CenterName.getText().toString(),FirstName.getText().toString(),LastName.getText().toString(),Mobile.getText().toString(),Email.getText().toString(),Address.getText().toString()
                    );


                    FirebaseUser user = fAuth.getCurrentUser();
                    fStore.collection("CareCenter").document(petm.getId())
                            .set(pets)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(UpdateCareCenter.this,"Care Center Updated",Toast.LENGTH_LONG);
                                }
                            });

                    startActivity(new Intent(getApplicationContext(), ViewCareCenter.class));
                }


            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("CareCenter").document(petm.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateCareCenter.this,"Care Center Deleted",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MyCareCenter.class));
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
