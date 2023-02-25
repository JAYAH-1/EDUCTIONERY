package com.example.eductionery.Viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.eductionery.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;
    public  TextView nameView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.categoryimage);
        nameView = itemView.findViewById(R.id.categoryname);

    }
}
