package com.example.eductionery;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eductionery.Adapters.cartAdater;
import com.example.eductionery.Model.cartItems;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class    AddToCart extends Fragment {



    private  RecyclerView recyclerView;
    private   FirebaseFirestore db;
    private  FirebaseAuth auth;
    private cartAdater  adapter;
    private ImageView view;

     private CollectionReference cartRef = db.collection("UserCart");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_add_to_cart, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser user  = auth.getCurrentUser();
        String id  =  user.getUid();
        recyclerView = view.findViewById(R.id.cartItems);
       view = view.findViewById(R.id.nocart);


        db = FirebaseFirestore.getInstance();


       Query query  =  db.collection("UserCart").document(id).collection("itemsAdded")
               .whereEqualTo("state" ,"isAdded");





     if(query != null){

         FirestoreRecyclerOptions<cartItems> cartView = new FirestoreRecyclerOptions.Builder<cartItems>()
                 .setQuery(query, cartItems.class)
                 .build();

         adapter = new cartAdater(cartView);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         recyclerView.setAdapter(adapter);
         view.setVisibility(View.GONE);
     }
      else{
         Toast.makeText(getActivity(), "Cart still emep", Toast.LENGTH_SHORT).show();
         view.setVisibility(View.VISIBLE);

     }


    }


}