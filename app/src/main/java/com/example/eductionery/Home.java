package com.example.eductionery;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.eductionery.Adapters.CategoriesAdapter;
import com.example.eductionery.Adapters.SliderAdapter;
import com.example.eductionery.Adapters.postAdapter;
import com.example.eductionery.Model.Categories;
import com.example.eductionery.Model.items;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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
    List<Categories> Items = new ArrayList<>();
    prof p = new prof();
    int spanCount = 2; // 3 columns
    int spacing = 20; // 50px
    boolean includeEdge = true;
    RecyclerView recyclerView1;
    private  FragmentContainer container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home2, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        db = FirebaseFirestore.getInstance();

        Query query = db.collection("Products");

         recyclerView1 =  view.findViewById(R.id.postItem);



        recyclerView1.setLayoutManager(new GridLayoutManager(getContext(), spanCount));

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();


        gsc = GoogleSignIn.getClient(getContext(),gso);

        FirestoreRecyclerOptions<items> posts = new FirestoreRecyclerOptions.Builder<items>()
                .setQuery(query, items.class)
                .build();

        postadapter = new postAdapter(posts);
        recyclerView1.addItemDecoration((new Decoration(spanCount, spacing, includeEdge)));
        recyclerView1.setAdapter(postadapter);

        postadapter.setOnItemClickListener(new postAdapter.OnItemClickListener() {
            @Override

            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                items post = documentSnapshot.toObject(items.class);
                String id = documentSnapshot.getId().toString();

                String name = post.getItemname();
                String url = post.getImgurl().toString();
                Bundle b= new Bundle();
                String price =post.getPrice();
                String quant = post.getQuant();


                if(id != null){
                    Log.d("Test",post.getItemname());
                    Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "id: null", Toast.LENGTH_SHORT).show();
                }
                b.putString("url",url);
                b.putString("id",id);
                b.putString("name",name);
                b.putString("price",price);

                    Fragment fragment = new ItemDetails();

                    FragmentManager fragmentManager =  getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                    fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.frameContainer, fragment);
                fragmentTransaction.commit();


            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        postadapter.startListening();
        postadapter.notifyDataSetChanged();
        recyclerView1.getRecycledViewPool().clear();


    }


    @Override
    public void onStop() {
        super.onStop();
        postadapter.stopListening();

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