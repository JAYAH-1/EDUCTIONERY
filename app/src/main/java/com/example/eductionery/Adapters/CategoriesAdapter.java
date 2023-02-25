package com.example.eductionery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eductionery.R;
import com.example.eductionery.Model.Categories;
import com.example.eductionery.Viewholder.MyViewHolder;

public class CategoriesAdapter extends  RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List<Categories> items;

    public CategoriesAdapter(Context context,   List<Categories> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getCategoryName());
        Glide.with(context)
                .load(items.get(position).getImageCategory())

                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
