package com.example.eductionery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eductionery.Model.cartItems;
import com.example.eductionery.Model.items;
import com.example.eductionery.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.makeramen.roundedimageview.RoundedImageView;

public class cartAdater extends FirestoreRecyclerAdapter<cartItems,cartAdater.myviewholder> {

    Context context;


    public cartAdater(@NonNull FirestoreRecyclerOptions<cartItems> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull cartItems model) {

        context = holder.image.getContext();
        holder.name.setText("Name: "+model.getName());
        holder.tag.setText("Tag: "+model.getTag());
        holder.qunant.setText("Quantity: "+model.getQuant());
        holder.price.setText("Price:" +model.getPrice());


        Glide.with(context)
                .load(model.getUrl())
                .into(holder.image);

    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post,parent,false);
        return new cartAdater.myviewholder(view);
    }



    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView tag,qunant ,price,name;
        RoundedImageView image;

        public myviewholder(@NonNull View itemView) {

            super(itemView);

            tag = itemView.findViewById(R.id.cartItemTagHolder);
            name = itemView.findViewById(R.id.cartItemNameHolder);
            price = itemView.findViewById(R.id.cartItemPriceHolder);
            qunant = itemView.findViewById(R.id.cartQuantityHolder);
            image = itemView.findViewById(R.id.cartItemPhoto);


        }
    }
}

