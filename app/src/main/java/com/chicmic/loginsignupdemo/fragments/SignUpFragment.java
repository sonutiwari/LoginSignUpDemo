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


public class SignUpFragment extends Fragment {

    private SignUpFragmentListenerInterface mListener;
    private AutoCompleteTextView mEmailAutoCompleteTextView;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private String email;
    private String mPassword;
    private String mConfirmPassword;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        if (pSavedInstanceState == null){
            email = "";
            mPassword = "";
            mConfirmPassword = "";
        } else {
            email = pSavedInstanceState.getString(AppConstants.EMAIL_STRING);
            mPassword = pSavedInstanceState.getString(AppConstants.PASSWORD_STRING);
            mConfirmPassword = pSavedInstanceState.getString(AppConstants.CONFIRM_PASSWORD_STRING);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater pInflater, ViewGroup pContainer,
                             Bundle pSavedInstanceState) {

        mListener.enableBackNavigation();

        // Inflate the layout for this fragment
        View view = pInflater.inflate(R.layout.fragment_sign_up, pContainer, false);

        // Getting references of sign in and sign up buttons.
        Button signUpButton = view.findViewById(R.id.email_sign_up_button);

        // Getting reference of email AutoCompleteTextViews.
        mEmailAutoCompleteTextView = view.findViewById(R.id.email);
        mPasswordEditText = view.findViewById(R.id.password);
        mConfirmPasswordEditText = view.findViewById(R.id.confirm_password);


        if (pSavedInstanceState != null){
            mEmailAutoCompleteTextView.setText(email);
            mPasswordEditText.setText(mPassword);
            mConfirmPasswordEditText.setText(mConfirmPassword);
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmailAutoCompleteTextView.getText().toString().trim();
                mPassword = mPasswordEditText.getText().toString().trim();
                if (isEmailValid(email) && mPassword.length() > 5
                        && mPassword.equals(mConfirmPasswordEditText.getText().toString().trim())) {
                    mListener.onSignUpButtonClick();
                } else if(! isEmailValid(email)){
                    mEmailAutoCompleteTextView.setError(getString(R.string.email_not_valid));
                    mEmailAutoCompleteTextView.requestFocus(0);
                } else if (mPassword.length() <= 5){
                    mPasswordEditText
                            .setError(getString(R.string.password_less_than_required));
                    mPasswordEditText.requestFocus(mPassword.length());
                } else {
                    mConfirmPasswordEditText.setError(getString(R.string.password_did_not_match));
                    mConfirmPasswordEditText
                            .requestFocus(mConfirmPasswordEditText.getText().toString().length());
                }
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        if (email == null){
            email = "";
        }
        if (mPassword == null){
            mPassword = "";
        }
        if (mConfirmPassword == null){
            mConfirmPassword = "";
        }
        pOutState.putString(AppConstants.EMAIL_STRING, email);
        pOutState.putString(AppConstants.PASSWORD_STRING, mPassword);
        pOutState.putString(AppConstants.CONFIRM_PASSWORD_STRING, mConfirmPassword);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Context pContext) {
        super.onAttach(pContext);
        if (pContext instanceof LoginFragmentListenerInterface) {
            mListener = (SignUpFragmentListenerInterface) pContext;
        } else {
            throw new RuntimeException(pContext.toString()
                    + getString(R.string.must_implement_fragment));
        }
    }



    /**
     * method is used for checking valid email id format.
     *
     * @param pEmail the input in email field.
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
