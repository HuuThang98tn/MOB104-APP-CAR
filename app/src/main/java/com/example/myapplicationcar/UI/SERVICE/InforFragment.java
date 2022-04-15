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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplicationcar.MODEL.Service;
import com.example.myapplicationcar.R;
import com.google.android.material.textfield.TextInputEditText;

public class InforFragment extends Fragment {
    private Spinner mSpinner;
    private String hangXe;
    private TextInputEditText edName, edBien;
    private Service service;

    private Button btnNext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InforFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null){
            service = (Service) arguments.getSerializable("service");
            Log.d("Asss", service.toString());
        }
        return inflater.inflate(R.layout.fragment_infor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSpinner = view.findViewById(R.id.sv_selectCar);
        edBien = view.findViewById(R.id.sv_edBienXe);
        edName = view.findViewById(R.id.sv_edTenXe);

        String[] typeCar ={"Toyota","Chevrolet","Ford","Honda","Hyundai","Isuzu","Suzuki", "Kia", "Mitsubishi","Lexus","Mazda","Nissan","Subaru","Ssangyong","Land Rover"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeCar);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hangXe = typeCar[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnNext = view.findViewById(R.id.sv_btn_next_info);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edName.getText().length() == 0 || edBien.getText().length()==0 ){
                    Toast.makeText(getActivity(), "Bạn cần nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
                }else{
                    Fragment fragment = new TimeServiceFragment();
                    if (service != null){
                        Bundle arguments = new Bundle();
                        arguments.putString("ten_xe", edName.getText().toString());
                        arguments.putString("hang", hangXe);
                        arguments.putString("bien_xe", edBien.getText().toString());
                        arguments.putSerializable("service", service);
                        fragment.setArguments(arguments);
                    }

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.sv_fragmentContainer, fragment).commit();
                    ((ServiceScreen) getActivity()).setDoneStep2();
                }
            }
        });

    }
}