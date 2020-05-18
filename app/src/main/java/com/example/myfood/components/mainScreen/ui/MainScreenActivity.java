package com.example.myfood.components.mainScreen.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myfood.R;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.menu.MenuContract;
import com.example.myfood.components.menu.NavigationListener;
import com.example.myfood.components.mainScreen.backstage.MainContract;
import com.example.myfood.components.mainScreen.backstage.MainPresenter;
import com.example.myfood.data.models.User;
import com.google.android.material.navigation.NavigationView;

public class MainScreenActivity extends BaseCompatActivity implements MainContract.View, MenuContract {

    MainPresenter presenter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        checkSession();

        user = (User) getIntent().getSerializableExtra("UserClass");
        Log.d("MYTAG", user.getName());
        presenter = new MainPresenter();
        presenter.attach(this);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        final NavigationListener navigationListener = new NavigationListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigationListener.onNavigationItemSelected(item, getApplicationContext());
                return false;
            }
        });
        setUserData(user);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void openMenu(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

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
            ((NavigationView)findViewById(R.id.navigationView)).inflateMenu(R.menu.drawer_menu_first_type);
        } else {
            ((TextView)findViewById(R.id.main_type_account)).setText("Питающийся");
            ((NavigationView)findViewById(R.id.navigationView)).inflateMenu(R.menu.drawer_menu_second_type);
        }
    }

    @Override
    public void showActivity(Class cl) {
        Intent intent = new Intent(getApplicationContext(), cl);
        intent.putExtra("UserClass", user);
        startActivity(intent);
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
