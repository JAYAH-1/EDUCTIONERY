package com.example.eductionery.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eductionery.Model.Users;
import com.example.eductionery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class SignUp extends AppCompatActivity {

    private Button Signup;
    private EditText fullname , address ,email, passrd, confirmpass;
    private ProgressDialog loadingBar;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    String postal;
    String street;
    String userType;
    String region ;
    String phone;
    final static public  String USER_KEY = "USER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);

        fullname =(EditText) findViewById(R.id.name);
        email =(EditText) findViewById(R.id.email);
        passrd = (EditText) findViewById(R.id.password);
        confirmpass =(EditText) findViewById(R.id.confirmpass);
        loadingBar = new ProgressDialog(this);
        Signup = (Button) findViewById(R.id.signupbtb);
        auth = FirebaseAuth.getInstance();

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAccount();


            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().signOut();
    }

    private void CreateAccount() {

        String name = fullname.getText().toString();
        String addrs = address.getText().toString();
        String password = passrd.getText().toString();
        String confirmed = confirmpass.getText().toString();
        String mail = email.getText().toString();
        postal="";
        street="";
        userType="User";
        region ="";
        phone= "";


        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(mail))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else if(password.length() <6){
            Toast.makeText(this, "Password length is too short..", Toast.LENGTH_SHORT).show();
        }
        else
        {

            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

         if(auth != null){
             auth.createUserWithEmailAndPassword(mail, password)
                     .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                         private static final String TAG = "";

                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()) {
                                 // Sign in success, update UI with the signed-in user's information
                                 if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                     Toast.makeText(SignUp.this,
                                             "User with this email already exist.", Toast.LENGTH_SHORT).show();

                                     loadingBar.dismiss();

                                 }
                             }
                              else if (!task.isSuccessful()){
                                 Toast.makeText(SignUp.this,
                                 "Wrong", Toast.LENGTH_SHORT).show();
                             loadingBar.dismiss();


                             }else {

                                 saveAccount(userType, phone, name,mail , password,confirmed,postal,street,region);
                                 Toast.makeText(SignUp.this,
                                         "Successfully Created.", Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(SignUp.this, SingIn.class);

                                 loadingBar.dismiss();

                             }

                         }
                     }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                         @Override
                         public void onSuccess(AuthResult authResult) {
                             saveAccount(userType, phone, name,mail , password,confirmed,postal,street,region);
                             Toast.makeText(SignUp.this,
                                     "Successfully Created.", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(SignUp.this, SingIn.class);

                             loadingBar.dismiss();
                         }
                     });

         }




        }
    }


    private void saveAccount(String userType,String phone, String fullname, String email, String password, String confirmedPass, String postal, String street, String region){
        FirebaseAuth aut = FirebaseAuth.getInstance();
        String id  = aut.getUid().toString();
        HashMap<String, Object> NormalAccount  = new HashMap<>();
        NormalAccount.put("fullname", fullname);
        NormalAccount.put("userType", userType);
        NormalAccount.put("address", address);
        NormalAccount.put("email", email);
        NormalAccount.put("password", password);
        NormalAccount.put("street", street);
        NormalAccount.put("region", region);
        NormalAccount.put("confirmedPass", confirmedPass);
        NormalAccount.put("postal", postal);
        NormalAccount.put("phone", phone);


            db = FirebaseFirestore.getInstance();
            db.collection("FirebaseAuthUsers").document(id)
                    .set(NormalAccount).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(SignUp.this, "Account Successfully created.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), SingIn.class);
                            startActivity(i);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUp.this, "Something went wrong while creating the account.", Toast.LENGTH_SHORT).show();

                        }
                    });


        }
}