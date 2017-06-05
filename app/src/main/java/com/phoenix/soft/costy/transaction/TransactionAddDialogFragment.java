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

package com.phoenix.soft.costy.transaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.phoenix.soft.costy.R;
import com.phoenix.soft.costy.models.Account;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yaoda on 13/03/17.
 */

public class TransactionAddDialogFragment extends DialogFragment {
    @BindView(R.id.et_mount)
    EditText mount;
    @BindView(R.id.rd_group)
    RadioGroup radioGroup;

    public static TransactionAddDialogFragment newInstance(Account account) {
        Bundle args = new Bundle();
        args.putString("account_id",account.getKey());
        TransactionAddDialogFragment fragment = new TransactionAddDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_detail, null);
        ButterKnife.bind(this, view);
        AlertDialog mDialog =
            new AlertDialog.Builder(getContext()).setTitle(R.string.title_transaction_add)
                .setPositiveButton("add", null)
                .setNegativeButton("cancel", null)
                .setView(view)
                .create();
        mDialog.setOnShowListener(
            dialog -> ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(v -> addTransaction()));
        mount.setOnEditorActionListener((v1, actionId, event) -> {
            addTransaction();
            return true;
        });
        return mDialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addTransaction() {
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
            Intent intent =
                new Intent().putExtra("mountNumber", mountNumber).putExtra("isAdd", isAdd);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            getDialog().dismiss();
        }
    }
}
