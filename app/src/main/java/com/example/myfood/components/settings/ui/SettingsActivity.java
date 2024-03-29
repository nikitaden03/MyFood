package com.example.myfood.components.settings.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfood.CheckActivity;
import com.example.myfood.R;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.settings.backstage.SettingsContract;
import com.example.myfood.components.settings.backstage.SettingsPresenter;
import com.example.myfood.data.models.User;

public class SettingsActivity extends BaseCompatActivity implements SettingsContract.View {

    EditText priceBreakfast, priceLunch, priceTeatime;
    TextView priceBreakfastText, priceLunchText, priceTeatimeText;
    SettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Это требуется, если пользователь вышел из аккаунта и случайно попал в эту activity
        checkSession();

        // Находит нужный presenter и прикрепляется к нему
        presenter = new SettingsPresenter();
        presenter.attach(this);

        // Достает данные о пользователи, которые были загружены ранее
        user = (User)getIntent().getSerializableExtra("UserClass");

        // Находит нужные элементы UI
        priceBreakfast = findViewById(R.id.settings_price_breakfast);
        priceLunch = findViewById(R.id.settings_price_lunch);
        priceTeatime = findViewById(R.id.settings_price_teatime);
        priceBreakfastText = findViewById(R.id.settings_price_breakfast_label);
        priceLunchText = findViewById(R.id.settings_price_lunch_label);
        priceTeatimeText = findViewById(R.id.settings_price_teatime_label);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        // Настраивает работу бокового меню
        installMenu();

        // Если тип аккаунта пользователя не "Дежурный", блокирует изменение цен.
        if (!user.isChargable()) {
            priceBreakfast.setEnabled(false);
            priceTeatime.setEnabled(false);
            priceLunch.setEnabled(false);
            (findViewById(R.id.settings_save_button)).setEnabled(false);
            (findViewById(R.id.settings_save_button)).setBackgroundColor(Color.parseColor("#B8A575"));
        }

        setPriceData(user);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void leaveAccount() {
        startActivity(new Intent(this, CheckActivity.class));
    }

    // Устанавливает цены на питание в нужные EditText-ы
    @Override
    public void setPriceData(User user) {
        priceBreakfast.setText(user.getPriceBreakfast() + "р");
        priceLunch.setText(user.getPriceLunch() + "р");
        priceTeatime.setText(user.getPriceTeatime() + "р");
    }

    // Передает нажатия на кнопки обработчику, который находится в presenter-e
    @Override
    public void onClick(View view) {
        presenter.onClick(view);
    }

    @Override
    public String getBreakfastPrice() {
        return priceBreakfast.getText().toString();
    }

    @Override
    public String getLunchPrice() {
        return priceLunch.getText().toString();
    }

    @Override
    public String getTeatimePrice() {
        return priceTeatime.getText().toString();
    }

    // Выводит строку красного цвета с заданным текстом. Используется для вывода сообщений о некорректности ценны
    @Override
    public void showBreakfastPriceAlert(String text) {
        priceBreakfastText.setText(text);
        priceBreakfastText.setTextColor(Color.parseColor("#f50707"));
    }

    // Выводит строку красного цвета с заданным текстом. Используется для вывода сообщений о некорректности ценны
    @Override
    public void showLunchPriceAlert(String text) {
        priceLunchText.setText(text);
        priceLunchText.setTextColor(Color.parseColor("#f50707"));
    }

    // Выводит строку красного цвета с заданным текстом. Используется для вывода сообщений о некорректности ценны
    @Override
    public void showTeatimePriceAlert(String text) {
        priceTeatimeText.setText(text);
        priceTeatimeText.setTextColor(Color.parseColor("#f50707"));
    }

    // Прячет все строки красного цвета.
    @Override
    public void hideAllAlerts() {
        Resources resources = getResources();
        priceBreakfastText.setText(resources.getString(R.string.price_breakfast_data));
        priceLunchText.setText(resources.getString(R.string.price_lunch_data));
        priceTeatimeText.setText(resources.getString(R.string.price_teatime_data));
        priceBreakfastText.setTextColor(Color.parseColor("#000000"));
        priceLunchText.setTextColor(Color.parseColor("#000000"));
        priceTeatimeText.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public User getUser() {
        return user;
    }

    // Изменяет цены на питание в инстансе класса User. Отличается от setPriceData(User user).
    @Override
    public void setPricesData(int a, int b, int c) {
        user.setPriceBreakfast(a);
        user.setPriceTeatime(b);
        user.setPriceLunch(c);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkSession();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSession();
    }

}
