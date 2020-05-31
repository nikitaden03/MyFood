package com.example.myfood.components.login.ui;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myfood.R;

public class SignUpFirstTypeFragment extends Fragment implements UiContract.Fragments.SignUpFirstFragment{

    private UiContract.View onClickInterface;
    private TextView emailTextView, passwordTextView, schoolTextView, fioTextView, numberClassTextView;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        onClickInterface = (UiContract.View) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up_first_type, container, false);

        // Находит нужные элементы UI
        Button signUp = v.findViewById(R.id.button_sign_up);
        ImageButton backArrow = v.findViewById(R.id.sign_up_back_arrow);

        final EditText emailEditText = v.findViewById(R.id.sign_up_email);
        final EditText passwordEditText = v.findViewById(R.id.sign_up_password);
        final EditText schoolEditText = v.findViewById(R.id.sign_up_school);
        final EditText fioEditText = v.findViewById(R.id.sign_up_name);
        final EditText numberClassEditText = v.findViewById(R.id.sign_up_class);

        emailTextView = v.findViewById(R.id.sign_up_email_label);
        passwordTextView = v.findViewById(R.id.sign_up_password_label);
        schoolTextView = v.findViewById(R.id.sign_up_school_label);
        fioTextView = v.findViewById(R.id.sign_up_name_label);
        numberClassTextView = v.findViewById(R.id.sign_up_class_label);

        // Обработчик кликов по кнопкам.
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_up_back_arrow:
                        onClickInterface.onClick(v.getId());

                        break;
                    case R.id.button_sign_up:
                        onClickInterface.onClickSignUpFirst(
                                emailEditText.getText().toString(),
                                passwordEditText.getText().toString(),
                                schoolEditText.getText().toString(),
                                fioEditText.getText().toString(),
                                numberClassEditText.getText().toString()

                        );
                        break;
                }
            }
        };

        // Вешает обработчик на сами кнопки
        signUp.setOnClickListener(onClickListener);
        backArrow.setOnClickListener(onClickListener);
        return v;
    }

    // Выводит строку красного цвета с заданным текстом. Используется для вывода сообщений о некоректности email
    @Override
    public void showEmailAlert(String text) {
        emailTextView.setText(text);
        emailTextView.setTextColor(Color.parseColor("#f50707"));
    }

    // Выводит строку красного цвета с заданным текстом. Используется для вывода сообщений о некоректности пароля
    @Override
    public void showPasswordAlert(String text) {
        passwordTextView.setText(text);
        passwordTextView.setTextColor(Color.parseColor("#f50707"));
    }

    // Выводит строку красного цвета с заданным текстом. Используется для вывода сообщений о некоректности номера школы
    @Override
    public void showSchoolAlert(String text) {
        schoolTextView.setText(text);
        schoolTextView.setTextColor(Color.parseColor("#f50707"));
    }

    // Выводит строку красного цвета с заданным текстом. Используется для вывода сообщений о некоректности имени
    @Override
    public void showNameAlert(String text) {
        fioTextView.setText(text);
        fioTextView.setTextColor(Color.parseColor("#f50707"));
    }

    // Выводит строку красного цвета с заданным текстом. Используется для вывода сообщений о некоректности номера класса
    @Override
    public void showClassAlert(String text) {
        numberClassTextView.setText(text);
        numberClassTextView.setTextColor(Color.parseColor("#f50707"));
    }

    // Прячет все строки красного цвета.
    @Override
    public void hideAlerts() {
        Resources resources = getResources();
        emailTextView.setText(resources.getString(R.string.your_email));
        passwordTextView.setText(resources.getString(R.string.your_password));
        schoolTextView.setText(resources.getString(R.string.your_school));
        fioTextView.setText(resources.getString(R.string.your_name));
        numberClassTextView.setText(resources.getString(R.string.your_class));
        emailTextView.setTextColor(Color.parseColor("#000000"));
        passwordTextView.setTextColor(Color.parseColor("#000000"));
        schoolTextView.setTextColor(Color.parseColor("#000000"));
        fioTextView.setTextColor(Color.parseColor("#000000"));
        numberClassTextView.setTextColor(Color.parseColor("#000000"));
    }
}
