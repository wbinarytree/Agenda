package com.phoenix.soft.agenda.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.R2;
import com.phoenix.soft.agenda.Utils;
import com.phoenix.soft.agenda.account.AccountPresenter;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Detail;
import com.phoenix.soft.agenda.repos.TestAccountRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yaoda on 23/02/17.
 */

public class DetailFragment extends Fragment implements DetailContract.View {
    public final static String TAG = "DETAIL_FRAGMENT";
    @BindView(R2.id.detail_list)
    RecyclerView detailRecyclerList;
    private Unbinder bind;
    private DetailContract.Presenter presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list,container,false);
        bind = ButterKnife.bind(this, view);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener((v -> showAddDetailDialog()));
        Account account = (Account) getArguments().get("detail");
        presenter = new DetailPresenter(account,this);
        presenter.loadDetailList();
        setRetainInstance(true);
        return view;
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
    public void showDetailList(List<Detail> detailList) {
        detailRecyclerList.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        detailRecyclerList.setAdapter(new DetailListAdapter(detailList));
        detailRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void showAddDetailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle("New Income/Expenses")
                .setMessage("Test message")
                .setView(inflater.inflate(R.layout.dialog_add_detail, null))
                .create()
                .show();

    }

    @Override
    public void showNoDetail() {

    }

    @Override
    public void showError() {
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(R.id.coordinator), Utils.fromHtml("<font color=\"#ffffff\">Loading error pleas wait</font>"), Snackbar.LENGTH_SHORT)
                .setAction("RETRY", v -> presenter.loadDetailList());
        snackbar.show();
    }
}
