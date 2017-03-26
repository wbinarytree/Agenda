package com.phoenix.soft.agenda;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding2.support.v4.view.RxViewPager;
import com.jakewharton.rxbinding2.view.RxMenuItem;
import com.jakewharton.rxbinding2.view.RxView;
import com.phoenix.soft.agenda.account.AccountAddDialogFragment;
import com.phoenix.soft.agenda.account.AccountContract;
import com.phoenix.soft.agenda.account.AccountDetailFragment;
import com.phoenix.soft.agenda.account.AccountPagerAdapter;
import com.phoenix.soft.agenda.account.AccountPresenter;
import com.phoenix.soft.agenda.account.di.DaggerAccountPresenterComponent;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.RxAccountSource;
import com.phoenix.soft.agenda.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Maybe;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements AccountContract.View {
    private static final String TAG = "MainActivity";
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_over_lay)
    ImageView imageToolBar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.coll_layout)
    CollapsingToolbarLayout coll;
    @BindView(R.id.container_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_bar)
    TabLayout tabLayout;
    @BindView(R.id.container_error)
    LinearLayout errorLayout;
    @BindView(R.id.container_no_account)
    LinearLayout noAccountLayout;
    @BindView(R.id.container_detail)
    FrameLayout detailContainer;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Inject
    RxAccountSource rxAccountRepository;
    @Inject
    AccountPresenter presenter;
    private int count = 0;
    private AccountPagerAdapter adapter;
    private List<Account> accountList;
    private CompositeDisposable disposable = new CompositeDisposable();
    private boolean isExpand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerAccountPresenterComponent.builder()
                                       .firebaseComponent(MainApplication.getFirebaseComponent())
                                       .build()
                                       .inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setPageMargin(20);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.container_detail, AccountDetailFragment.newInstance(), AccountDetailFragment.TAG)
                                       .commit();
        }

        appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> isExpand = verticalOffset == 0);

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
        presenter.loadAccount();
    }

    private void updateViewByPagerContent(int position) {
        Picasso.with(this)
               .load(Integer.valueOf(accountList.get(position).getAccountPicUrl()))
               .noFade()
               .resize(400, 800)
               .into(imageToolBar);
        AccountDetailFragment fragment = (AccountDetailFragment) getSupportFragmentManager().findFragmentByTag(AccountDetailFragment.TAG);
        if (fragment != null) {
            fragment.selectChart(position);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        disposable.add(RxMenuItem.clicks(menu.findItem(R.id.action_icon))
                                 .subscribe(objects -> appbar.setExpanded(!isExpand)));
        disposable.add(RxMenuItem.clicks(menu.findItem(R.id.action_plus)).subscribe(o -> {
            AccountAddDialogFragment fragment = new AccountAddDialogFragment();
            fragment.show(getSupportFragmentManager(), AccountAddDialogFragment.TAG);
        }));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

        } else if (id == R.id.action_icon) {

        } else if (id == R.id.action_plus) {

        }
        //hide hidden mode for now
//        if (objects.size() > 20) {
//            Snackbar.make(findViewById(R.id.coordinator), Utils.fromHtml("<font color=\"#ffffff\">Activted Hidden Mode</font>"), Snackbar.LENGTH_SHORT)
//                    .show();
//            count = 0;
//            Intent intent = new Intent(this, HiddenActivity.class);
//            startActivity(intent);
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
        presenter.detachView();
    }

    public AccountContract.Presenter getPresenter() {
        return presenter;
    }

    public Maybe<List<Account>> getAccountList() {
        return Maybe.defer(() -> Maybe.just(accountList));
    }

    @Override
    public void showAccountList(List<Account> accountList) {
        this.accountList = accountList;
        adapter = new AccountPagerAdapter(getSupportFragmentManager(), this.accountList);
        viewPager.setAdapter(adapter);
        disposable.add(RxViewPager.pageSelections(viewPager).subscribe(position -> {
            if (!fab.isShown()) {
                fab.show();
            }
            updateViewByPagerContent(position);
        }));
        AccountDetailFragment fragment = (AccountDetailFragment) getSupportFragmentManager().findFragmentByTag(AccountDetailFragment.TAG);
        if (fragment != null) {
            fragment.showPieChart();
            detailContainer.setVisibility(View.VISIBLE);
            appbar.setExpanded(true, true);
        }
        disposable.add(RxView.clicks(fab)
                             .subscribe(o -> adapter.getFragment(viewPager.getCurrentItem())
                                                    .onClick()));
    }

    @Override
    public void showNoAccount() {
        appbar.setExpanded(false);
        noAccountLayout.setVisibility(View.VISIBLE);
        fab.setOnClickListener(v -> {
        });
    }

    @Override
    public void showError() {
        appbar.setExpanded(false);
        Snackbar.make(findViewById(R.id.coordinator), Utils.fromHtml("<font color=\"#ffffff\">" + getString(R.string.error_title_sync) + "</font>"), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.title_retry), v -> presenter.loadAccount())
                .show();
        errorLayout.setVisibility(View.VISIBLE);
        fab.setImageResource(R.drawable.ic_replay_white_24dp);
        fab.setOnClickListener(v -> presenter.loadAccount());
    }

    @Override
    public void updateAccount(Account account) {
        viewPager.getAdapter().notifyDataSetChanged();
        AccountDetailFragment fragment = (AccountDetailFragment) getSupportFragmentManager().findFragmentByTag(AccountDetailFragment.TAG);
        if (fragment != null) {
            fragment.updateChart(account);
        }
    }

    @Override
    public void showModifyAccount() {
        // TODO: 16/03/17
    }

    @Override
    public void showLoading() {
        errorLayout.setVisibility(View.GONE);
        detailContainer.setVisibility(View.GONE);
        noAccountLayout.setVisibility(View.GONE);
        fab.setImageResource(R.drawable.ic_sync_white_24dp);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        fab.startAnimation(animation);
    }

    @Override
    public void hideLoading() {
        fab.setImageResource(R.drawable.ic_add_white_24dp);
        fab.clearAnimation();
    }

    public RxAccountSource getRepo() {
        return this.rxAccountRepository;
    }

    public interface FabClick {
        void onClick();
    }
}



