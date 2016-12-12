package com.domicilio.confiable.doco.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.domicilio.confiable.doco.R;

public class FreeDocosActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_share_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_docos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(this);

        btn_share_code = (Button) findViewById(R.id.btn_share_code);
        btn_share_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_share_code){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Code XXXXXXxxxx https://play.google.com/store/apps/details?id=com.fancyapp.qrcode.barcode.scanner.reader&hl=es_419");
            startActivity(Intent.createChooser(intent, getString(R.string.share)));
        }else {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
