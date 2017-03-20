package com.phoenix.soft.agenda.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.Utils;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Detail;
import com.phoenix.soft.agenda.module.Events;
import com.phoenix.soft.agenda.rxbus.RxBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yaoda on 23/02/17.
 */

public class DetailFragment extends Fragment implements DetailContract.View, MainActivity.FabClick {
    public final static String TAG = "DETAIL_FRAGMENT";
    private static final int REQUEST_CODE_DETAIL = 0xF1;
    @BindView(R.id.detail_list)
    RecyclerView detailRecyclerList;
    private Unbinder bind;
    private DetailContract.Presenter presenter;
    private DetailListAdapter detailListAdapter;
    private Account account;

    public static DetailFragment newInstance(Account account) {
        Bundle args = new Bundle();
        args.putParcelable("detail", account);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
        bind = ButterKnife.bind(this, view);
        account = (Account) getArguments().get("detail");
        presenter = new DetailPresenter(account.getKey(), this);
        presenter.loadDetailList();
        setRetainInstance(true);
        Log.d(TAG, "onCreateView: ");
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
    public void showDetailList(List<Detail> details) {
        account.setDetailList(details);
        detailListAdapter = new DetailListAdapter(account);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        detailRecyclerList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        detailRecyclerList.setAdapter(detailListAdapter);
        detailRecyclerList.setLayoutManager(layout);
        detailRecyclerList.setItemAnimator(new SlideInLeftAnimator());
        RxBus.getInstance().send(new Events.ToolbarChangeEvent(true));
    }

    @Override
    public void showAddDetailDialog() {
        //Target Fragment will be set by FragmentManager. Which means it can be saved after saveInstance.
        DialogAddFragment dialogAddFragment = new DialogAddFragment();
        dialogAddFragment.setTargetFragment(this, REQUEST_CODE_DETAIL);
        dialogAddFragment.show(getFragmentManager(), "add");
    }

    @Override
    public void updateList() {
        detailListAdapter.notifyItemInserted(0);
        detailRecyclerList.scrollToPosition(0);
    }

    @Override
    public void showNoDetail() {

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
