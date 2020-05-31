package com.example.myfood.components.myINK.ui;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfood.R;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.menu.MenuContract;
import com.example.myfood.components.menu.NavigationListener;
import com.example.myfood.components.myINK.backstage.MyInkContract;
import com.example.myfood.components.myINK.backstage.MyInkPresenter;
import com.example.myfood.data.models.User;
import com.google.android.material.navigation.NavigationView;

public class MyInkActivity extends BaseCompatActivity implements MyInkContract.View{

    MyInkPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ink);

        // Это требуется, если пользователь вышел из аккаунта и случайно попал в эту activity
        checkSession();

        // Находит нужный presenter и прикрепляется к нему
        presenter= new MyInkPresenter();
        presenter.attach(this);

        // Достает данные о пользователи, которые были загружены ранее
        user = (User)getIntent().getSerializableExtra("UserClass");

        // Находит нужные элементы UI
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        // Настраивает работу бокового меню
        installMenu();

        // Вешает слушатель на кнопку "Скопировать"
        Button toCopy = findViewById(R.id.my_ink_button);
        toCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.toCopy(Integer.toString(user.getINK()));
            }
        });

        // В зависимости от факта заполнености цен на питание показывает ИНК, либо нет
        if (user.getPriceBreakfast() != 0 && user.getPriceLunch() != 0 && user.getPriceTeatime() != 0) {
            ((TextView) findViewById(R.id.ink_label)).setText(Integer.toString(user.getINK()));
        } else {
            ((TextView) findViewById(R.id.ink_label)).setText(getResources().getString(R.string.ink_empty));
            ((TextView) findViewById(R.id.ink_label)).setTextSize(24);
            toCopy.setEnabled(false);
        }
    }


    @Override
    public void showToast() {
        Toast.makeText(this, getResources().getString(R.string.copy_successful), Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return this;
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
