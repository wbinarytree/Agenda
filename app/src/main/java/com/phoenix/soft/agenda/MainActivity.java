package com.phoenix.soft.agenda;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.phoenix.soft.agenda.account.AccountListFragment;
import com.phoenix.soft.agenda.module.Events;
import com.phoenix.soft.agenda.rxbus.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R2.id.fab)
    FloatingActionButton fab;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.image_over_lay)
    ImageView imageToolBar;
    @BindView(R2.id.appbar)
    AppBarLayout appbar;
    @BindView(R2.id.coll_layout)
    CollapsingToolbarLayout coll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getResources().getString(R.string.title_main));
        }
        if (savedInstanceState == null) {
            Fragment fragment = new AccountListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_content, fragment, AccountListFragment.TAG)
                    .commit();
        }

        RxBus.getInstance().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (o instanceof Events.ToolbarChangeEvent) {
                        modifyToolbar(((Events.ToolbarChangeEvent) o).isVisible());
                    }
                });


    }


    private void modifyToolbar(boolean isVisible) {
        if (isVisible) {
            appbar.setFitsSystemWindows(true);
            imageToolBar.setVisibility(View.VISIBLE);
            imageToolBar.setFitsSystemWindows(true);
            coll.setFitsSystemWindows(true);
            appbar.requestApplyInsets();
        } else {
            appbar.setFitsSystemWindows(false);
            imageToolBar.setVisibility(View.GONE);
            appbar.requestApplyInsets();
        }
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }

}



