package com.phoenix.soft.agenda.account;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.module.Account;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yaoda on 13/03/17.
 */

public class AccountAddDialogFragment extends DialogFragment {
    public static final String TAG = "AddAccountDialog";
    @BindView(R.id.et_mount)
    EditText mount;
    @BindView(R.id.et_name)
    EditText name;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_account, null);
        ButterKnife.bind(this, view);
        AlertDialog mDialog = new AlertDialog.Builder(getContext()).setTitle(R.string.title_transaction_add)
                                                                   .setPositiveButton("add", null)
                                                                   .setNegativeButton("cancel", null)
                                                                   .setView(view)
                                                                   .create();
        mDialog.setOnShowListener(dialog -> ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                                                  .setOnClickListener(v -> addAccount()));
        name.setOnEditorActionListener((v1, actionId, event) -> {
            addAccount();
            return true;
        });
        return mDialog;
    }


    private void addAccount() {
        String mountNumber = mount.getText().toString();
        String accountName = name.getText().toString();
        if (mountNumber.equals("")) {
            mount.setError("Enter Number");
        } else if (accountName.equals("")) {
            name.setError("Enter Name");
        } else {
            MainActivity activity = (MainActivity) getActivity();
            Account account = new Account();
            account.setAccountName(accountName);
            account.setIncome(Money.parse("USD " + mountNumber));
            account.setOutcome(Money.zero(CurrencyUnit.USD));
            account.setAccountPicUrl("123");
            activity.getPresenter().addAccount(account);
            getDialog().dismiss();
        }
    }
}




