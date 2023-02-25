package com.example.eductionery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class UserUpdateInfo extends Fragment {

    TextView saveInfo;
    FirebaseFirestore db;
    EditText email;
    EditText phoneNumber;
    EditText Region;
    EditText Postal;
    EditText street;
    String userType;
    String newEmail , phone , region , postal,Street;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_update_info, container, false);
    }

    @Override



    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.updatedEmail);
        phoneNumber = view.findViewById(R.id.updatedPhone);
        Region = view.findViewById(R.id.region);
        Postal = view.findViewById(R.id.postal);
        street = view.findViewById(R.id.street);

        userType =  "USER";
       String accountStatus = "NOT UPDATED";
      saveInfo = view.findViewById(R.id.saveUpdatedInfo);

      db = FirebaseFirestore.getInstance();



      saveInfo.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            userType = "SELLER";
              saveToAccount(userType);
          }
      });

    }

    public void saveToAccount(String userType){

        newEmail = email.getText().toString();
        phone = phoneNumber.getText().toString();
        region = Region.getText().toString();
        postal = Postal.getText().toString();
        Street =street.getText().toString();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();


        String saveTouser = user.getUid();


        Map<String, Object> newInfo = new HashMap<>();
        newInfo.put("email", newEmail);
        newInfo.put("postal", postal);
        newInfo.put("region", region);
        newInfo.put("userType",userType);
        newInfo.put("street", Street);
        newInfo.put("phone",phone );
        newInfo.put("accountStatus", "UPDATED");


        if(user != null){
            db.collection("FirebaseAuthUsers").document(saveTouser)
                    .update(newInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed to updated", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
           else{
            Toast.makeText(getContext(), "No user is signed in at t", Toast.LENGTH_SHORT).show();

        }


    }

}