package com.phoenix.soft.agenda.detail;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.account.AccountContract;

/**
 * Created by yaoda on 13/03/17.
 */

public class DialogAddFragment extends DialogFragment {

    private EditText mount;
    private TextView textView;
    private RadioGroup radioGroup;
    private DetailContract.Presenter presenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_detail, null);

        AlertDialog mDialog = new AlertDialog.Builder(getContext())
                .setTitle("New Income/Expenses")
                .setPositiveButton("add", (dialog, which) -> {
                    String mountNumber = mount.getText().toString();
                    if (mountNumber.equals("")) {
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.orangeRed));
                    } else {
                        boolean isAdd = false;
                        switch (radioGroup.getCheckedRadioButtonId()) {
                            case R.id.rd_income:
                                isAdd = true;
                                break;
                            case R.id.rd_outcome:
                                isAdd = false;
                                break;
                        }

                        presenter.addDetail(mountNumber,isAdd);
//                presenter.addDetail(mountNumber, isAdd);
//                        this.dismiss();
                    }
                })
                .setView(view)
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .create();

        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_add_detail, container, false);
        mount = (EditText) dialogView.findViewById(R.id.et_mount);
        textView = (TextView) dialogView.findViewById(R.id.tv_mount);
        radioGroup = (RadioGroup) dialogView.findViewById(R.id.rd_group);
        mount.setOnEditorActionListener((v1, actionId, event) -> {
            ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE).performClick();
//            getDialog().get
//            getDialog().getButton(DialogInterface.BUTTON_POSITIVE).performClick();
            return true;
        });
        
        return dialogView;
    }
}
