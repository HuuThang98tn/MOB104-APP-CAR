package com.example.myapplicationcar.UI.SERVICE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapplicationcar.ADAPTER.SpinnerAdapterService;
import com.example.myapplicationcar.MODEL.Service;
import com.example.myapplicationcar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceFragment extends Fragment {
    private Spinner spService;
    private Switch sBaoDuong, sSuaChua, sRuaXe;
    private SpinnerAdapterService spinnerAdapterService;
    private FirebaseFirestore db;
    private List<Service> listService;
    private List<Service> listSpinner, listBd, listSc, listRx;
    private Button btnNext;
    private FragmentManager fg;
    private TextView tvSelected;
    private Service serviceSelected;


    public ServiceFragment() {

    }

    public static ServiceFragment newInstance(String param1, String param2) {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spService = view.findViewById(R.id.sv_spinner);
        sBaoDuong = view.findViewById(R.id.sv_switchBd);
        sSuaChua = view.findViewById(R.id.sv_switchSc);
        sRuaXe = view.findViewById(R.id.sv_switchRx);
        btnNext = view.findViewById(R.id.sv_btn_next);
        tvSelected = view.findViewById(R.id.sv_tvSelected);

        fg = getActivity().getSupportFragmentManager();
        db = FirebaseFirestore.getInstance();
        listService = new ArrayList<>();
        listSpinner = new ArrayList<>();
        listBd = new ArrayList<>();
        listSc = new ArrayList<>();
        listRx = new ArrayList<>();

        sBaoDuong.setChecked(true);

        getDataBd();

        getDataAll();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InforFragment fragment = new InforFragment();
                if (serviceSelected != null){
                    Bundle arguments = new Bundle();
                    arguments.putSerializable("service", serviceSelected);
                    fragment.setArguments(arguments);
                }

                fg.beginTransaction()
                        .replace(R.id.sv_fragmentContainer, fragment)
                        .commit();
                ((ServiceScreen) getActivity()).setDoneStep1();

            }
        });

        eventSwitch(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    void eventSwitch(View view) {
        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                switch (compoundButton.getId()) {
                    case R.id.sv_switchBd:
                        if (isChecked) {
                            listService.clear();
                            listService.addAll(listBd);
                            spinnerAdapterService.notifyDataSetChanged();

                            sRuaXe.setChecked(false);
                            sSuaChua.setChecked(false);
                        }

                        break;
                    case R.id.sv_switchRx:
                        if (isChecked) {
                            listService.clear();
                            listService.addAll(listRx);
                            spinnerAdapterService.notifyDataSetChanged();

                            sBaoDuong.setChecked(false);
                            sSuaChua.setChecked(false);
                        }

                        break;
                    case R.id.sv_switchSc:
                        if (isChecked) {
                            listService.clear();
                            listService.addAll(listSc);
                            spinnerAdapterService.notifyDataSetChanged();

                            sRuaXe.setChecked(false);
                            sBaoDuong.setChecked(false);
                        }

                        break;
                    default:
                        sBaoDuong.setChecked(true);
                        break;
                }
            }
        };

        ((Switch) view.findViewById(R.id.sv_switchBd)).setOnCheckedChangeListener(multiListener);
        ((Switch) view.findViewById(R.id.sv_switchSc)).setOnCheckedChangeListener(multiListener);
        ((Switch) view.findViewById(R.id.sv_switchRx)).setOnCheckedChangeListener(multiListener);
    }

    public void getDataBd() {

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

                            spinnerAdapterService = new SpinnerAdapterService(getActivity(), listService);
                            spinnerAdapterService.notifyDataSetChanged();
                            spService.setAdapter(spinnerAdapterService);

                            spService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    serviceSelected = listService.get(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                        } else {
                            Log.d("errrrr", "Error getting documents: ", task.getException());
                        }
                    }
                });
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

                                if (service.getType().equals("sua_chua")){
                                    listSc.add(service);
                                }
                                if (service.getType().equals("bao_duong_dinh_ky")){
                                    listBd.add(service);
                                }
                                if (service.getType().equals("rua_xe")){
                                    listRx.add(service);
                                }
                            }
                            Collections.reverse(listBd);
                            Collections.reverse(listSc);
                            Collections.reverse(listRx);
                        } else {
                            Log.d("errrrr", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}