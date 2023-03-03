package com.example.eductionery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.LinearLayout;

public class HolderFragments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment fragment = new createPost();
        setContentView(R.layout.activity_holder_fragments);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,fragment).commit();

    }
}