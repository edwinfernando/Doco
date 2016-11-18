package com.domicilio.confiable.doco.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.model.Driver;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by edwin on 16/11/2016.
 */

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverHolder> {

    private List<Driver> dataSource;
    private Context context;

    public DriverAdapter(Context context, List<Driver> dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }

    @Override
    public DriverAdapter.DriverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_conductor_disponible, parent, false);

        DriverHolder historialHolder = new DriverHolder(view);
        return historialHolder;
    }

    @Override
    public void onBindViewHolder(DriverAdapter.DriverHolder holder, int position) {
        Driver driver = dataSource.get(position);
        // holder.conductor_profile_image.setImageDrawable(driver.getImage_profile());
        holder.name_driver.setText(driver.getName());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public static class DriverHolder extends RecyclerView.ViewHolder {

        CircleImageView conductor_profile_image;
        TextView name_driver;

        public DriverHolder(View itemView) {
            super(itemView);

            conductor_profile_image = (CircleImageView) itemView.findViewById(R.id.conductor_profile_image);
            name_driver = (TextView) itemView.findViewById(R.id.name_driver);
        }
    }
}
