package com.phoenix.soft.agenda.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.module.AccountFire;
import com.phoenix.soft.agenda.repos.AccountRepository;
import com.phoenix.soft.agenda.repos.TestAccountRepository;

import org.joda.money.Money;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @BindView(R.id.email)
    AutoCompleteTextView etUsername;
    @BindView(R.id.password)
    EditText etPassword;
    @BindView(R.id.login_progress)
    View vProgress;
    @BindView(R.id.login_form)
    View vLoginForm;
    @BindView(R.id.email_sign_in_button)
    Button btnSignIn;
    AccountRepository repository = new TestAccountRepository(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                databaseReference.child("Account").setValue(new AccountFire("BankAccount","www.google.com",1));
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
            // ...
        };
        etPassword.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        btnSignIn.setOnClickListener(view -> SignUp(etUsername.getText().toString(),etPassword.getText().toString()));

        vLoginForm = findViewById(R.id.login_form);
        vProgress = findViewById(R.id.login_progress);
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

    private void SignUp(String email,String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void attemptLogin() {

        // Reset errors.
        etUsername.setError(null);
        etPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        // Check for a valid email address.
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
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

