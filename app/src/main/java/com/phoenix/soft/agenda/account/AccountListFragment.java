package com.phoenix.soft.agenda.account;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.R2;
import com.phoenix.soft.agenda.detail.DetailFragment;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.TestAccountRepository;
import com.phoenix.soft.agenda.transition.DetailTransition;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yaoda on 22/02/17.
 */

public class AccountListFragment extends Fragment implements AccountContract.View {
    public static final String TAG = "ACCOUNT_LIST_FRAGMENT";
    @BindView(R2.id.account_list)
    RecyclerView accountList;
    @BindView(R2.id.layout_loading)
    FrameLayout loadingProcess;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private AccountContract.Presenter presenter;
    private Unbinder bind;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_account_list, container, false);
        bind = ButterKnife.bind(this, view);
        fab = ((MainActivity) getActivity()).getFab();
        fab.setOnClickListener(v -> this.showError());
        toolbar = ((MainActivity) getActivity()).getToolbar();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new AccountPresenter(new TestAccountRepository(), this);
        presenter.loadAccount();
    }

    @Override
    public void showAccountList(List<Account> accounts) {
        accountList.setVisibility(View.VISIBLE);
        loadingProcess.setVisibility(View.GONE);
        // TODO: 23/02/17 add different layoutManager
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
                .make(getActivity().findViewById(R.id.coordinator), "Loading error pleas wait", Snackbar.LENGTH_SHORT)
                .setAction("RETRY", v -> presenter.loadAccount());
        snackbar.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void showDetails(Account account) {
        // TODO: 23/02/17
        Fragment fragment = new DetailFragment();
        Bundle arg = new Bundle();
        arg.putParcelable("detail",account);
        fragment.setArguments(arg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new AutoTransition());
            fragment.setSharedElementReturnTransition(new AutoTransition());
            
        }
            getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_content,fragment ,DetailFragment.TAG)
                .addSharedElement(accountList.findViewById(R.id.account_card), getString(R.string.account_transition))
                .addToBackStack("account_page")
                .commit();
    }
}
