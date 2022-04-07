package com.example.myapplicationcar.UI.SERVICE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplicationcar.R;

public class InforFragment extends Fragment {

    private Button btnNext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InforFragment() {
        // Required empty public constructor
    }


    public static InforFragment newInstance(String param1, String param2) {
        InforFragment fragment = new InforFragment();
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
        return inflater.inflate(R.layout.fragment_infor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = view.findViewById(R.id.sv_btn_next_info);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.sv_fragmentContainer, new TimeServiceFragment()).commit();
                ((ServiceScreen) getActivity()).setDoneStep2();
            }
        });
    }
}