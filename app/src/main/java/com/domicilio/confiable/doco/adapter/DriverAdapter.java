package com.domicilio.confiable.doco.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.model.Driver;
import com.domicilio.confiable.doco.presenters.fragments.IDriverAvailablePresenter;
import com.domicilio.confiable.doco.util.DeviceDimensionsHelper;
import com.domicilio.confiable.doco.util.Utilities;

import java.util.List;

/**
 * Created by edwin on 16/11/2016.
 */

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverHolder> implements View.OnClickListener {

    private IDriverAvailablePresenter driverAvailablePresenter;

    private List<Driver> dataSource;
    private Context context;

    public DriverAdapter(Context context, List<Driver> dataSource, IDriverAvailablePresenter driverAvaiblePresenter) {
        this.context = context;
        this.dataSource = dataSource;
        this.driverAvailablePresenter = driverAvaiblePresenter;
    }

    @Override
    public DriverAdapter.DriverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_driver_available, parent, false);
        DriverHolder driverHolder = new DriverHolder(view);
        return driverHolder;
    }

    @Override
    public void onBindViewHolder(DriverAdapter.DriverHolder holder, int position) {
        Driver driver = dataSource.get(position);
        // holder.conductor_profile_image.setImageDrawable(driver.getImage_profile());
        holder.name_driver.setText(driver.getName());
        holder.fab_driver_profile.setOnClickListener(this);

        holder.view.setOnClickListener(this);
        holder. driver_available_image.setImageDrawable(Utilities.roundedBitmapDrawable(context,R.drawable.profile,
                (int) (DeviceDimensionsHelper.getDisplayWidth(context) * context.getResources().getDimension(R.dimen.size_photo_item_driver_available))));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_driver_profile:
                driverAvailablePresenter.gotoDriverProfileFragment();
                break;
            default:
                driverAvailablePresenter.gotoDriverComeFragment();
                break;
        }
    }

    public static class DriverHolder extends RecyclerView.ViewHolder {
     //   CircleImageView conductor_profile_image;
        TextView name_driver;
        View view;

        ImageView driver_available_image;
        ImageButton fab_driver_profile;

        public DriverHolder(View itemView) {
            super(itemView);

            this.view = itemView;
         //   conductor_profile_image = (CircleImageView) itemView.findViewById(R.id.conductor_profile_image);
            driver_available_image = (ImageView) itemView.findViewById(R.id.driver_available_image);
            name_driver = (TextView) itemView.findViewById(R.id.name_driver);

            fab_driver_profile = (ImageButton) itemView.findViewById(R.id.fab_driver_profile);
        }
    }
}
