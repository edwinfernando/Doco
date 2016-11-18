package com.domicilio.confiable.doco.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class CheckPermissionActivityManager extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static CheckingPermissionListener checkingPermissionListener;

    public interface CheckingPermissionListener {
        void onResult(boolean isGranted);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String permission = getIntent().getStringExtra("permission");

        // Should we show an explanation?
        //if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
        // Show an expanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
        //} else {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_CODE);
        //}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try {
            CheckPermissionActivityManager.checkingPermissionListener.onResult(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        } finally {
            CheckPermissionActivityManager.checkingPermissionListener = null;
        }
    }

    public static boolean checkPermission(Context context, String permission,
                                          CheckingPermissionListener checkingPermissionListener) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if(checkingPermissionListener != null) {
                CheckPermissionActivityManager.checkingPermissionListener = checkingPermissionListener;
                Intent intent = new Intent(context, CheckPermissionActivityManager.class);
                intent.putExtra("permission", permission);
                context.startActivity(intent);
            }

            return false;
        } else {
            return true;
        }
    }
}
