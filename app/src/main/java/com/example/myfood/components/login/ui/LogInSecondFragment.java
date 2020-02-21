package com.example.myfood.components.login.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myfood.R;


public class LogInSecondFragment extends Fragment implements UiContract.Fragments.LoginFragment{

    private UiContract.View onClickInterface;
    private TextView emailTextView, passwordTextView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onClickInterface = (UiContract.View) activity;
    }

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View v = inflater.inflate(R.layout.fragment_login_second, container, false);

        Button login = v.findViewById(R.id.button_login);
        ImageButton backArrow = v.findViewById(R.id.login_back_arrow);
        final EditText emailEditText = v.findViewById(R.id.login_email);
        final EditText passwordEditText = v.findViewById(R.id.login_password);
        emailTextView = v.findViewById(R.id.login_email_alert);
        passwordTextView = v.findViewById(R.id.login_password_alert);

        android.view.View.OnClickListener onClickListener = new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                switch (v.getId()) {
                    case R.id.login_back_arrow:
                        onClickInterface.onClick(v.getId());
                        break;
                    case R.id.button_login:
                        onClickInterface.onClickLogin(emailEditText.getText().toString(), passwordEditText.getText().toString());
                        break;
                }
            }
        };

        login.setOnClickListener(onClickListener);
        backArrow.setOnClickListener(onClickListener);

        return v;
    }

    @Override
    public void showEmailAlert(String text) {
        emailTextView.setText(text);
        emailTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPasswordAlert(String text) {
        passwordTextView.setText(text);
        passwordTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAlerts() {
        emailTextView.setVisibility(View.GONE);
        passwordTextView.setVisibility(View.GONE);
    }
}
