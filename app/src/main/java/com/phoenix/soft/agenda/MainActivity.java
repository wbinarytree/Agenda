package com.phoenix.soft.agenda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v4.view.RxViewPager;
import com.jakewharton.rxbinding2.view.RxView;
import com.phoenix.soft.agenda.account.AccountListFragment;
import com.phoenix.soft.agenda.account.AccountPagerAdapter;
import com.phoenix.soft.agenda.detail.DetailFragment;
import com.phoenix.soft.agenda.hidden.HiddenActivity;
import com.phoenix.soft.agenda.module.Events;
import com.phoenix.soft.agenda.repos.TestAccountRepository;
import com.phoenix.soft.agenda.rxbus.RxBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
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
    @BindView(R2.id.container_pager)
    ViewPager viewPager;
    @BindView(R2.id.tab_bar)
    TabLayout tabLayout;
    private int count = 0;
    private AccountPagerAdapter adapter;
    private ViewPager.OnPageChangeListener pageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        adapter = new AccountPagerAdapter(getSupportFragmentManager(), new TestAccountRepository(this));
        viewPager.setAdapter(adapter);

        RxViewPager.pageSelections(viewPager).subscribe(position -> adapter.getFragment(position).upDateActivity());


        RxBus.getInstance().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (o instanceof Events.ToolbarChangeEvent) {
                        Log.d(TAG, "onCreate: message Receive");
                    }
                });
//        subscribe = RxView.clicks(fab)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> adapter.getFragment(viewPager.getCurrentItem()).FabClick());

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
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

        } else if (id == R.id.action_icon) {
            count++;
            if (count == 15) {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.coordinator),
                                Utils.fromHtml("<font color=\"#ffffff\">Activted Hidden Mode</font>"),
                                Snackbar.LENGTH_SHORT);
                snackbar.show();
                count = 0;
                Intent intent = new Intent(this, HiddenActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.action_plus) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //need to dispose here. Otherwise the observable will running forever.
    }
}



