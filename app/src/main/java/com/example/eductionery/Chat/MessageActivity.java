package com.example.eductionery.Chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eductionery.Adapters.MessageAdapter;
import com.example.eductionery.Model.Chats;
import com.example.eductionery.R;
import com.example.eductionery.Model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {


    private static final String TAG = "chat";
    String friendid, message, myid;
    CircleImageView imageViewOnToolbar;
    TextView usernameonToolbar;
    Toolbar toolbar;
    FirebaseUser firebaseUser;

    EditText et_message;
    Button send;

    DatabaseReference reference;

    List<Chats> chatsList;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView122;
    ValueEventListener seenlistener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        toolbar = findViewById(R.id.toolbar_message);

        toolbar = findViewById(R.id.toolbar_message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageViewOnToolbar = findViewById(R.id.profile_image_toolbar_message);
        usernameonToolbar = findViewById(R.id.username_ontoolbar_message);

        recyclerView122 = findViewById(R.id.recyclerview_messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView122.setLayoutManager(layoutManager);

        send = findViewById(R.id.send_messsage_btn);
        et_message = findViewById(R.id.edit_message_text);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            myid = firebaseUser.getUid(); // my id or the one who is loggedin
        }


        friendid = getIntent().getStringExtra("friendid"); // retreive the friendid when we click on the item

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null){
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference docRef = db.collection("FirebaseAuthUsers").document(friendid);
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.w(TAG, "Listen failed.", error);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Users users = snapshot.toObject(Users.class);

                        usernameonToolbar.setText(users.getFullname()); // set the text of the user on textivew in toolbar
                          if(users.getImgurl() !=null){
                              if (users.getImgurl().equals("default")) {
                                  imageViewOnToolbar.setImageResource(R.drawable.user);
                              } else {
                                  Glide.with(getApplicationContext()).load(users.getImgurl()).into(imageViewOnToolbar);
                              }
                              readMessages(myid, friendid, users.getImgurl());
                          }





                    } else {
                        Log.d(TAG, "Current data: null",error);
                    }
                }
            });
        }

        seenMessage(friendid);






        et_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (s.toString().length() > 0) {

                    send.setEnabled(true);

                } else {

                    send.setEnabled(false);


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text = et_message.getText().toString();

                if (!text.startsWith(" ")) {
                    et_message.getText().insert(0, " ");

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                message = et_message.getText().toString();

                sendMessage(myid, friendid, message);

                et_message.setText(" ");


            }
        });




    }



    private void seenMessage(final String friendid) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colRef = db.collection("Chats");
        Query query = colRef.whereEqualTo("reciever", myid).whereEqualTo("sender", friendid);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }

                if (snapshot != null) {
                    for (QueryDocumentSnapshot ds : snapshot) {
                        Chats chats = ds.toObject(Chats.class);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        ds.getReference().update(hashMap);
                    }
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });






    }

    private void readMessages(final String myid, final String friendid, final String imageURL) {

        chatsList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

       FirebaseAuth auth = FirebaseAuth.getInstance();
       FirebaseUser user = auth.getCurrentUser();



        CollectionReference chatsRef = db.collection("Chats");

        chatsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }

                chatsList.clear();

                for (QueryDocumentSnapshot doc : value) {
                    Chats chats = doc.toObject(Chats.class);
                       if(chats.getSender() !=null && chats.getReceiver()!=null){
                           if (chats.getSender().equals(myid) && chats.getReceiver().equals(friendid) ||
                                   chats.getSender().equals(friendid) && chats.getReceiver().equals(myid)) {
                               chatsList.add(chats);
                               Log.w(TAG, "MESESGA YAWA"+chats.getMessage(), error);

                           }
                       }

                }

                messageAdapter = new MessageAdapter(MessageActivity.this, chatsList, imageURL);
                recyclerView122.setAdapter(messageAdapter);
            }
        });

    }

    private void sendMessage(final String myid, final String friendid, final String message) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myid);
        hashMap.put("receiver", friendid);
        hashMap.put("message", message);
        hashMap.put("isseen", false);

        db.collection("Chats").document(myid)
                .set(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MessageActivity.this, "CHAT SAVED", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MessageActivity.this, "FAILED TP SAVE CHAT DATA ", Toast.LENGTH_SHORT).show();
                    }
                });

        db.collection("Chatslist").document(myid)
                .set(new HashMap<String, Object>() {{
                    put("id", friendid);
                }})
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " );
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });


    }




    private void Status (final String status) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(firebaseUser !=null){
            DocumentReference docRef = db.collection("Chatslist").document(firebaseUser.getUid());

            docRef.update("status", status)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "User status updated successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating user status", e);
                        }
                    });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        Status("online");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Status("offline");

    }


}