package com.phoenix.soft.agenda.account;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.phoenix.soft.agenda.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yaoda on 13/03/17.
 */

public class AccountAddDialogFragment extends DialogFragment {
    private final String TAG = "AddAccountDialog";
    @BindView(R.id.et_name)
    EditText mount;
    @BindView(R.id.et_mount)
    EditText name;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_detail, null);
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
        } else if(accountName.equals("")){
            name.setError("Enter Name");
        }else{
            Intent intent = new Intent().putExtra("mountNumber", mountNumber)
                                        .putExtra("name", accountName);
            onActivityResult(getTargetRequestCode(),RESULT_OK,intent);
            getDialog().dismiss();
        }
    }
}




