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

    private List<Driver> driver_list;
    RecyclerView list_driver_available;

    private LinearLayoutManager linear_layout_manager;

    public ListDriverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_drives, container, false);
        // Inflate the layout for this fragment
        linear_layout_manager = new LinearLayoutManager(getContext());
        LDrivers lDrivers = new LDrivers();
        list_driver_available = (RecyclerView) view.findViewById(R.id.list_drivers_avaible);

       // lDrivers.addDriver("12344","Edwin Fernando Muñoz","3137649407",70);
        driver_list = lDrivers.getListDrivers();

        DriverAdapter adapter = new DriverAdapter(getContext(), driver_list);
        list_driver_available.setAdapter(adapter);
        list_driver_available.setLayoutManager(linear_layout_manager);

        return view;
    }

}
