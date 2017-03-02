package com.phoenix.soft.agenda.account;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.R2;
import com.phoenix.soft.agenda.module.Account;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yaoda on 22/02/17.
 */

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {
    private List<Account> accountList;
    private Context context;
    private AccountContract.View view;


    public AccountListAdapter(List<Account> accountList, Context context, AccountContract.View view) {
        this.accountList = accountList;
        this.context = context;
        this.view = view;
    }


    public void addAccount(){
        this.notifyItemChanged(accountList.size()-1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Account account = accountList.get(position);
        holder.tvIncome.setText(account.getIncome().toString());
        holder.tvOutcome.setText(account.getOutcome().toString());
        String total = account.getIncome().minus(account.getOutcome()).toString();
        if(total.contains("-")){
            holder.tvTotal.setTextColor(ContextCompat.getColor(context,R.color.orangeRed));
        }else{
            holder.tvTotal.setTextColor(ContextCompat.getColor(context,R.color.limeGreen));
        }
        holder.tvTotal.setText(total);
        ViewCompat.setTransitionName(holder.cardView,"accountCard" + String.valueOf(position));
        holder.cardView.setOnClickListener(v -> view.showDetails(account, position));
        holder.cardView.setOnLongClickListener(v -> {
            view.showModifyAccount();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.tv_income)
        TextView tvIncome;
        @BindView(R2.id.tv_outcome)
        TextView tvOutcome;
        @BindView(R2.id.tv_total)
        TextView tvTotal;
        @BindView(R2.id.account_card)
        CardView cardView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}
