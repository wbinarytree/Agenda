package com.phoenix.soft.costy.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.phoenix.soft.costy.MainApplication;
import com.phoenix.soft.costy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yaoda on 17/03/17.
 */

public class LoginFragment extends Fragment implements AuthActivity.FabClick {
    public static final String TAG = "LoginFragment";
    @BindView(R.id.email)
    AutoCompleteTextView etUsername;
    @BindView(R.id.password)
    EditText etPassword;
    FloatingActionButton fab;


    public static LoginFragment newInstance() {
         Bundle args = new Bundle();
         LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        MainApplication.getAppComponent().inject(this);
        ButterKnife.bind(this, v);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        etPassword.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                onFabClick(textView);
                return true;
            }
            return false;
        });
        return v;
    }


    @Override
    public void onFabClick(View v) {

    }
}
