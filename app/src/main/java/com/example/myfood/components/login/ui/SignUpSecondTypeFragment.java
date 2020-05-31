package com.example.myfood.components.login.ui;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myfood.R;

public class SignUpSecondTypeFragment extends Fragment implements UiContract.Fragments.SignUpSecondFragment{

    private UiContract.View onClickInterface;
    private TextView emailTextView, passwordTextView, fioTextView, inkTextView;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        onClickInterface = (UiContract.View) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up_second, container, false);

        // Находит нужные элементы UI
        Button signUp = v.findViewById(R.id.button_sign_up);
        ImageButton backArrow = v.findViewById(R.id.sign_up_back_arrow);

        final EditText emailEditText = v.findViewById(R.id.sign_up_email);
        final EditText passwordEditText = v.findViewById(R.id.sign_up_password);
        final EditText fioEditText = v.findViewById(R.id.sign_up_name);
        final EditText inkEditText = v.findViewById(R.id.sign_up_ink);

        emailTextView = v.findViewById(R.id.sign_up_email_label);
        passwordTextView = v.findViewById(R.id.sign_up_password_label);
        fioTextView = v.findViewById(R.id.sign_up_name_label);
        inkTextView = v.findViewById(R.id.sign_up_ink_label);

        // Обработчик кликов по кнопкам.
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_up_back_arrow:
                        onClickInterface.onClick(v.getId());

                        break;
                    case R.id.button_sign_up:
                        onClickInterface.onClickSignUpSecond(
                                emailEditText.getText().toString(),
                                passwordEditText.getText().toString(),
                                fioEditText.getText().toString(),
                                inkEditText.getText().toString()
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

    // Выводит строку красного цвета с заданным текстом. Используется для вывода сообщений о некоректности имени
    @Override
    public void showNameAlert(String text) {
        fioTextView.setText(text);
        fioTextView.setTextColor(Color.parseColor("#f50707"));
    }

    // Выводит строку красного цвета с заданным текстом. Используется для вывода сообщений о некоректности ИНК (индивидуального номера класса)
    @Override
    public void showInkAlert(String text) {
        inkTextView.setText(text);
        inkTextView.setTextColor(Color.parseColor("#f50707"));
    }

    // Прячет все строки красного цвета.
    @Override
    public void hideAlerts() {
        Resources resources = getResources();
        emailTextView.setText(resources.getString(R.string.your_email));
        passwordTextView.setText(resources.getString(R.string.your_password));
        inkTextView.setText(resources.getString(R.string.your_ink));
        fioTextView.setText(resources.getString(R.string.your_name));
        emailTextView.setTextColor(Color.parseColor("#000000"));
        passwordTextView.setTextColor(Color.parseColor("#000000"));
        inkTextView.setTextColor(Color.parseColor("#000000"));
        fioTextView.setTextColor(Color.parseColor("#000000"));
    }
}
