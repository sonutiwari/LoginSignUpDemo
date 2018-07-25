package com.chicmic.loginsignupdemo.interfaces;

import com.chicmic.loginsignupdemo.fragments.SignUpFragment;

public interface LoginFragmentListenerInterface {
    void onSignInClick();
    void startSignUpFragment(SignUpFragment signUpFragment);
    void onSignUpButtonClick();
    void enableBackNavigation();
    void disableBackNavigation();
}
