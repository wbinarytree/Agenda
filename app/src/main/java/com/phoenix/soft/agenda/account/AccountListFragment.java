package com.phoenix.soft.agenda.account;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.R2;
import com.phoenix.soft.agenda.Utils;
import com.phoenix.soft.agenda.detail.DetailFragment;
import com.phoenix.soft.agenda.detail.DetailListAdapter;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Events;
import com.phoenix.soft.agenda.repos.TestAccountRepository;
import com.phoenix.soft.agenda.rxbus.RxBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AccountListFragment extends Fragment implements AccountContract.View {
    public static final String TAG = "ACCOUNT_LIST_FRAGMENT";
    @BindView(R2.id.account_list)
    RecyclerView accountRecyclerView;
    @BindView(R2.id.layout_loading)
    FrameLayout loadingProcess;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private AccountContract.Presenter presenter;
    private Unbinder bind;
    private AccountListAdapter adapter;
    private TestAccountRepository repository;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_list, container, false);
        bind = ButterKnife.bind(this, view);
        fab = ((MainActivity) getActivity()).getFab();
        fab.setOnClickListener(v -> AccountListFragment.this.showError());
        toolbar = ((MainActivity) getActivity()).getToolbar();
        toolbar.setOnClickListener(v -> presenter.addAccount(null));
        RxBus.getInstance().send(new Events.ToolbarChangeEvent(false));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        repository = new TestAccountRepository(getContext());
        presenter = new AccountPresenter(repository, this);
        presenter.loadAccount();
    }

    @Override
    public void showAccountList(List<Account> accounts) {
        accountRecyclerView.setVisibility(View.VISIBLE);
        loadingProcess.setVisibility(View.GONE);
        // TODO: 23/02/17 add different layoutManager
        accountRecyclerView.setLayoutManager(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                new LinearLayoutManager(getContext()) : new GridLayoutManager(getContext(), 2));
        adapter = new AccountListAdapter(accounts, getContext(), this);
        accountRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showNoAccount() {
        accountRecyclerView.setVisibility(View.GONE);
        loadingProcess.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(R.id.coordinator),
                        Utils.fromHtml("<font color=\"#ffffff\">Loading error pleas wait</font>"),
                        Snackbar.LENGTH_SHORT)
                .setAction("RETRY", v -> presenter.loadAccount());
        snackbar.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void showDetails(Account account, int position) {
        // TODO: 23/02/17
        Fragment fragment = new DetailFragment();
        Bundle arg = new Bundle();
        arg.putParcelable("detail", account);
        fragment.setArguments(arg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new ChangeBounds());
            fragment.setSharedElementReturnTransition(new AutoTransition());

        }
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_content, fragment, DetailFragment.TAG)
                // RecyclerView.getChildAt(int index) only get those children shown on the screen.
                // We need the adapter to get the specific ViewHolder
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addSharedElement(((AccountListAdapter.ViewHolder) accountRecyclerView.
                        findViewHolderForAdapterPosition(position)).cardView, getString(R.string.account_transition))
                .addToBackStack("account_page")
                .commit();
    }

    @Override
    public void updateAccount() {
        adapter.addAccount();
        accountRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void showModifyAccount() {
        // TODO: 27/02/17  add modify mode when recyclerview child long touched
        for(int i = 0;i< adapter.getItemCount();i++){
            // TODO: 03/03/17
            //specific animation here . add onclick
            ImageView ivDel = ((AccountListAdapter.ViewHolder) accountRecyclerView.findViewHolderForAdapterPosition(i)).ivDel;
            ivDel.setVisibility(View.VISIBLE);
            RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(5000);
            animation.setInterpolator(new LinearInterpolator());
            ivDel.startAnimation(animation);
        }
    }
}
