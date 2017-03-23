package com.phoenix.soft.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.support.v4.view.RxViewPager;
import com.jakewharton.rxbinding2.view.RxView;
import com.phoenix.soft.agenda.account.AccountAddDialogFragment;
import com.phoenix.soft.agenda.account.AccountContract;
import com.phoenix.soft.agenda.account.AccountDetailFragment;
import com.phoenix.soft.agenda.account.AccountPagerAdapter;
import com.phoenix.soft.agenda.account.AccountPresenter;
import com.phoenix.soft.agenda.hidden.HiddenActivity;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.FirebaseAccountRepository;
import com.phoenix.soft.agenda.repos.RxAccountSource;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @Inject
    RxAccountSource rxAccountRepository;
    private int count = 0;
    private AccountPagerAdapter adapter;
    private List<Account> accountList;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AccountContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainApplication.getFirebaseComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        presenter = new AccountPresenter(rxAccountRepository, this);
        presenter.loadAccount();

        disposable.add(RxView.clicks(fab)
                             .subscribe(o -> adapter.getFragment(viewPager.getCurrentItem())
                                                    .onClick()));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setPageMargin(20);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.container_detail, AccountDetailFragment.newInstance(), AccountDetailFragment.TAG)
                                       .commit();

        }
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
                Snackbar.make(findViewById(R.id.coordinator), Utils.fromHtml("<font color=\"#ffffff\">Activted Hidden Mode</font>"), Snackbar.LENGTH_SHORT)
                        .show();
                count = 0;
                Intent intent = new Intent(this, HiddenActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.action_plus) {
            // TODO: 23/03/17
            AccountAddDialogFragment fragment = new AccountAddDialogFragment();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //need to dispose here. Otherwise the observable will running forever.
        disposable.clear();
    }

    public List<Account> getAccountList() {
        return accountList;
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
        }
    }

    @Override
    public void showNoAccount() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void updateAccount() {
        // TODO: 16/03/17
    }

    @Override
    public void showModifyAccount() {
        // TODO: 16/03/17
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public RxAccountSource getRepo() {
        return this.rxAccountRepository;
    }

    public interface FabClick {
        void onClick();
    }
}



