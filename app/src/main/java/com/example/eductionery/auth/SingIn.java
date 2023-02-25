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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import io.paperdb.Paper;


import com.example.eductionery.Feed;
import com.example.eductionery.Helper.remindmeHelper;
import com.example.eductionery.Model.Users;
import com.example.eductionery.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;


public class SingIn extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    private FirebaseAuth mAuth;

    FirebaseFirestore db;
     private Button logIn;
     private EditText username , password;
     private TextView toSignIn;
     private CheckBox remember;
     private ProgressDialog loadingBar;
    private static final String TAG = "GOOGLEAUTH";
    FirebaseUser firebaseUser;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        logIn = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loadingBar = new ProgressDialog(this);
        remember =(CheckBox) findViewById(R.id.rememberMe) ;
        toSignIn = (TextView) findViewById((R.id.toSignIn)) ;
        googleBtn = (ImageView) findViewById(R.id.authgoog);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser  = mAuth.getCurrentUser();


        gso  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);





        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingIn.this, SignUp.class);
                startActivity(intent);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogInuser();
            }
        });
    }

    private void LogInuser() {
        mAuth = FirebaseAuth.getInstance();

       String name  = username.getText().toString();
       String pass = password.getText().toString();

       if(TextUtils.isEmpty(name)  ){
           Toast.makeText(this, "Pleas put your user name", Toast.LENGTH_SHORT).show();
       }else if(TextUtils.isEmpty(pass)){
           Toast.makeText(this, "Pleas put your password", Toast.LENGTH_SHORT).show();
       }else{

           loadingBar.setTitle("Logging In");
           loadingBar.setMessage("Please wait ");
           loadingBar.setCanceledOnTouchOutside(false);
           loadingBar.show();


           mAuth.signInWithEmailAndPassword(name, pass)
                   .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               // Sign in success, update UI with the signed-in user's information
                               Toast.makeText(SingIn.this, "Successful",
                                       Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(SingIn.this, Feed.class);
                               loadingBar.dismiss();
                               startActivity(intent);
                           } else {
                               // If sign in fails, display a message to the user.
                               Log.w(TAG, "signInWithEmail:failure", task.getException());
                               Toast.makeText(SingIn.this, "Please check your email or password",
                                       Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                           }
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                     Toast.makeText(SingIn.this, "Theres an error",
                                   Toast.LENGTH_SHORT).show();
                       }
                   });

       }

    }

    private void validateCredentials() {

        db = FirebaseFirestore.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        String Username = mAuth.getCurrentUser().getDisplayName();
        String Email = mAuth.getCurrentUser().getEmail();

        HashMap<String, Object> authGoogle = new HashMap<>();
        authGoogle.put("username", Username);
        authGoogle.put("email", Email);
        FirebaseFirestore doc = FirebaseFirestore.getInstance();

        if(id !=null && Username != null && Email != null){

            db.collection("FirebaseAuthUsers").document(id)
                    .set(authGoogle).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(SingIn.this, "Google Account saved !!.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(SingIn.this, "ID IS NOT NULL  Account NOT saved !!.", Toast.LENGTH_SHORT).show();
        }


    }

   public  void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);

        validateCredentials();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                navigateToSecondActivity();
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {

                System.out.println("Erppr"+e);

            }
        }

    }

    public  void navigateToSecondActivity() {
             finish();
            Intent intent = new Intent(this, Feed.class);
             startActivity(intent);
         }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(SingIn.this,"Signing in Success !",Toast.LENGTH_SHORT).show();

                            //  updateUI(user);
                        } else {

                            Toast.makeText(SingIn.this,"Login failed",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}

