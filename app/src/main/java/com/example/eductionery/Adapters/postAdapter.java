package com.example.eductionery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.eductionery.Model.items;
import com.example.eductionery.R;
import java.util.ArrayList;

public class postAdapter extends RecyclerView.Adapter<postAdapter.myviewholder>{
    ArrayList<items> itemlist;
    private CardView cardView;
    Context context;

    public postAdapter(Context context ,ArrayList<items> itemlist) {
        this.itemlist = itemlist;
        this.context = context;
    }

    public void filterData(ArrayList <items> itemsList){
        this.itemlist = itemsList;
        notifyDataSetChanged();

    }

    public void add(items item){
        itemlist.add(item);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public postAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postAdapter.myviewholder holder, int position) {
        items post = itemlist.get(position);
        holder.itemname.setText("Name: "+post.getItemname());
        holder.tag.setText("Tag: "+post.getItemtag());
        holder.description.setText("Description: "+post.getItemdesc());
        holder.categories.setText("Category:" +post.getItemcategories());

        Glide.with(context)
                .load(post.getImgurl())
                .into(holder.post);

        ;

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
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

                }
            });

        }
    }
}
