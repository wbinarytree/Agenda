package com.phoenix.soft.agenda.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.MainApplication;
import com.phoenix.soft.agenda.R;

import javax.inject.Inject;

/**
 * A login screen that offers login via email/password.
 */
public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "AuthActivity";
    @Inject
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainApplication.getFirebaseComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
//                databaseReference.child("Account")
//                                 .setValue(new AccountFire("BankAccount", "www.google.com", 1));
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.container, new LoginFragment())
                                   .commit();
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

//    private void SignUp() {
//
//        // Reset errors.
//        etUsername.setError(null);
//        etPassword.setError(null);
//
//        // Store values at the time of the login attempt.
//        String email = etUsername.getText().toString();
//        String password = etPassword.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            etPassword.setError(getString(R.string.error_invalid_password));
//            focusView = etPassword;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            etUsername.setError(getString(R.string.error_field_required));
//            focusView = etUsername;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            etUsername.setError(getString(R.string.error_invalid_email));
//            focusView = etUsername;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            firebaseAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(this, task -> {
//                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
//                            if (!task.isSuccessful()) {
//                                Toast.makeText(AuthActivity.this, "Failed", Toast.LENGTH_SHORT)
//                                     .show();
//                            }
//                        });
//        }
}



