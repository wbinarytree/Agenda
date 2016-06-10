package com.phoenix.soft.agenda.resource;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.phoenix.soft.agenda.R;

/**
 * Created by phoenix on 16/6/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolBar = (Toolbar) findViewById(R.id.toolbar);

        if (toolBar != null) {
            setSupportActionBar(toolBar);
            if (getSupportActionBar() != null) {
            // // TODO: 16/6/10  
            }
        }
        toolBarConfigure();
    }

    protected  void toolBarConfigure(){

    }

    /*
        use in the activities
        Example :   @Override
                      public int getLayoutResource() {
                        return R.layout.activity_main;
                      }
    * */
    protected abstract int getLayoutResource();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




}
