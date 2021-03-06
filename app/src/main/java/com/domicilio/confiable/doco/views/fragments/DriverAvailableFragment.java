package com.domicilio.confiable.doco.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.adapter.DriverAdapter;
import com.domicilio.confiable.doco.domain.LDrivers;
import com.domicilio.confiable.doco.model.Driver;
import com.domicilio.confiable.doco.presenters.fragments.DriverAvailablePresenter;
import com.domicilio.confiable.doco.presenters.fragments.IDriverAvailablePresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverAvailableFragment extends Fragment implements IDriverAvailableView {

    private IDriverAvailablePresenter driverAvailablePresenter;
    private List<Driver> driver_list;
    RecyclerView list_driver_available;

    private LinearLayoutManager linear_layout_manager;

    public DriverAvailableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_drives, container, false);

        linear_layout_manager = new LinearLayoutManager(getContext());
        LDrivers lDrivers = new LDrivers();

        list_driver_available = (RecyclerView) view.findViewById(R.id.list_drivers_avaible);

        if (lDrivers.getListDrivers().size()<1)
            lDrivers.addDriver("12344","Juan Cabuyales","3147452427",20);

        driver_list = lDrivers.getListDrivers();
        driverAvailablePresenter = new DriverAvailablePresenter(this, getActivity());

        DriverAdapter adapter = new DriverAdapter(getContext(), driver_list, driverAvailablePresenter);
        list_driver_available.setAdapter(adapter);
        list_driver_available.setLayoutManager(linear_layout_manager);

        return view;
    }

    @Override
    public void gotoDriverProfileFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.fragment_container, new DriverProfileFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void gotoDriverComeFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragment_container, new DriverComeFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
