package com.example.eductionery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;


public class createPost extends Fragment {


    private static final String TAG = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FrameLayout fr;
    FirebaseAuth auth;
    String CategoryList[];
    private RadioGroup rg;
    private Uri uri;
    String uridownloaded ,Itemkey,saveCurrentDate, saveCurrentTime,catergory,itemTag ,itemName,item_Desc,price;
    String itemSate ="notApprove";
    ProgressDialog loadingbar;
    FirebaseStorage storage;
    String selected;
    StorageReference storageReference;
    private RadioButton radioButton;
    LinearLayout showCategory;
    Button buttonUpload;
    ImageView backButton,itemPhoto;
    TextView selectedCategory,selectPhoto;
    EditText itenmName , itemDescription , itemPrice;
    FrameLayout frame ;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_post, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        buttonUpload = (Button) view.findViewById(R.id.upload);
        itemPrice = (EditText) view.findViewById(R.id.itemPrice);
        backButton =(ImageView)  view.findViewById(R.id.back);
        itemPhoto = (ImageView) view.findViewById(R.id.itemPhoto);
        Itemkey= UUID.randomUUID().toString();
        fr = (FrameLayout) view.findViewById(R.id.CreateposT);
        showCategory = (LinearLayout) view.findViewById(R.id.CategoryShow) ;
        selectedCategory = (TextView) view.findViewById(R.id.selectedCategory);
        itenmName = (EditText) view.findViewById(R.id.itemname);
        itemDescription = (EditText) view.findViewById(R.id.itemDescription);



        itemPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                opeFileDialog();
            }
        });



        showCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new ExtendedCategory();
                FragmentTransaction fragmentTransaction =  getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.frameContainer, fragment);
                fragmentTransaction.commit();

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                uploadImage();
            }
        });
    }



    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public void onStart() {
        super.onStart();

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();;
    }

    ActivityResultLauncher<Intent> sActActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        uri = data.getData();
                        itemPhoto.setImageURI(uri);


                    }

                }


            });

    public void opeFileDialog(){

        Intent  data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("*/*");
        data = Intent.createChooser(data,"choose image");
        sActActivityResultLauncher.launch(data);
    }

    private void uploadImage() {

        loadingbar = new ProgressDialog(getContext());
        loadingbar.setTitle("Uploading File Please Wait...");
        loadingbar.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("itemimages/"+fileName);

        final UploadTask uploadTask = storageReference.putFile(uri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(getContext(), "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        uridownloaded = storageReference.getDownloadUrl().toString();
                        return storageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            uridownloaded = task.getResult().toString();

                            Toast.makeText(getContext(), "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }




    public  boolean isApproved(String state){

        if(state.equals("Approved")){
            return true;
        }else if(state.equals("notApprove")){
            return  false;
        }
        return false;
    }

    private void SaveProductInfoToDatabase() {

        itemName = itenmName.getText().toString();
        item_Desc= itemDescription.getText().toString();
        price= itemPrice.getText().toString();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        auth  = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String CurrentUser = user.getUid();

        if(CurrentUser ==null){

            Toast.makeText(getContext(), "User must sign in", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Map<String, Object> products = new HashMap<>();
            products.put("imgurl", uridownloaded);
            products.put("itemtag", itemTag);
            products.put("itemcategories",catergory);
            products.put("itemdesc", item_Desc);
            products.put("itemname", itemName);
            products.put("uploadedate", saveCurrentDate);
            products.put("uploadedtime", saveCurrentTime);

            db.collection("Products").document(CurrentUser).collection("POSTS").document()
                    .set(products)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            loadingbar.dismiss();
                            Toast.makeText(getContext(), "Post Submitted to be check first", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "  Failed to upload  item", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }


}