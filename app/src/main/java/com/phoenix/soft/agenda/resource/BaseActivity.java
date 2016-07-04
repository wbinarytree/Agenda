package com.phoenix.soft.agenda.resource;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.phoenix.soft.agenda.R;

/**
 * Created by phoenix on 16/6/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private FrameLayout contentView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
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

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
            if ( R.layout.activity_base == layoutResID) {
                super.setContentView(R.layout.activity_base);
                contentView = (FrameLayout) findViewById(R.id.activity_content);
                contentView.removeAllViews();

            } else if (layoutResID != R.layout.activity_base) {
                View addView = LayoutInflater.from(this).inflate(layoutResID, null);
                contentView.addView(addView);
            }
    }
}
