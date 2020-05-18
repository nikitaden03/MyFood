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
import com.example.myfood.components.myClassInfo.backstage.AsyncCallBack;
import com.example.myfood.components.myClassInfo.backstage.MyClassContract;
import com.example.myfood.components.myClassInfo.backstage.MyClassPresenter;
import com.example.myfood.data.models.Classmate;
import com.example.myfood.data.models.User;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MyClassActivity extends BaseCompatActivity implements MyClassContract.View, MenuContract, AsyncCallBack {

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
        presenter.prepareInformMyClass(user);
    }

    public void showMyClass2(ArrayList<Classmate> classmates) {
        ListView listView = findViewById(R.id.classInfo_list_view);
        String name;
        ArrayList<String> arrayList1 = new ArrayList<>();
        for (Classmate classmate: classmates) {
            name = classmate.getName();
            arrayList1.add(name);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.my_class_list, arrayList1);
        ((TextView) findViewById(R.id.food_count)).setText("ВСЕГО: " + arrayList1.size() + " УЧЕНИКОВ");
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

    @Override
    public void reternClassmates(ArrayList<Classmate> classmates) {
        showMyClass2(classmates);
    }
}
