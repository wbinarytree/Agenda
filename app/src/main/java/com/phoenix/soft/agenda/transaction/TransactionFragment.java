package com.phoenix.soft.agenda.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.utils.Utils;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Transaction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yaoda on 23/02/17.
 */

public class TransactionFragment extends Fragment implements TransactionContract.View, MainActivity.FabClick {
    public final static String TAG = "DETAIL_FRAGMENT";
    private static final int REQUEST_CODE_DETAIL = 0xF1;
    @BindView(R.id.detail_list)
    RecyclerView detailRecyclerList;
    private Unbinder bind;
    private TransactionContract.Presenter presenter;
    private TransactionListAdapter transactionListAdapter;
    private Account account;

    public static TransactionFragment newInstance(Account account) {
        Bundle args = new Bundle();
        args.putParcelable("detail", account);
        TransactionFragment fragment = new TransactionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
        bind = ButterKnife.bind(this, view);
        account = (Account) getArguments().get("detail");
        setRetainInstance(true);
        Log.d(TAG, "onCreateView: ");
        return view;
    }

    public void setPresenter(TransactionContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        presenter.loadDetailList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        bind.unbind();
    }

    @Override
    public void showTransactionList(List<Transaction> transactions) {
        account.setTransactionList(transactions);
        transactionListAdapter = new TransactionListAdapter(account);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        detailRecyclerList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        detailRecyclerList.setAdapter(transactionListAdapter);
        detailRecyclerList.setLayoutManager(layout);
        detailRecyclerList.setItemAnimator(new SlideInLeftAnimator());
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        detailRecyclerList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.hide();
                } else if (dy < 0) {
                    fab.show();
                }
            }
        });
    }

    @Override
    public void showAddDetailDialog() {
        //Target Fragment will be set by FragmentManager. Which means it can be saved after saveInstance.
        TransactionAddDialogFragment dialogAddFragment = new TransactionAddDialogFragment();
        dialogAddFragment.setTargetFragment(this, REQUEST_CODE_DETAIL);
        dialogAddFragment.show(getFragmentManager(), "add");
    }

    @Override
    public void updateList() {
        transactionListAdapter.notifyItemInserted(0);
        detailRecyclerList.scrollToPosition(0);
    }

    @Override
    public void showNoTransaction() {

    }

    @Override
    public void showError(String errorMessage) {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.coordinator), Utils.fromHtml("<font color=\"#ffffff\">" + errorMessage + "</font>"), Snackbar.LENGTH_SHORT)
                                    .setAction("RETRY", v -> presenter.loadDetailList());
        snackbar.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_DETAIL:
                if (resultCode == RESULT_OK) {
                    presenter.addDetail(data.getStringExtra("mountNumber"), data.getBooleanExtra("isAdd", false));
                }
        }
    }

    @Override
    public void onClick() {
        showAddDetailDialog();
    }
}