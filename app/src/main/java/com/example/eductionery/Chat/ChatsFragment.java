package com.example.eductionery.Chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eductionery.Adapters.UserAdapter;
import com.example.eductionery.Model.Chatslist;
import com.example.eductionery.Model.Users;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.eductionery.R;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {


    private static final String TAG ="chats" ;
    List<Chatslist> userlist;
    List<Users> mUsers;
    RecyclerView recyclerView;
    UserAdapter mAdapter;
    FirebaseUser firebaseUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chats, container, false);


        userlist = new ArrayList<>();

        recyclerView = view.findViewById(R.id.chat_recyclerview_chatfrag);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


         if(firebaseUser !=null){
             FirebaseFirestore db = FirebaseFirestore.getInstance();

             CollectionReference chatsListRef = db.collection("Chatslist");

             chatsListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                 @Override
                 public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                     if (error != null) {
                         Log.d(TAG, "Error getting chats list: ", error);
                         return;
                     }

                     userlist.clear();
                     for (QueryDocumentSnapshot doc : snapshot) {
                         Chatslist chatslist = doc.toObject(Chatslist.class);
                         userlist.add(chatslist);
                     }

                     ChatsListings();
                 }
             });


         }


        return view;
    }

    private void ChatsListings() {

        mUsers = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("FirebaseAuthUsers")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        mUsers.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            Users users = doc.toObject(Users.class);

                            for (Chatslist chatslist : userlist) {
                                if (users.getUserID().equals(chatslist.getId())) {
                                    mUsers.add(users);
                                }
                            }
                        }

                        mAdapter = new UserAdapter(getContext(), mUsers, true);
                        recyclerView.setAdapter(mAdapter);
                    }
                });


    }
}