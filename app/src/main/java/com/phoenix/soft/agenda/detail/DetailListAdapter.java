package com.phoenix.soft.agenda.detail;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Detail;

import org.joda.money.Money;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yaoda on 23/02/17.
 */

public class DetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = -1;
    private List<Detail> details;
    private Account account;

    public DetailListAdapter(List<Detail> details) {
        this.details = details;
    }

    public DetailListAdapter(Account account) {
        this.details = account.getDetailList();
        this.account = account;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_balance_header,parent,false);
            return new HeaderHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_detail_list, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemHolder){
            ItemHolder itemHolder = (ItemHolder) holder;
            Context context = itemHolder.tvDesc.getContext();
            Detail detail = details.get(position-1);
            itemHolder.tvDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(detail
                    .getDate()));
            itemHolder.tvDesc.setText(context.getText(R.string.tmp_detail_desc));
            Money money = detail.getMoney();
            if (money.toString().contains("-")) {
                //holder.tvTitle.setText(holder.tvTitle.getContext().getText(R.string.outcome_title));
                String text = "- " + money.toString().replace("-", " ");
                itemHolder.tvNumber.setText(text);
                itemHolder.tvNumber.setTextColor(ContextCompat.getColor(context, R.color.orangeRed));
            } else {
                //holder.tvTitle.setText(holder.tvTitle.getContext().getText(R.string.income_title));
                String s = "+ " + money.toString();
                itemHolder.tvNumber.setText(s);
                itemHolder.tvNumber.setTextColor(ContextCompat.getColor(context, R.color.limeGreen));
            }
        } else if(holder instanceof HeaderHolder){
            HeaderHolder headerHolder = (HeaderHolder) holder;
            Context context = headerHolder.cardView.getContext();
            headerHolder.tvIncome.setText(account.getIncome().toString());
            headerHolder.tvOutcome.setText(account.getOutcome().toString());
            String total = account.getIncome().minus(account.getOutcome()).toString();
            if (total.contains("-")) {
                headerHolder.tvTotal.setTextColor(ContextCompat.getColor(context, R.color.orangeRed));
            } else {
                headerHolder.tvTotal.setTextColor(ContextCompat.getColor(context, R.color.limeGreen));
            }
            headerHolder.tvTotal.setText(total);
            ViewCompat.setTransitionName(headerHolder.cardView, "accountCard" + String.valueOf(position));
            //holder.cardView.setOnClickListener(v -> view.showDetails(account, position));
//            headerHolder.cardView.setOnLongClickListener(v -> {
//                view.showModifyAccount();
//                return true;
//            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return details.size() + 1;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_detail_desc)
        TextView tvDesc;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_title_type)
        TextView tvTitle;

        public ItemHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_income)
        TextView tvIncome;
        @BindView(R.id.tv_outcome)
        TextView tvOutcome;
        @BindView(R.id.tv_total)
        TextView tvTotal;
        @BindView(R.id.account_card)
        CardView cardView;

        public HeaderHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}