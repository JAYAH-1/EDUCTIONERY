package com.example.eductionery.Viewholder;

import android.media.Image;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eductionery.Interface.ItemClickListner;
import com.example.eductionery.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public TextView itemType , itemName ,itemLocation;
   public ImageView itemPic;
    private ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

      //  itemPic = (ImageView) itemView.findViewById(R.id.profileImage);
       // itemName = (TextView) itemView.findViewById(R.id.item_Desc);
       //  itemType =(TextView) itemView.findViewById(R.id.itemType);
       // itemLocation =(TextView) itemView.findViewById(R.id.SchoolitemLocation);
    ;
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner =listner;
    }
    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
