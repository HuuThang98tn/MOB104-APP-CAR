package com.example.myapplicationcar.UI.SERVICE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationcar.MODEL.Service;
import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.HISTORY.HistoryScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MoneyServiceFragment extends Fragment {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private Button btnDone;
    private Service service;
    private String bien_xe, ten_xe, hang, ngay, uid;
    private TextView tvSV, tvSum;
    private ArrayList<Integer> listPrice;

    public MoneyServiceFragment() {

    }


    public static MoneyServiceFragment newInstance(String param1, String param2) {
        MoneyServiceFragment fragment = new MoneyServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            service = (Service) arguments.getSerializable("service");
            bien_xe = arguments.getString("bien_xe");
            ten_xe = arguments.getString("ten_xe");
            hang = arguments.getString("hang");
            ngay = arguments.getString("ngay");
        }
        return inflater.inflate(R.layout.fragment_money_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDone = view.findViewById(R.id.sv_btn_done);
        tvSV = view.findViewById(R.id.tv_monneyService);
        tvSum = view.findViewById(R.id.tv_monneySum);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user!= null){
            uid = user.getUid();
        }

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        if (service != null) {
            listPrice = service.getPrice();
            int tb = 0;
            tb =   (Integer.parseInt(String.valueOf(listPrice.get(0))) + Integer.parseInt(String.valueOf(listPrice.get(1)))) / 2;

            tvSV.setText(formatter.format(tb) + "đ");
            tvSum.setText(formatter.format(tb + 100000) + "đ");
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (service != null && uid != null){
                    addOder();
                }
            }
        });
    }

    private void addOder(){
        Map<String, Object> oder = new HashMap<>();
        oder.put("user_id", uid);
        oder.put("service_id", service.getId());
        oder.put("name_car", ten_xe);
        oder.put("type_car", hang);
        oder.put("number_car", bien_xe);
        oder.put("date", ngay);
        oder.put("time", (Integer.parseInt(service.getTime())+15));

        db.collection("oder")
                .add(oder)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Đặt dịch vụ thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), HistoryScreen.class));
                        getActivity().finish();
                        Log.d("ooooiii", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Lỗi rồi dcm mayyy", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}