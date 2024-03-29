package com.example.myfood.components.myClassInfo.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfood.R;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.myClassInfo.backstage.AsyncCallBack;
import com.example.myfood.components.myClassInfo.backstage.MyClassContract;
import com.example.myfood.components.myClassInfo.backstage.MyClassPresenter;
import com.example.myfood.data.models.User;

import java.util.ArrayList;

public class MyClassActivity extends BaseCompatActivity implements MyClassContract.View, AsyncCallBack {

    MyClassPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);

        // Это требуется, если пользователь вышел из аккаунта и случайно попал в эту activity
        checkSession();

        // Достает данные о пользователи, которые были загружены ранее
        user = (User)getIntent().getSerializableExtra("UserClass");

        // Находит нужный presenter и прикрепляется к нему
        presenter = new MyClassPresenter();
        presenter.attach(this);

        // Выводит название школы и номер класса
        ((TextView)findViewById(R.id.classInfo_label)).setText("Школа " + user.getSchool() + ", " + user.getNumberClass());

        // Находит нужные элементы UI
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        // Настраивает работу бокового меню
        installMenu();

        presenter.prepareInformMyClass(user);
    }

    // Заполняет ListView данными, которые были получены из presenter-a
    @Override
    public void showMyClass(ArrayList<String> classmates) {
        ListView listView = findViewById(R.id.classInfo_list_view);
        String name;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.my_class_list, classmates);
        ((TextView) findViewById(R.id.food_count)).setText("ВСЕГО: " + classmates.size() + " УЧЕНИКОВ");
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
    public void returnClassmates(ArrayList<String> classmates) {
        showMyClass(classmates);
    }
}
