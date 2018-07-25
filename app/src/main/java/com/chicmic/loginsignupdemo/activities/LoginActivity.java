package com.chicmic.loginsignupdemo.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.chicmic.loginsignupdemo.fragments.LoginFragment;
import com.chicmic.loginsignupdemo.R;
import com.chicmic.loginsignupdemo.fragments.SignUpFragment;
import com.chicmic.loginsignupdemo.interfaces.LoginFragmentListenerInterface;
import com.chicmic.loginsignupdemo.interfaces.SignUpFragmentListenerInterface;
import com.chicmic.loginsignupdemo.utilities.AppConstants;

public class LoginActivity extends AppCompatActivity
        implements LoginFragmentListenerInterface
        , SignUpFragmentListenerInterface {

    private LoginFragment mLoginFragment = LoginFragment.newInstance();

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_login);

        // Setting title of the page.
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.action_bar);
        }

        if (pSavedInstanceState == null){

            // Getting fragment manager to call fragment transactions.
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.login_frame, mLoginFragment, AppConstants.LOGIN_FRAGMENT_TAG)
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle pSavedState) {
        super.onSaveInstanceState(pSavedState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(pItem);
    }

    @Override
    public void onSignInClick() {
        Toast.makeText(this, R.string.sign_in_completed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startSignUpFragment(SignUpFragment pSignUpFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.login_frame, pSignUpFragment, AppConstants.SIGN_UP_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onSignUpButtonClick() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void enableBackNavigation() {
        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void disableBackNavigation() {
        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}

