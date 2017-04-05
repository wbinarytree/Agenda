package com.phoenix.soft.agenda.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
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
    private List<Highlight> highlights;

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
        pieChart.setEntryLabelColor(R.color.primary_dark);
        pieChart.setHighlightPerTapEnabled(false);
        pieChart.setDrawCenterText(true);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        return view;
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
    private List<PieEntry> setUpEntries(List<Account> accountList) {
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
        if (pieChart != null && pieChart.getData() != null && position <= highlights.size()) {
            pieChart.highlightValue(highlights.get(position));
        }
    }

    @Override
    public void showNoChart() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void updateChart(Account account) {
        IPieDataSet dataSet = pieChart.getData().getDataSet();
        Money minus = account.getIncome().minus(account.getOutcome());
        dataSet.addEntry(new PieEntry(minus.getAmount().floatValue(), account.getAccountName()));
        highlights.add(new Highlight(highlights.size(), 0, 0));
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }
}