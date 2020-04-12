package com.example.myfood.components.myINK.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.myfood.data.User;
import com.google.android.material.navigation.NavigationView;

public class MyInkActivity extends BaseCompatActivity implements MyInkContract.View, MenuContract {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    User user;
    MyInkPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ink);

        checkSession();

        presenter= new MyInkPresenter();
        presenter.attach(this);

        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        user = (User)getIntent().getSerializableExtra("UserClass");
        final NavigationListener navigationListener = new NavigationListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigationListener.onNavigationItemSelected(item, getApplicationContext());
                return false;
            }
        });

        if (user.isChargable()) {
            ((NavigationView)findViewById(R.id.navigationView)).inflateMenu(R.menu.drawer_menu_first_type);
        } else {
            ((NavigationView)findViewById(R.id.navigationView)).inflateMenu(R.menu.drawer_menu_second_type);
        }

        Button toCopy = findViewById(R.id.my_ink_button);
        toCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.toCopy(Integer.toString(user.getINK()));
            }
        });

        if (user.getINK() != 0) {
            ((TextView) findViewById(R.id.ink_label)).setText(Integer.toString(user.getINK()));
        } else {
            ((TextView) findViewById(R.id.ink_label)).setText(getResources().getString(R.string.ink_empty));
            ((TextView) findViewById(R.id.ink_label)).setTextSize(24);
            toCopy.setEnabled(false);
        }
    }

    @Override
    public void openMenu(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
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
