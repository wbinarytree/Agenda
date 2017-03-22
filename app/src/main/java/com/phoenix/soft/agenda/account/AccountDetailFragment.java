package com.phoenix.soft.agenda.account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.repos.RxAccountRepository;

/**
 * Created by yaoda on 22/03/17.
 */

public class AccountDetailFragment extends Fragment implements AccountDetailContract.View {


    private RxAccountRepository repository;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        repository = ((MainActivity)getActivity()).getRepo();
    }

    public static AccountDetailFragment newInstance() {
        Bundle args = new Bundle();
        AccountDetailFragment fragment = new AccountDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account_detail, container, false);
    }

    @Override
    public void showPieChart() {

    }

    @Override
    public void showWeekDeatail() {

    }

}
