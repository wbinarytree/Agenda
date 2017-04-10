package com.phoenix.soft.agenda.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.module.Account;

import org.joda.money.Money;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yaoda on 22/03/17.
 */

public class AccountDetailFragment extends Fragment implements AccountDetailContract.View {

    public static final String TAG = "AccountDetailFragment";
    @BindView(R.id.detail_chart)
    PieChart pieChart;
    @BindView(R.id.tv_total)
    TextView textView;
    private List<Highlight> highlights;
    private List<Account> accountList;

    public static AccountDetailFragment newInstance() {
        Bundle args = new Bundle();
        AccountDetailFragment fragment = new AccountDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_detail, container, false);
        ButterKnife.bind(this, view);
        initPieChart();
        return view;
    }

    private void initPieChart() {
        pieChart.setEntryLabelColor(R.color.primary_dark);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setDrawCenterText(true);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
//        pieChart.setClickable(true);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int i = pieChart.getData().getMaxEntryCountSet().getEntryIndex((PieEntry) e);
                if (i != -1) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.selectAccount(i);
                }

            }

            @Override
            public void onNothingSelected() {
                MainActivity activity = (MainActivity) getActivity();
                activity.selectAccount(0);
            }
        });
    }

    @Override
    public void showPieChart() {
        ((MainActivity) getActivity()).getAccountList()
                                      .map(accountList -> setupPieData(setUpEntries(accountList)))
                                      .subscribe(pieData -> {
                                          pieChart.setData(pieData);
                                          selectChart(0);
                                          pieChart.invalidate();
                                      }, throwable -> showError(), this::showNoChart);
    }

    @NonNull
    private List<PieEntry> setUpEntries(List<Account> list) {
        this.accountList = list;
        List<PieEntry> entries = new ArrayList<>(accountList.size());
        for (Account account : accountList) {
            Money minus = account.getIncome().minus(account.getOutcome());
            entries.add(new PieEntry(minus.getAmount().floatValue(), account.getAccountName()));
        }
        return entries;
    }

    @NonNull
    private PieData setupPieData(List<PieEntry> pieEntries) {
        highlights = new ArrayList<>(pieEntries.size());
        for (int i = 0; i < pieEntries.size(); i++) {
            highlights.add(new Highlight(i, 0, 0));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, getString(R.string.title_summary));
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        dataSet.setValueTextColor(R.color.primary_dark);
        dataSet.setValueTextSize(14);
        dataSet.setSelectionShift(8f);
        dataSet.setSliceSpace(3f);
        return new PieData(dataSet);
    }

    @Override
    public void showWeeklyDetail() {

    }

    @Override
    public void selectChart(int position) {
        if (pieChart != null && pieChart.getData() != null && position < highlights.size()) {
            pieChart.highlightValue(highlights.get(position));
        }
        if (position < accountList.size()) {
            Account account = accountList.get(position);
            Money minus = account.getIncome().minus(account.getOutcome());
            String text = minus.getCurrencyUnit().getSymbol() + minus.getAmount().toString();
            textView.setText(text);
        }
    }

    @Override
    public void showNoChart() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void addAccountToChart(Account account) {
        IPieDataSet dataSet = pieChart.getData().getDataSet();
        Money minus = account.getIncome().minus(account.getOutcome());
        dataSet.addEntry(new PieEntry(minus.getAmount().floatValue(), account.getAccountName()));
        highlights.add(new Highlight(highlights.size(), 0, 0));
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }

    @Override
    public void deleteAccountToChart(Account account) {
        IPieDataSet dataSet = pieChart.getData().getDataSet();
        int index = -1;
        for (int i = 0; i < dataSet.getEntryCount(); i++) {
            PieEntry entry = dataSet.getEntryForIndex(i);
            if (entry.getLabel().equals(account.getAccountName())) {
                index = i;
            }
        }
        if (index != -1) {
            dataSet.removeEntry(index);
        }
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }

    @Override
    public void updateAccountChart(Account account) {
        IPieDataSet dataSet = pieChart.getData().getDataSet();
        PieEntry entry = dataSet.getEntryForIndex(accountList.indexOf(account));
        Money minus = account.getIncome().minus(account.getOutcome());
        entry.setY(minus.getAmount().floatValue());
        entry.setLabel(account.getAccountName());
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
        String text = minus.getCurrencyUnit().getSymbol() + minus.getAmount().toString();
        textView.setText(text);
    }
}
