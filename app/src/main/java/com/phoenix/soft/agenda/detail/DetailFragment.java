package com.phoenix.soft.agenda.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.R2;
import com.phoenix.soft.agenda.module.Account;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yaoda on 23/02/17.
 */

public class DetailFragment extends Fragment {
    public final static String TAG = "DETAIL_FRAGMENT";
    @BindView(R2.id.detail_list)
    RecyclerView detailList;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(container == null){
            return null;
        }
        View v = inflater.inflate(R.layout.fragment_detail_list,container,false);
        bind = ButterKnife.bind(this, v);
        Account account = (Account) getArguments().get("detail");
        detailList.setAdapter(new DetailListAdapter(account.getDetailList()));
        detailList.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
