package com.phoenix.soft.costy.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.phoenix.soft.costy.MainApplication;
import com.phoenix.soft.costy.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.phoenix.soft.costy.utils.Utils.isEmailValid;
import static com.phoenix.soft.costy.utils.Utils.isPasswordValid;

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
    @Inject
    FirebaseAuth auth;

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
        etUsername.setError(null);
        etPassword.setError(null);
        String email = etUsername.getText().toString();
        String password = etPassword.getText().toString();
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
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            fab.setImageResource(R.drawable.ic_sync_white_24dp);
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            fab.startAnimation(animation);
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.msg_error_login, Toast.LENGTH_SHORT)
                         .show();
                    fab.clearAnimation();
                }
            });
        }
    }
}
