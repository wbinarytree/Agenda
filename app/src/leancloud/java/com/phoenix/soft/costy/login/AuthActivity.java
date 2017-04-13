package com.phoenix.soft.costy.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.phoenix.soft.costy.MainApplication;
import com.phoenix.soft.costy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "AuthActivity";
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.login_background)
    View root;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainApplication.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (getIntent().getBooleanExtra("signOut", false)) {
            //sign out
        }
        fab.hide();
        tvSignUp.setVisibility(View.GONE);
    }

    interface FabClick {
        void onFabClick(View v);
    }
}


