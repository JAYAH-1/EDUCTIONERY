package com.example.eductionery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eductionery.Chat.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;


public class ItemDetails extends Fragment {

    TextView itemName;
    RoundedImageView img;
    String imgurl , id ,url ,price,name,quant;
    Button addtoCart;
    String itemState;
    FrameLayout fr;
    View view;
    ImageView chat;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
         imgurl = getArguments().getString("url");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view= inflater.inflate(R.layout.fragment_item_details, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            id = bundle.getString("id");
            url = bundle.getString("url");
            price = bundle.getString("price");
            name = bundle.getString("name");
            quant= bundle.getString("quant");
        }


 //        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        chat = view.findViewById(R.id.chatSeller);
        img = view.findViewById(R.id.imageDetail);
        itemName = view.findViewById(R.id.itemname);
        addtoCart = view.findViewById(R.id.addToCart);

        if(url != null) {
            Glide.with(getActivity())
                    .load(imgurl)
                    .into(img);
        }
        itemName.setText(name);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateState();
                String state = "isAdded";

                addtocartItem(url,name,price,quant,state);

            }
        });


       return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }


     public void updateState(){

         FirebaseAuth auth = FirebaseAuth.getInstance();
         FirebaseUser user= auth.getCurrentUser();
         String currentuser =  user.getUid();

         FirebaseFirestore db = FirebaseFirestore.getInstance();
         itemState = "AddedToCart";
         db.collection("FirebaseAuthUsers").document(currentuser)
                 .update("itemState",itemState)
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void unused) {
                         Toast.makeText(getContext(), "item State channge to added to cart", Toast.LENGTH_SHORT).show();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(getContext(), "Failed to change", Toast.LENGTH_SHORT).show();
                         Bundle b = new Bundle();
                         b.putString("userID",id);

                         Fragment fragment = new AddToCart();
                         FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                         fragmentTransaction.replace(R.id.itemss, fragment);
                         fragment.setArguments(b);
                         fragmentTransaction.addToBackStack("tag");
                         fragmentTransaction.commit();

                     }
                 });

     }

   public  void addtocartItem(String url ,String name , String price ,String quant,String state ){

        FirebaseFirestore db =FirebaseFirestore.getInstance();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        String   timeItemAdded = currentTime.format(calendar.getTime());


       HashMap<String, Object> itemCart  = new HashMap<>();
       itemCart.put("url", url);
       itemCart.put("name", name);
       itemCart.put("price", price);
       itemCart.put("quantity", quant);
       itemCart.put("timeAdded", timeItemAdded);
       itemCart.put("state", state);

            db.collection("UserCart").document(id).collection("itemsAdded").document()
                    .set(itemCart)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                        }
                    });
   }

    @Override
    public void onStop() {
        super.onStop();
     //   ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}