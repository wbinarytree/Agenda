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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.support.v4.view.RxViewPager;
import com.jakewharton.rxbinding2.view.RxView;
import com.phoenix.soft.agenda.account.AccountContract;
import com.phoenix.soft.agenda.account.AccountPagerAdapter;
import com.phoenix.soft.agenda.account.AccountPresenter;
import com.phoenix.soft.agenda.hidden.HiddenActivity;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Events;
import com.phoenix.soft.agenda.repos.AccountRepository;
import com.phoenix.soft.agenda.repos.FirebaseAccountRepository;
import com.phoenix.soft.agenda.rxbus.RxBus;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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
    AccountRepository repository;
    private int count = 0;
    private AccountPagerAdapter adapter;
    private List<Account> accountList;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AccountContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MainApplication) getApplication()).getBuilder().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        presenter = new AccountPresenter(repository, this);
        presenter.loadAccount();

        disposable.add(RxBus.getInstance()
                            .toObservable()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(o -> {
                                if (o instanceof Events.ToolbarChangeEvent) {
                                    Log.d(TAG, "onCreate: message Receive");
                                }
                            }));

        disposable.add(RxView.clicks(fab)
                             .subscribe(o -> adapter.getFragment(viewPager.getCurrentItem())
                                                    .onClick()));
        tabLayout.setupWithViewPager(viewPager);

    }

    private void updateViewByPagerContent(int position) {
        Picasso.with(this)
               .load(Integer.valueOf(accountList.get(position).getAccountPicUrl()))
               .noFade()
               .resize(400, 800)
               .into(imageToolBar);
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
                Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator), Utils.fromHtml("<font color=\"#ffffff\">Activted Hidden Mode</font>"), Snackbar.LENGTH_SHORT);
                snackbar.show();
                count = 0;
                Intent intent = new Intent(this, HiddenActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.action_plus) {
            FirebaseAccountRepository repo = new FirebaseAccountRepository();
            repo.start();
            repo.addAccount(accountList.get(0));
            repo.end();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //need to dispose here. Otherwise the observable will running forever.
        disposable.clear();
    }

    @Override
    public void showAccountList(List<Account> accountList) {
        this.accountList = accountList;
        adapter = new AccountPagerAdapter(getSupportFragmentManager(), this.accountList);
        viewPager.setAdapter(adapter);
        disposable.add(RxViewPager.pageSelections(viewPager)
                                  .subscribe(this::updateViewByPagerContent));

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

    public interface FabClick {
        void onClick();
    }
}



