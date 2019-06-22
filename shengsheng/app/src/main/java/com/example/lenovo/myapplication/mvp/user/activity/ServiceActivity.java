package com.example.lenovo.myapplication.mvp.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.lenovo.myapplication.R;

/**
 * Created by dingin on 2018/5/23.
 */

public class ServiceActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_service);
        final Intent intent = getIntent();
        final int position = intent.getIntExtra("position",-1);


        ImageView imageView = findViewById(R.id.img_service);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phone_intent = new Intent();
                phone_intent.setAction(Intent.ACTION_DIAL);
                phone_intent.setData(Uri.parse("tel:15930418648"));
                startActivity(phone_intent);
            }
        });

        ImageView ser_back = findViewById(R.id.service_back);
        ser_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ServiceActivity.this.finish();

            }
        });

    }




}
