package com.example.myfood.components.login.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfood.R;

public class LoginFirstFragment extends Fragment {

    private UiContract.View onClickInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onClickInterface = (UiContract.View) activity;
    }

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View v = inflater.inflate(R.layout.fragment_login_first, container, false);

        // Находит нужные элементы UI
        Button buttonSignUp = v.findViewById(R.id.button_to_choose_fragment);
        Button buttonLogIn = v.findViewById(R.id.button_to_login_fragment);

        // Обработчик кликов по кнопкам.
        android.view.View.OnClickListener onClickListener = new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                int buttonId = v.getId();
                onClickInterface.onClick(buttonId);
            }
        };

        // Вешает обработчик на сами кнопки
        buttonLogIn.setOnClickListener(onClickListener);
        buttonSignUp.setOnClickListener(onClickListener);
        return v;
    }

}
