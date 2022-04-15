package com.example.myapplicationcar.UI.HISTORY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplicationcar.ADAPTER.HistoryAdapter;
import com.example.myapplicationcar.ADAPTER.ItemHistory;
import com.example.myapplicationcar.MODEL.Oder;
import com.example.myapplicationcar.MODEL.Service;
import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.ACCOUNT.SettingScreen;
import com.example.myapplicationcar.UI.HOME.HomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryScreen extends AppCompatActivity {
    private Toolbar htToolbar;
    private BottomNavigationView htBoottom;
    private LinearLayout loadData, dataOn;
    private List<Oder> listOder;
    private List<Service> listService;
    private HistoryAdapter adapter;
    private RecyclerView hsRV;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_screen);

        initUI();

        setUp();
        getDataAll();
    }

    void setUp() {
        //Setup FireBase
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

        //Toolbar setup
        setSupportActionBar(htToolbar);
        getSupportActionBar().setTitle("Lịch sử");

        //Set bottoom tab
        htBoottom.setSelectedItemId(R.id.action_history);
        htBoottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(HistoryScreen.this, HomeScreen.class));
                        finish();
                        return true;
                    case R.id.action_history:
                        return true;
                    case R.id.action_account:
                        startActivity(new Intent(HistoryScreen.this, SettingScreen.class));
                        finish();
                        return true;
                }
                return false;
            }
        });
        //Recycle view
        listOder = new ArrayList<>();
        listService = new ArrayList<>();

        adapter = new HistoryAdapter(listOder, this, listService, new ItemHistory() {
            @Override
            public void clickItem(Oder oder) {
                Intent intent = new Intent(HistoryScreen.this, DetailHistory.class);
                intent.putExtra("oder", oder);
                for (Service item : listService){
                    if (item.getId().equals(oder.getService_id())) {
                        intent.putExtra("service", item);
                    }
                }
                startActivity(intent);
            }
        });

        hsRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        hsRV.setAdapter(adapter);

    }


    void initUI() {
        htToolbar = findViewById(R.id.history_toolbar);
        htBoottom = findViewById(R.id.history_bottomTab);
        loadData = findViewById(R.id.loadData);
        dataOn = findViewById(R.id.dataSucces);
        hsRV = findViewById(R.id.hs_reyclerview);
    }

    public void getDataAll() {
        db.collection("service")
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
                        } else {
                            Log.d("errrrr", "Error getting documents: ", task.getException());
                        }
                    }
                });
        db.collection("oder")
                .whereEqualTo("user_id", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Oder oder = new Oder();
                                oder.setUser_id(String.valueOf(document.get("user_id")));
                                oder.setService_id(String.valueOf(document.get("service_id")));
                                oder.setName_car(String.valueOf(document.get("name_car")));
                                oder.setType_car(String.valueOf(document.get("type_car")));
                                oder.setNumber_car(String.valueOf(document.get("number_car")));
                                oder.setDate(String.valueOf(document.get("date")));
                                oder.setTime(String.valueOf(document.get("time")));

                                listOder.add(oder);
                            }
                            Collections.sort(listOder, (x, y) -> x.getDate().compareTo(y.getDate()));
                            loadData.setVisibility(View.GONE);
                            dataOn.setVisibility(View.VISIBLE);
                        } else {
                            Log.d("errrrr", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}