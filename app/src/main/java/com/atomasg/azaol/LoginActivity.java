package com.atomasg.azaol;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int REQUEST_READ_CONTACTS = 0;

    @BindView(R.id.tvEmail)
    public AutoCompleteTextView tvEmail;

    @BindView(R.id.tvPassword)
    public EditText tvPassword;

    private FirebaseAuth mAuth;
    private Navigator navigator;


    // UI references.
    @BindView(R.id.login_progress)
    View mProgressView;

    @BindView(R.id.email_login_form)
    View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        getSupportActionBar().hide();

        navigator = new Navigator(this);

        mAuth = FirebaseAuth.getInstance();

    }


    @OnClick(R.id.email_sign_in_button)
    public void attemptLogin() {

        // Reset errors.
        tvEmail.setError(null);
        tvPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = tvEmail.getText().toString();
        String password = tvPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        boolean b= TextUtils.isEmpty(password);


        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            tvPassword.setError(getString(R.string.error_invalid_password));
            focusView = tvPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            tvEmail.setError(getString(R.string.error_field_required));
            focusView = tvEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            tvEmail.setError(getString(R.string.error_invalid_email));
            focusView = tvEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuth.signInWithEmailAndPassword(email, password)       //todo check pass
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                navigator.goToMenu();
                                showProgress(false);

                            } else {

                                showProgress(false);
                                Toast.makeText(LoginActivity.this, "No se ha podido hacer Login.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}

