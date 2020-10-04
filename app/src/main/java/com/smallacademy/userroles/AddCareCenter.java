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

public class AddCareCenter extends AppCompatActivity {

    Button createBtn, cancelBtn;
    boolean valid = true;
    EditText CenterName,FirstName,LastName,Mobile,Email,Address;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_care_center);

        createBtn   = findViewById(R.id.Addcenter);
        cancelBtn   = findViewById(R.id.AddcancelBtn);
        CenterName   = findViewById(R.id.AddCenterName);
        FirstName   = findViewById(R.id.AddFirstName);
        LastName    = findViewById(R.id.AddLastName);
        Mobile      = findViewById(R.id.mobilenumber);
        Email       = findViewById(R.id.AddEmail);
        Address     = findViewById(R.id.AddAddress);
        fAuth       = FirebaseAuth.getInstance();
        fStore      = FirebaseFirestore.getInstance();


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(CenterName);
                checkField(FirstName);
                checkField(LastName);
                checkField(Mobile);
                checkField(Email);
                checkField(Address);


                if(valid){

                    Toast.makeText(AddCareCenter.this, "Care Center Added", Toast.LENGTH_LONG).show();
                    CollectionReference df = fStore.collection("CareCenter");
                    FirebaseUser user = fAuth.getCurrentUser();
                    Map<String,Object> centernfo = new HashMap<>();
                    centernfo.put("CareCenter",CenterName.getText().toString());
                    centernfo.put("FirstName", FirstName.getText().toString() );
                    centernfo.put("LastName", LastName.getText().toString());
                    centernfo.put("Mobile", Mobile.getText().toString()+ "");
                    centernfo.put("Email", Email.getText().toString());
                    centernfo.put("Address", Address.getText().toString());
                    centernfo.put("user",user.getUid());

                    df.add(centernfo);
                    startActivity(new Intent(getApplicationContext(), ViewCareCenter.class));

                }


            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewCareCenter.class));
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