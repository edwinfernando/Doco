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
import com.domicilio.confiable.doco.presenters.fragments.IDriverAvaiblePresenter;
import com.domicilio.confiable.doco.util.DeviceDimensionsHelper;
import com.domicilio.confiable.doco.util.Utilities;

import java.util.List;

/**
 * Created by edwin on 16/11/2016.
 */

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverHolder> implements View.OnClickListener {

    private IDriverAvaiblePresenter driverAvaiblePresenter;

    private List<Driver> dataSource;
    private Context context;

    public DriverAdapter(Context context, List<Driver> dataSource, IDriverAvaiblePresenter driverAvaiblePresenter) {
        this.context = context;
        this.dataSource = dataSource;
        this.driverAvaiblePresenter = driverAvaiblePresenter;
    }

    @Override
    public DriverAdapter.DriverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_driver_avaible, parent, false);
        DriverHolder historialHolder = new DriverHolder(view);
        return historialHolder;
    }

    @Override
    public void onBindViewHolder(DriverAdapter.DriverHolder holder, int position) {
        Driver driver = dataSource.get(position);
        // holder.conductor_profile_image.setImageDrawable(driver.getImage_profile());
        holder.name_driver.setText(driver.getName());
        holder.fab_driver_profile.setOnClickListener(this);

        holder.view.setOnClickListener(this);
        holder. driver_avaible_image.setImageDrawable(Utilities.roundedBitmapDrawable(context,R.drawable.profile,
                (int) (DeviceDimensionsHelper.getDisplayWidth(context) * context.getResources().getDimension(R.dimen.size_photo_item_driver_avaible))));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_driver_profile:
                driverAvaiblePresenter.gotoDriverProfileFragment();
                break;
            default:
                driverAvaiblePresenter.gotoDriverComeFragment();
                break;
        }
    }

    public static class DriverHolder extends RecyclerView.ViewHolder {
     //   CircleImageView conductor_profile_image;
        TextView name_driver;
        View view;

        ImageView driver_avaible_image;
        ImageButton fab_driver_profile;

        public DriverHolder(View itemView) {
            super(itemView);

            this.view = itemView;
         //   conductor_profile_image = (CircleImageView) itemView.findViewById(R.id.conductor_profile_image);
            driver_avaible_image = (ImageView) itemView.findViewById(R.id.driver_avaible_image);
            name_driver = (TextView) itemView.findViewById(R.id.name_driver);

            fab_driver_profile = (ImageButton) itemView.findViewById(R.id.fab_driver_profile);
        }
    }
}
