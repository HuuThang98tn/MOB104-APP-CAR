package com.example.myapplicationcar.UI.HOME;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplicationcar.ADAPTER.SliderAdapter;
import com.example.myapplicationcar.MODEL.Service;
import com.example.myapplicationcar.MODEL.Slider;
import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.ACCOUNT.InformationScreen;
import com.example.myapplicationcar.UI.HISTORY.HistoryScreen;
import com.example.myapplicationcar.UI.ACCOUNT.SettingScreen;
import com.example.myapplicationcar.UI.SERVICE.ServiceScreen;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    private BottomNavigationView mBottomTab;
    private Toolbar mToolbar;
    private ImageView imgAvatar, imgBanner;
    private FirebaseFirestore db;
    private DatabaseReference mDatabase;
    private List<String> listSlider;
    private ShimmerFrameLayout mShimmerFrameLayout, mShBanner;
    private SliderAdapter sliderAdapter;
    private List<Service> listService;
    private RecyclerView rvSlider;
    private LinearLayout menuService, menuHistory, menuCall, menuInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        initUI();

        setup();


    }

    private void setup() {

        //Setting firestore
        db = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("slider");
        //Set Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        //List
        listSlider = new ArrayList<>();
        listService = new ArrayList<>();

        getMultipleData();
        getDataSlider();
        getBanner();

        sliderAdapter = new SliderAdapter(listService, listSlider, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvSlider.setLayoutManager(layoutManager);
        rvSlider.setAdapter(sliderAdapter);

        //Set bottoom tab
        mBottomTab.setSelectedItemId(R.id.action_home);
        mBottomTab.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        return true;
                    case R.id.action_history:
                        startActivity(new Intent(HomeScreen.this, HistoryScreen.class));
                        finish();
                        return true;
                    case R.id.action_account:
                        startActivity(new Intent(HomeScreen.this, SettingScreen.class));
                        finish();
                        return true;
                }
                return false;
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, SettingScreen.class));
            }
        });

        menuInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, InformationScreen.class));
            }
        });

        menuCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mNumberPhone = "02466618648";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mNumberPhone)));
            }
        });
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, HistoryScreen.class));
            }
        });
        menuService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, ServiceScreen.class));
            }
        });
    }

    private void getDataSlider() {
        listSlider.add(0,"https://firebasestorage.googleapis.com/v0/b/mob104-auto-care.appspot.com/o/Slider%2Fslider_1.jpg?alt=media&token=f9d94e16-a142-4c91-9598-8e12ba5f87e0");
        listSlider.add(1, "https://firebasestorage.googleapis.com/v0/b/mob104-auto-care.appspot.com/o/Slider%2Fslider_2.jpg?alt=media&token=1442f49d-d0df-467c-b482-b3a98a4f6305");
        listSlider.add(2, "https://firebasestorage.googleapis.com/v0/b/mob104-auto-care.appspot.com/o/Slider%2Fslider_3.jpg?alt=media&token=ed441794-b287-4fd2-9690-307179862532");
        listSlider.add(3, "https://firebasestorage.googleapis.com/v0/b/mob104-auto-care.appspot.com/o/Slider%2Fslider_4.jpg?alt=media&token=dc18f196-e46d-4896-8be4-8d1ddc633b40");
        listSlider.add(4, "https://firebasestorage.googleapis.com/v0/b/mob104-auto-care.appspot.com/o/Slider%2Fslider_5.jpg?alt=media&token=2bee7cd0-2424-472f-bbbd-4889299525d5");
    }

    private void initUI() {
        mBottomTab = findViewById(R.id.home_bottomTab);
        mToolbar = findViewById(R.id.home_toolbar);
        imgAvatar = findViewById(R.id.avatarUser);
        imgBanner = findViewById(R.id.home_banner);
        mShimmerFrameLayout = findViewById(R.id.sf_loading);
        mShBanner = findViewById(R.id.sf_banner);
        rvSlider = findViewById(R.id.rvSlider);
        menuService = findViewById(R.id.menuService);
        menuHistory = findViewById(R.id.menuHistory);
        menuCall = findViewById(R.id.menuCall);
        menuInfo = findViewById(R.id.menuInfo);

        mShimmerFrameLayout.startShimmer();
        mShBanner.startShimmer();

        showUserInformation();
    }

    private void showUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
//            String name = user.getDisplayName();
//            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            Glide.with(this).load(photoUrl).error(R.drawable.img_df_user).into(imgAvatar);
        }
    }

    public void getBanner() {
        String photoUrl = "https://firebasestorage.googleapis.com/v0/b/mob104-auto-care.appspot.com/o/Banner%2Fbanner_1.jpg?alt=media&token=f3bed712-8dba-4386-8602-45cbb9cacc8a";

        Glide.with(this).load(photoUrl).error(R.drawable.img_df_user).into(imgBanner);
    }

    public void getMultipleData() {
        db.collection("service")
                .whereEqualTo("type", "bao_duong_dinh_ky")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Service service = new Service();
                                service.setId(document.getId());
                                service.setName((String) document.get("name_sv"));
                                service.setNote((String) document.get("note"));
                                service.setType((String) document.get("type"));
                                service.setTime((String) document.get("time"));
                                service.setPrice((ArrayList<Integer>) document.get("price"));
                                listService.add(service);
                            }
                            Collections.reverse(listService);

                            mShimmerFrameLayout.stopShimmer();
                            mShimmerFrameLayout.setVisibility(View.GONE);
                            rvSlider.setVisibility(View.VISIBLE);

                            mShBanner.stopShimmer();
                            mShBanner.setVisibility(View.GONE);
                            imgBanner.setVisibility(View.VISIBLE);

                            sliderAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("errrrr", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }



//    private void addPostEventListener() {
//        ValueEventListener postListener = new ValueEventListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//                    listSlider.add(childDataSnapshot.getValue(Slider.class));
//                }
//                listSlider.sort(Comparator.comparing(Slider::getId));
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        mDatabase.addValueEventListener(postListener);
//    }

}