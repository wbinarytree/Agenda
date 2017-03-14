package com.phoenix.soft.agenda.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.R2;
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

/**
 * Created by yaoda on 23/02/17.
 */

public class DetailFragment extends Fragment implements DetailContract.View {
    public final static String TAG = "DETAIL_FRAGMENT";
    @BindView(R2.id.detail_list)
    RecyclerView detailRecyclerList;
    private Unbinder bind;
    private DetailContract.Presenter presenter;
    private DetailListAdapter detailListAdapter;

    public static DetailFragment newInstance(Account account) {
        Bundle args = new Bundle();
        args.putParcelable("detail", account);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DetailContract.Presenter getPresenter() {
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
        bind = ButterKnife.bind(this, view);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener((v -> showAddDetailDialog()));
        Account account = (Account) getArguments().get("detail");
        presenter = new DetailPresenter(account, this);
        presenter.loadDetailList();
        setHasOptionsMenu(true);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, null);
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
        detailListAdapter = new DetailListAdapter(detailList);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        detailRecyclerList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        detailRecyclerList.setAdapter(detailListAdapter);
        detailRecyclerList.setLayoutManager(layout);
        detailRecyclerList.setItemAnimator(new SlideInLeftAnimator());
        RxBus.getInstance().send(new Events.ToolbarChangeEvent(true));
    }

    @Override
    public void showAddDetailDialog() {
/*        // TODO: 03/03/17 change this into DialogFragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_add_detail, null);
        EditText mount = (EditText) dialogView.findViewById(R.id.et_mount);
        AlertDialog alertDialog = builder.setTitle("New Income/Expenses")
                .setView(dialogView)
                .setPositiveButton("add", (dialog, which) -> {
                })
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {

            RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.rd_group);
            String mountNumber = mount.getText().toString();
            if (mountNumber.equals("")) {
                TextView textView = (TextView) dialogView.findViewById(R.id.tv_mount);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.orangeRed));
            } else {
                boolean isAdd = false;
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rd_income:
                        isAdd = true;
                        break;
                    case R.id.rd_outcome:
                        isAdd = false;
                        break;
                }
                presenter.addDetail(mountNumber, isAdd);
                alertDialog.dismiss();
            }

        });
        mount.setOnEditorActionListener((v1, actionId, event) -> {
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
            return true;
        });*/

        DialogAddFragment dialogAddFragment = new DialogAddFragment();
        dialogAddFragment.setPresenter(this.presenter);
        dialogAddFragment.setTargetFragment(this,100);
        dialogAddFragment.show(getFragmentManager(),"add");

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
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(R.id.coordinator), Utils.fromHtml("<font color=\"#ffffff\">" + errorMessage + "</font>"), Snackbar.LENGTH_SHORT)
                .setAction("RETRY", v -> presenter.loadDetailList());
        snackbar.show();
    }

}
