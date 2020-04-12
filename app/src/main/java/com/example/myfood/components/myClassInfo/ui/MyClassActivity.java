package com.example.myfood.components.myClassInfo.ui;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfood.R;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.menu.MenuContract;
import com.example.myfood.components.menu.NavigationListener;
import com.example.myfood.components.myClassInfo.backstage.MyClassContract;
import com.example.myfood.components.myClassInfo.backstage.MyClassPresenter;
import com.example.myfood.data.User;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MyClassActivity extends BaseCompatActivity implements MyClassContract.View, MenuContract {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    User user;
    MyClassPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);

        checkSession();

        presenter = new MyClassPresenter();
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

        ((TextView)findViewById(R.id.classInfo_label)).setText("Школа " + user.getSchool() + ", " + user.getNumberClass());

        if (user.isChargable()) {
            ((NavigationView)findViewById(R.id.navigationView)).inflateMenu(R.menu.drawer_menu_first_type);
        } else {
            ((NavigationView)findViewById(R.id.navigationView)).inflateMenu(R.menu.drawer_menu_second_type);
        }
        showMyClass();
    }

    @Override
    public void showActivity(Class cl) {
        Intent intent = new Intent(getApplicationContext(), cl);
        intent.putExtra("UserClass", user);
        startActivity(intent);
    }

    @Override
    public void openMenu(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void showMyClass() {
        ListView listView = findViewById(R.id.classInfo_list_view);
        ArrayList<String> arrayList = presenter.getInformMyClass(user);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.my_class_list, arrayList);
        ((TextView) findViewById(R.id.food_count)).setText("ВСЕГО: " + arrayList.size() + " УЧЕНИКОВ");
        listView.setAdapter(arrayAdapter);
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
