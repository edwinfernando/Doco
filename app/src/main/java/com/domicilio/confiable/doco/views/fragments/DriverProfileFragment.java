package com.domicilio.confiable.doco.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.util.DeviceDimensionsHelper;
import com.domicilio.confiable.doco.util.SwipeDetector;
import com.domicilio.confiable.doco.util.Utilities;


public class DriverProfileFragment extends Fragment {

    ImageView driver_profile_image;

    public DriverProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_driver_profile, container, false);

        driver_profile_image = (ImageView) view.findViewById(R.id.driver_profile_image);
        driver_profile_image.setImageDrawable(Utilities.roundedBitmapDrawable(getContext(),R.drawable.profile,
                (int) (DeviceDimensionsHelper.getDisplayWidth(view.getContext()) * getResources().getDimension(R.dimen.size_photo_profile))));

        new SwipeDetector(view).setOnSwipeListener(new SwipeDetector.onSwipeEvent() {
            @Override
            public void SwipeEventDetected(View v, SwipeDetector.SwipeTypeEnum swipeType) {
                if(swipeType==SwipeDetector.SwipeTypeEnum.LEFT_TO_RIGHT)
                    getActivity().onBackPressed();
            }
        });
        return view;
    }

}
