package com.example.eductionery.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.example.eductionery.Model.items;
import com.example.eductionery.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.internal.cache.DiskLruCache;

public class postAdapter extends FirestoreRecyclerAdapter<items,postAdapter.myviewholder> {
    ArrayList<items> itemlist;
    private CardView cardView;
    Context context;

    private OnItemClickListener listener;

    public postAdapter(@NonNull FirestoreRecyclerOptions<items> options) {
        super(options);
    }


    public void filterData(ArrayList <items> itemsList){
        this.itemlist = itemsList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public postAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post,parent,false);
        return new myviewholder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull items model) {

       context = holder.post.getContext();
        holder.itemname.setText("Name: "+model.getItemname());
        holder.tag.setText("Tag: "+model.getItemtag());
        holder.description.setText("Description: "+model.getItemdesc());
        holder.categories.setText("Category:" +model.getItemcategories());

    if(model != null){
        Glide.with(context)
                .load(model.getImgurl())
                .into(holder.post);

    }

    }


  class myviewholder extends RecyclerView.ViewHolder
    {
        TextView tag,itemname ,description,categories;
        ImageView post;

        public myviewholder(@NonNull View itemView) {

            super(itemView);

            tag = itemView.findViewById(R.id.tag);
            itemname = itemView.findViewById(R.id.itemName);
            description = itemView.findViewById(R.id.description);
            categories = itemView.findViewById(R.id.ctgries);
            post = itemView.findViewById(R.id.post);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {

                        listener.onItemClick(getSnapshots().getSnapshot(position), position);

                    }
                }
            });

        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
