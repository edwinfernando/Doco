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

public class DriverComeFragment extends Fragment {

    ImageView driver_come_image;

    public DriverComeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_come, container, false);

        driver_come_image = (ImageView) view.findViewById(R.id.driver_come_image);
        driver_come_image.setImageDrawable(Utilities.roundedBitmapDrawable(getContext(),R.drawable.profile,
                (int) (DeviceDimensionsHelper.getDisplayWidth(view.getContext()) * getResources().getDimension(R.dimen.size_photo_item_driver_available))));

        new SwipeDetector(view).setOnSwipeListener(new SwipeDetector.onSwipeEvent() {
            @Override
            public void SwipeEventDetected(View v, SwipeDetector.SwipeTypeEnum swipeType) {
                if(swipeType==SwipeDetector.SwipeTypeEnum.RIGHT_TO_LEFT)
                    getActivity().onBackPressed();
            }
        });
        return view;
    }

}
