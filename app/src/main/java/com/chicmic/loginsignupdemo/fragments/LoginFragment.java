package com.chicmic.loginsignupdemo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.chicmic.loginsignupdemo.R;
import com.chicmic.loginsignupdemo.interfaces.LoginFragmentListenerInterface;
import com.chicmic.loginsignupdemo.interfaces.SignUpFragmentListenerInterface;
import com.chicmic.loginsignupdemo.utilities.AppConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginFragment extends Fragment
        implements SignUpFragmentListenerInterface {

    // Declaring global objects.
    private SignUpFragment mSignUpFragment = SignUpFragment.newInstance();
    private LoginFragmentListenerInterface mListener;
    private AutoCompleteTextView mEmailAutoCompleteTextView;
    private EditText mPasswordEditText;
    private String mEmail;
    private String mPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            mEmail = "";
            mPassword = "";
        } else {
            mEmail = savedInstanceState.getString(AppConstants.EMAIL_STRING);
            mPassword = savedInstanceState.getString(AppConstants.PASSWORD_STRING);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater mInflater, ViewGroup mContainer,
                             Bundle mSavedInstanceState) {
        // Disabling back navigation.
        mListener.disableBackNavigation();

        // Inflate the layout for this fragment
        View view = mInflater.inflate(R.layout.fragment_login, mContainer, false);

        // Getting references of sign in and sign up buttons.
        Button loginButton = view.findViewById(R.id.email_sign_in_button);
        Button signUpButton = view.findViewById(R.id.email_sign_up_button);

        // Getting reference of email AutoCompleteTextViews.
        mEmailAutoCompleteTextView = view.findViewById(R.id.email);
        mPasswordEditText = view.findViewById(R.id.password);


        if (mSavedInstanceState != null){
            mEmailAutoCompleteTextView.setText(mEmail);
            mPasswordEditText.setText(mPassword);
        }

        // Setting touch listener on sign in button.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = mEmailAutoCompleteTextView.getText().toString().trim();
                mPassword = mPasswordEditText.getText().toString().trim();

                if (isEmailValid(mEmail) && mPassword.length() > 5) {
                    mListener.onSignInClick();
                } else if (! isEmailValid(mEmail)){
                    mEmailAutoCompleteTextView
                            .setError(getResources().getString(R.string.email_not_valid));
                    mPasswordEditText.clearFocus();
                    if(mEmail == null){
                        mEmailAutoCompleteTextView.requestFocus(0);
                    } else {
                        mEmailAutoCompleteTextView.requestFocus(mEmail.length());
                    }
                } else if (mPassword.length() < 6){
                    mPasswordEditText
                            .setError(getResources()
                                    .getString(R.string.password_less_than_required));
                    mEmailAutoCompleteTextView.clearFocus();
                    if (mPassword == null) {
                        mPasswordEditText.requestFocus(0);
                    } else {
                        mPasswordEditText.requestFocus(mPassword.length());
                    }
                }
            }
        });

        // Setting touch listener on sign up button.
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling function to start new fragment.
                mListener.startSignUpFragment(mSignUpFragment);
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        if (mEmail == null){
            mEmail = "";
        }
        if (mPassword == null){
            mPassword = "";
        }
        pOutState.putString(AppConstants.EMAIL_STRING, mEmail);
        pOutState.putString(AppConstants.PASSWORD_STRING, mPassword);
    }


    @Override
    public void onAttach(Context pContext) {
        super.onAttach(pContext);
        if (pContext instanceof LoginFragmentListenerInterface) {
            mListener = (LoginFragmentListenerInterface) pContext;
        } else {
            throw new RuntimeException(pContext.toString()
                    + R.string.must_implement_fragment);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSignUpButtonClick() {
        mListener.onSignUpButtonClick();
    }

    @Override
    public void enableBackNavigation() {
        mListener.enableBackNavigation();
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param pEmail email input
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String pEmail) {
        if (pEmail == null){
            return false;
        }
        String expression = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pEmail);
        return matcher.matches();
    }

}
