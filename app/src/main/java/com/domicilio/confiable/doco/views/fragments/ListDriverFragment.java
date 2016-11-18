package com.domicilio.confiable.doco.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.adapter.DriverAdapter;
import com.domicilio.confiable.doco.domain.LDrivers;
import com.domicilio.confiable.doco.model.Driver;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListDriverFragment extends Fragment {

    private List<Driver> driverList;
    RecyclerView listDriverAvailable;

    private LinearLayoutManager linearLayoutManager;

    public ListDriverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_drives, container, false);
        // Inflate the layout for this fragment
        linearLayoutManager = new LinearLayoutManager(getContext());
        LDrivers lDrivers = new LDrivers();

        lDrivers.addDriver("12344","Edwin Fernando Muñoz","3137649407",70);
        driverList = lDrivers.getListDrivers();

        DriverAdapter adapter = new DriverAdapter(getContext(), driverList);
        listDriverAvailable.setAdapter(adapter);
        listDriverAvailable.setLayoutManager(linearLayoutManager);

        return view;
    }

}
