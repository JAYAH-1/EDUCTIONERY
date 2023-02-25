package com.example.eductionery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eductionery.auth.SignUp;
import com.example.eductionery.auth.SingIn;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class StarterScreen extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private Button login ,asSeller;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    GoogleSignInAccount acct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter_screen);
        mAuth = FirebaseAuth.getInstance();

        login = (Button) findViewById(R.id.startlogin);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
        acct = GoogleSignIn.getLastSignedInAccount(this);

         if(checkCurrentuser()==true) {
           //  Intent intent = new Intent(StarterScreen.this, MainActivity.class);
           //  startActivity(intent);
         }else{

             login.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(StarterScreen.this, SingIn.class);
                     startActivity(intent);
                 }

             });
         }



    }
    public  boolean checkCurrentuser() {
        if (acct != null) {

            return true;
        } else {
            return false;
        }
    }
}