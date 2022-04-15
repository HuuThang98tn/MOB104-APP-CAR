package com.example.myapplicationcar.UI.SERVICE;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationcar.MODEL.Service;
import com.example.myapplicationcar.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeServiceFragment extends Fragment {
    private Button btnNext;
    private TextInputEditText edNgay;
    private Service service;
    private TextView timeSV, timeSum;
    private String bien_xe, ten_xe, hang, ngay;

    public TimeServiceFragment() {

    }


    public static TimeServiceFragment newInstance(String param1, String param2) {
        TimeServiceFragment fragment = new TimeServiceFragment();
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
        if (arguments != null){
            service = (Service) arguments.getSerializable("service");
            bien_xe= arguments.getString("bien_xe");
            ten_xe= arguments.getString("ten_xe");
            hang= arguments.getString("hang");
        }
        return inflater.inflate(R.layout.fragment_time_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = view.findViewById(R.id.sv_btn_next_time);
        edNgay = view.findViewById(R.id.sv_edNgay);
        timeSV = view.findViewById(R.id.time_sub);
        timeSum = view.findViewById(R.id.time_sum);

        edNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDate();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edNgay.getText().length() == 0){
                    Toast.makeText(getActivity(), "Bạn cần nhập ngày", Toast.LENGTH_SHORT).show();
                }else {
                    Fragment fragment = new MoneyServiceFragment();
                    if (service != null){
                        Bundle arguments = new Bundle();
                        arguments.putString("ten_xe", ten_xe);
                        arguments.putString("hang", hang);
                        arguments.putString("bien_xe", bien_xe);
                        arguments.putString("ngay", edNgay.getText().toString());
                        arguments.putSerializable("service", service);
                        fragment.setArguments(arguments);
                    }


                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.sv_fragmentContainer, fragment).commit();
                    ((ServiceScreen) getActivity()).setDoneStep3();

                }
            }
        });

        if (service != null){
            timeSV.setText(service.getTime()+"p");
            timeSum.setText(convertTime(Integer.parseInt(service.getTime())));
        }

    }

    private void dialogDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                edNgay.setText(dateFormat.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    String convertTime(int timeSP){
        int minutes = timeSP + 15;

        if (minutes > 60){
            int hour = minutes/60;
            minutes=minutes-hour*60;
            String m = Integer.toString(minutes);
            String h = Integer.toString(hour);
            String newTime=h+"h:"+m+"p";
            return newTime;
        }else {
            String newTime= minutes+"p";
            return newTime;
        }
    }
}