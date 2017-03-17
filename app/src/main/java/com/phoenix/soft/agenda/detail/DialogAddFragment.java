package com.phoenix.soft.agenda.detail;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.phoenix.soft.agenda.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yaoda on 13/03/17.
 */

public class DialogAddFragment extends DialogFragment {
    @BindView(R.id.et_mount)
    EditText mount;
    @BindView(R.id.rd_group)
    RadioGroup radioGroup;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_detail, null);
        ButterKnife.bind(this,view);
        AlertDialog mDialog = new AlertDialog.Builder(getContext())
                .setTitle("New Income/Expenses")
                .setPositiveButton("add", null)
                .setNegativeButton("cancel", null)
                .setView(view)
                .create();
        mDialog.setOnShowListener(dialog -> ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> addDetail()));
        mount.setOnEditorActionListener((v1, actionId, event) -> {
            addDetail();
            return true;
        });
        return mDialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addDetail() {
        String mountNumber = mount.getText().toString();
        if (mountNumber.equals("")) {
            mount.setError("Enter Number");
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
            Intent intent = new Intent()
                    .putExtra("mountNumber", mountNumber)
                    .putExtra("isAdd", isAdd);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            getDialog().dismiss();
        }
    }


}
