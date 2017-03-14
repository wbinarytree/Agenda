package com.phoenix.soft.agenda.detail;

import android.app.Dialog;
import android.content.DialogInterface;
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

/**
 * Created by yaoda on 13/03/17.
 */

public class DialogAddFragment extends DialogFragment {

    private EditText mount;
    private TextView textView;
    private RadioGroup radioGroup;
    private DetailContract.Presenter presenter;

    //Not a big fan for this approach
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_detail, null);

        AlertDialog mDialog = new AlertDialog.Builder(getContext())
                .setTitle("New Income/Expenses")
                .setPositiveButton("add", null)
                .setNegativeButton("cancel", null)
                .setView(view)
                .create();
        mDialog.setOnShowListener(dialog -> ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> addDetail()));
        mount = (EditText) view.findViewById(R.id.et_mount);
        textView = (TextView) view.findViewById(R.id.tv_mount);
        radioGroup = (RadioGroup) view.findViewById(R.id.rd_group);
        mount.setOnEditorActionListener((v1, actionId, event) -> {
            addDetail();
            return true;
        });
        return mDialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO change this
        presenter = ((DetailFragment)getFragmentManager().findFragmentByTag(DetailFragment.TAG)).getPresenter();
    }

    private void addDetail() {
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
            presenter.addDetail(mountNumber, isAdd);
            getDialog().dismiss();
        }
    }

}
