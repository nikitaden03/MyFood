package com.example.myfood.components.mainScreen.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myfood.R;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.mainScreen.backstage.MainContract;
import com.example.myfood.components.mainScreen.backstage.MainPresenter;
import com.example.myfood.data.models.User;

public class MainScreenActivity extends BaseCompatActivity implements MainContract.View {

    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // Это требуется, если пользователь вышел из аккаунта и случайно попал в эту activity
        checkSession();

        // Достает данные о пользователи, которые были загружены ранее
        user = (User) getIntent().getSerializableExtra("UserClass");

        // Находит нужный presenter и прикрепляется к нему
        presenter = new MainPresenter();
        presenter.attach(this);

        // Находит нужные элемменты UI
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        // Настраивает работу бокового меню
        installMenu();

        setUserData(user);
    }

    @Override
    public Context getContext() {
        return this;
    }

    // Выводит данные пользователя на экран
    @Override
    public void setUserData(User user) {
        ((TextView)findViewById(R.id.main_name)).setText(user.getName());
        ((TextView)findViewById(R.id.main_school)).setText(user.getSchool());
        ((TextView)findViewById(R.id.main_class)).setText(user.getNumberClass());
        ((TextView)findViewById(R.id.main_price_breakfast)).setText(user.getPriceBreakfast() + "р");
        ((TextView)findViewById(R.id.main_price_teatime)).setText(user.getPriceTeatime() + "р");
        ((TextView)findViewById(R.id.main_price_lunch)).setText(user.getPriceLunch() + "р");
        if (user.getINK() != 0) {
            ((TextView) findViewById(R.id.main_INK)).setText(Integer.toString(user.getINK()));
        } else {
            ((TextView) findViewById(R.id.main_INK)).setText("Отсутствует");
        }
        if (user.isChargable()) {
            ((TextView)findViewById(R.id.main_type_account)).setText("Дежурный");
        } else {
            ((TextView)findViewById(R.id.main_type_account)).setText("Питающийся");
        }
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
