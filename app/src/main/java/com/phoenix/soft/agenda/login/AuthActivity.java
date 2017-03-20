package com.phoenix.soft.agenda.login;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.MainApplication;
import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.Utils;
import com.phoenix.soft.agenda.repos.FirebaseAccountRepository;

import javax.inject.Inject;

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
    @Inject
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainApplication.getFirebaseComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupSignUpText();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                fab.clearAnimation();
                Utils.viewMoveToCenter(fab, this, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) getDrawable(R.drawable.sync_to_tick);
                        if (drawable != null) {
                            fab.setImageDrawable(drawable);
                            drawable.start();
                            fab.setClickable(false);
                        }
                        new Handler(getMainLooper()).postDelayed(() -> {
                            //FirebaseAccountRepository.getObservable().subscribe(accountFire -> Log.d(TAG, "accept: "));
                            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                            startActivity(intent);
                        },250);
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

            } else {
                if (savedInstanceState == null) {
                    showLogin();
                }
            }
        };
    }

    private void showLogin() {
        FragmentManager fm = getSupportFragmentManager();
        final LoginFragment loginFragment = fm.findFragmentByTag(LoginFragment.TAG) != null ? (LoginFragment) fm
                .findFragmentByTag(LoginFragment.TAG) : LoginFragment.newInstance();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment instanceof LoginFragment && fragment.isVisible()) {
            Log.d(TAG, "showLogin: LoginFragment is shown");
        } else if (fragment == null) {
            fm.beginTransaction().add(R.id.container, loginFragment, LoginFragment.TAG).commit();
            fab.setOnClickListener(loginFragment::onFabClick);
        } else if (fragment instanceof SignUpFragment) {
            fm.beginTransaction().remove(fragment).show(loginFragment).commit();
            fab.setOnClickListener(loginFragment::onFabClick);
        }
        tvSignUp.setVisibility(View.VISIBLE);
    }

    private void showSignUp(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment instanceof LoginFragment) {
            SignUpFragment signUpFragment = SignUpFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                                       .hide(fragment)
                                       .add(R.id.container, signUpFragment, SignUpFragment.TAG)
                                       .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                       .commit();
            fab.setOnClickListener(signUpFragment::onFabClick);
        }
        tvSignUp.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void setupSignUpText() {
        String string = getString(R.string.sign_up_long);
        int start = string.indexOf("{");
        int end = string.indexOf("}") - 1;
        SpannableString ss = new SpannableString(string.replace("{", "").replace("}", ""));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                showSignUp();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSignUp.setText(ss);
        tvSignUp.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (!(fragment instanceof LoginFragment)) {
            showLogin();
        } else {
            super.onBackPressed();
        }
    }

    interface FabClick{
        void onFabClick(View v);
    }
}


