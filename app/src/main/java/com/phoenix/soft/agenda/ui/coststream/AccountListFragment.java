package com.phoenix.soft.agenda.ui.coststream;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.R2;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.TestAccountRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yaoda on 22/02/17.
 */

public class AccountListFragment extends Fragment implements CostStreamContract.View {
    public static final String TAG = "ACCOUNT_LIST_FRAGMENT";
    @BindView(R2.id.account_list)
    RecyclerView accountList;
    @BindView(R2.id.layout_loading)
    FrameLayout loadingProcess;
    private FloatingActionButton fab;
    private CostStreamContract.Presenter presenter;
    private Unbinder bind;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_account_list, container, false);
        bind = ButterKnife.bind(this, view);
        fab = ((MainActivity)getActivity()).getFab();
        fab.setOnClickListener(v -> this.showError());
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new CostStreamPresenter(new TestAccountRepository(),this);
        presenter.loadAccount();
    }

    @Override
    public void showAccountList(List<Account> accounts) {
        accountList.setVisibility(View.VISIBLE);
        loadingProcess.setVisibility(View.GONE);
        accountList.setAdapter(new AccountListAdapter(accounts, getContext(), this));
        accountList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void showNoAccount() {
        accountList.setVisibility(View.GONE);
        loadingProcess.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(R.id.coordinator), "Loading error pleas wait", Snackbar.LENGTH_LONG)
                .setAction("RETRY", v -> presenter.loadAccount());
        snackbar.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Override
    public void showDetails() {
        // TODO: 23/02/17
    }
}
