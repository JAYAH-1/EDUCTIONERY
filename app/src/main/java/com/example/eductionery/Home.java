package com.example.eductionery;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.eductionery.Adapters.CategoriesAdapter;
import com.example.eductionery.Adapters.SliderAdapter;
import com.example.eductionery.Adapters.postAdapter;
import com.example.eductionery.Model.Categories;
import com.example.eductionery.Model.items;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {
    RecyclerView recview;
    SliderView sliderView;
    CategoriesAdapter adapter;
    postAdapter postadapter;
    RecyclerView.LayoutManager layoutManager;
    private final String TAG = "Main2Activity";
    FirebaseFirestore db;
    LinearLayoutManager HorizontalLayout;
    GridLayoutManager grd;
    ArrayList<com.example.eductionery.Model.items> posted;
    ProgressDialog loadingbar;
    SearchView f;
    String data;
    Context context;
    // list

    BottomNavigationView bottomNavigationView;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FloatingActionButton additem;
    List<Categories> items = new ArrayList<>();
    prof p = new prof();
    int spanCount = 2; // 3 columns
    int spacing = 20; // 50px
    boolean includeEdge = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home2, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView1 =  view.findViewById(R.id.postItem);

        recyclerView1.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getContext(),gso);
        posted = new ArrayList<>();
        postadapter = new postAdapter(getContext(), posted);
        recyclerView1.addItemDecoration((new Decoration(spanCount, spacing, includeEdge)));
        recyclerView1.setAdapter(postadapter);


        sliderView = view.findViewById(R.id.imageSlider);


        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();


        loadPost();

    }


    public void loadPost() {
        loadingbar = new ProgressDialog(getContext());
        loadingbar.setTitle("Fetching new post...");
        loadingbar.show();
        db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dslist = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds : dslist) {
                            items post = ds.toObject(items.class);
                            postadapter.add(post);
                            loadingbar.dismiss();
                        }
                    }
                });
    }



    private void search(String text) {
        ArrayList<items> filter = new ArrayList<>();
        for (items item : posted) {
            if (item.getItemtag().toLowerCase().contains(text.toLowerCase())) {
                filter.add(item);
            }
        }
        if (filter.isEmpty()) {

        } else {
            postadapter.filterData(filter);
        }
    }

    private void listeners() {

        f.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                return true;
            }
        });

    }
}