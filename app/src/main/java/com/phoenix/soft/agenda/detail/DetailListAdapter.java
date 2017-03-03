package com.phoenix.soft.agenda.detail;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.R2;
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

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.ViewHolder> {
    private List<Detail> details;

    public DetailListAdapter(List<Detail> details) {
        this.details = details;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.tvDesc.getContext();
        Detail detail = details.get(position);
        holder.tvDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(detail.getDate()));
        holder.tvDesc.setText(context.getText(R.string.tmp_detail_desc));
        Money money = detail.getMoney();
        if (money.toString().contains("-")) {
            //holder.tvTitle.setText(holder.tvTitle.getContext().getText(R.string.outcome_title));
            String text = "- " + money.toString().replace("-", " ");
            holder.tvNumber.setText(text);
            holder.tvNumber.setTextColor(ContextCompat.getColor(context, R.color.orangeRed));
        } else {
            //holder.tvTitle.setText(holder.tvTitle.getContext().getText(R.string.income_title));
            String s = "+ " + money.toString();
            holder.tvNumber.setText(s);
            holder.tvNumber.setTextColor(ContextCompat.getColor(context, R.color.limeGreen));

        }
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tv_detail_desc)
        TextView tvDesc;
        @BindView(R2.id.tv_date)
        TextView tvDate;
        @BindView(R2.id.tv_number)
        TextView tvNumber;
        @BindView(R2.id.tv_title_type)
        TextView tvTitle;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}
