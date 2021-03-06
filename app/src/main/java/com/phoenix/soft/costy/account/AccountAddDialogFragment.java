/*
 * Copyright 2017 WBinaryTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phoenix.soft.costy.account;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.phoenix.soft.costy.MainActivity;
import com.phoenix.soft.costy.R;
import com.phoenix.soft.costy.models.Account;
import java.util.ArrayList;
import java.util.List;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * Created by yaoda on 13/03/17.
 */

public class AccountAddDialogFragment extends DialogFragment {
    public static final String TAG = "AddAccountDialog";
    private static List<CurrencyUnit> currencyUnitList = new ArrayList<>();

    static {
        currencyUnitList.add(CurrencyUnit.USD);
        currencyUnitList.add(CurrencyUnit.EUR);
        currencyUnitList.add(CurrencyUnit.of("CNY"));
        currencyUnitList.add(CurrencyUnit.GBP);
        currencyUnitList.add(CurrencyUnit.JPY);
    }

    @BindView(R.id.et_mount) EditText mount;
    @BindView(R.id.et_name) EditText name;
    @BindView(R.id.currency_spinner) Spinner spinner;

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_account, null);
        ButterKnife.bind(this, view);
        AlertDialog mDialog =
            new AlertDialog.Builder(getContext()).setTitle(R.string.title_transaction_add)
                .setPositiveButton("add", null)
                .setNegativeButton("cancel", null)
                .setView(view)
                .create();
        mDialog.setOnShowListener(
            dialog -> ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(v -> addAccount()));
        name.setOnEditorActionListener((v1, actionId, event) -> {
            addAccount();
            return true;
        });
        spinner.setAdapter(new CurrencyAdapter(getContext(), currencyUnitList));
        return mDialog;
    }

    private void addAccount() {
        String mountNumber = mount.getText().toString();
        String accountName = name.getText().toString();
        CurrencyUnit currencyUnit = (CurrencyUnit) spinner.getSelectedItem();
        if (mountNumber.equals("")) {
            mount.setError("Enter Number");
        } else if (accountName.equals("")) {
            name.setError("Enter Name");
        } else {
            MainActivity activity = (MainActivity) getActivity();
            Account account = new Account();
            account.setCurrency(currencyUnit);
            account.setAccountName(accountName);
            account.setIncome(Money.parse(currencyUnit + mountNumber));
            account.setOutcome(Money.zero(currencyUnit));
            account.setAccountPicUrl("123");
            activity.getPresenter().addAccount(account);
            getDialog().dismiss();
        }
    }

    private class CurrencyAdapter extends ArrayAdapter<CurrencyUnit> {

        CurrencyAdapter(@NonNull Context context, List<CurrencyUnit> currencyUnitList) {
            super(context, android.R.layout.simple_spinner_dropdown_item, currencyUnitList);
        }
    }
}




