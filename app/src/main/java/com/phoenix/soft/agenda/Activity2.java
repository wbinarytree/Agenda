package com.phoenix.soft.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.phoenix.soft.agenda.resource.BaseActivity;

/**
 * Created by phoenix on 16/6/5.
 */

public class Activity2 extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_2);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.phoenix.soft.agenda.TOAST");
                sendBroadcast(intent);
            }
        });


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_2;
    }
}
