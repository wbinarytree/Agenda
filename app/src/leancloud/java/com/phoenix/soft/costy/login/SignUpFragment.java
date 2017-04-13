package com.phoenix.soft.costy.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.phoenix.soft.costy.MainApplication;
import com.phoenix.soft.costy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.phoenix.soft.costy.utils.Utils.isEmailValid;
import static com.phoenix.soft.costy.utils.Utils.isPasswordValid;
import static com.phoenix.soft.costy.utils.Utils.isUsername;

/**
 * Created by phoenix on 2017/3/19.
 */

public class SignUpFragment extends Fragment implements AuthActivity.FabClick {
    public static final String TAG = "SignUpFragment";
    @BindView(R.id.email)
    AutoCompleteTextView etUsername;
    @BindView(R.id.password)
    EditText etPassword;
    @BindView(R.id.username)
    EditText etNickName;
    FloatingActionButton fab;

    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainApplication.getAppComponent().inject(this);
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        return view;
    }

    @Override
    public void onFabClick(View view) {
        etUsername.setError(null);
        etPassword.setError(null);
        etNickName.setError(null);
        String email = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String nickname = etPassword.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            etUsername.setError(getString(R.string.error_field_required));
            focusView = etUsername;
            cancel = true;
        } else if (!isEmailValid(email)) {
            etUsername.setError(getString(R.string.error_invalid_email));
            focusView = etUsername;
            cancel = true;
        } else if (!isUsername(nickname)) {
            etNickName.setError(getString(R.string.error_nick_name));
            focusView = etNickName;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
                //sign Up
        }
    }
}
