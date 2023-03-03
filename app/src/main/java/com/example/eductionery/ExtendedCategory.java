package com.example.eductionery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class ExtendedCategory extends Fragment {


    LinearLayout books, showCategory,accessories,clothing ,gadgets, uniforms;
    private String[] CategoryList;
    String choosen;
    Bundle bundle;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        books = view.findViewById(R.id.books);
        showCategory = view.findViewById(R.id.CategoryShow);
        accessories = view.findViewById(R.id.accessories);
        clothing = view.findViewById(R.id.clothing);
        gadgets = view.findViewById(R.id.gadgets);
        uniforms = view.findViewById(R.id.SchoolUniforms);

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryList = new String[]{"Programming Books","Accounting Book","Horror","Accesories","Mystery stories","Poetry"};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
                mbuilder.setTitle("Choose from Sub category");
                mbuilder.setIcon(R.drawable.ic_baseline_category_24);
                mbuilder.setSingleChoiceItems(CategoryList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     choosen = CategoryList[i];
                     bundle = new Bundle();
                     bundle.putString("key",choosen);

                    }
                });
                mbuilder.setNeutralButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog mdiaglog = mbuilder.create();
                mdiaglog.show();
            }
        });



        gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CategoryList = new String[]{"Headset","Casual wear ","Casual wear","Lingerie  "};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
                mbuilder.setTitle("Choose from Sub category");
                mbuilder.setIcon(R.drawable.ic_baseline_category_24);
                mbuilder.setSingleChoiceItems(CategoryList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        choosen = CategoryList[i];
                        bundle =new Bundle();
                        bundle.putString("key",choosen);


                    }
                });

                mbuilder.setNeutralButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog mdiaglog = mbuilder.create();
                mdiaglog.show();

            }
        });


        clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryList = new String[]{"Business attire","Casual wear ","Casual wear","Lingerie  "};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
                mbuilder.setTitle("Choose from Sub category");
                mbuilder.setIcon(R.drawable.ic_baseline_category_24);
                mbuilder.setSingleChoiceItems(CategoryList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choosen =CategoryList[i];
                        bundle = new Bundle();
                        bundle.putString("key",choosen);

                    }
                });
                mbuilder.setNeutralButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog mdiaglog = mbuilder.create();
                mdiaglog.show();
            }
        });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.extendedategory, container, false);
    }
}