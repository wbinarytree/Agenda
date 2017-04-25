/*
 * Copyright 2017 WBinaryTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phoenix.soft.costy.account;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.phoenix.soft.costy.R;
import com.phoenix.soft.costy.models.Account;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.image_over_lay) ImageView imageToolBar;
    @BindView(R.id.appbar) AppBarLayout appbar;
    @BindView(R.id.coll_layout) CollapsingToolbarLayout coll;
    @BindView(R.id.container_pager) ViewPager viewPager;
    @BindView(R.id.tab_bar) TabLayout tabLayout;
    @BindView(R.id.container_error) LinearLayout errorLayout;
    @BindView(R.id.container_no_account) LinearLayout noAccountLayout;
    @BindView(R.id.container_detail) FrameLayout detailContainer;
    @BindView(R.id.drawer_layout)

    DrawerLayout drawerLayout;
    private int count = 0;
    private AccountPagerAdapter adapter;
    private List<Account> accountList;
    private CompositeDisposable disposable = new CompositeDisposable();
    private boolean isExpand;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDrawer();
        //        disposable.add(accoutViewModel.getAccountList()
        //                                      .subscribe(this::showAccountList, this::showError));

    }

    private void showError(Throwable error) {

    }

    private void showAccountList(List<Account> accountList) {

    }

    private void initDrawer() {
        setSupportActionBar(toolbar);
        appbar.addOnOffsetChangedListener(
            (appBarLayout, verticalOffset) -> isExpand = verticalOffset == 0);
        ActionBarDrawerToggle toggle =
            new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setPageMargin(20);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

        } else if (id == R.id.action_icon) {

        } else if (id == R.id.action_plus) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}



