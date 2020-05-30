package com.example.myfood.components.studentFood.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myfood.R;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.menu.MenuContract;
import com.example.myfood.components.menu.NavigationListener;
import com.example.myfood.components.studentFood.backstage.StudentFoodContract;
import com.example.myfood.components.studentFood.backstage.StudentFoodPresenter;
import com.example.myfood.components.studentFood.backstage.callBackinterface;
import com.example.myfood.data.models.User;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class StudentFoodActivity extends BaseCompatActivity implements StudentFoodContract.View, MenuContract, callBackinterface {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    User user;
    StudentFoodPresenter presenter;
    ImageButton back, next;
    CheckBox breakfast, teatime, lunch;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_food);

        checkSession();

        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        user = (User)getIntent().getSerializableExtra("UserClass");
        presenter = new StudentFoodPresenter();
        presenter.attach(this);

        breakfast = findViewById(R.id.food_checkbox_breakfast);
        lunch = findViewById(R.id.food_checkbox_lunch);
        teatime = findViewById(R.id.food_checkbox_teatime);
        button = findViewById(R.id.food_button_send);

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

        back = findViewById(R.id.food_previous_button);
        next = findViewById(R.id.food_next_button);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.food_next_button:
                        presenter.cursorUp();
                        break;
                    case R.id.food_previous_button:
                        presenter.cursorDown();
                        break;
                }
                setFood();
            }
        };

        back.setOnClickListener(onClickListener);
        next.setOnClickListener(onClickListener);
        presenter.preapareData();
    }

    private void setFood() {

        Calendar calendar = new GregorianCalendar();
        ((TextView) findViewById(R.id.food_data)).setText(presenter.getCursor() + "." + presenter.getCurrentMonth());

        Log.d("StudentFood", calendar.get(Calendar.HOUR) + "");

        if (calendar.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(presenter.getCursor()) && calendar.get(Calendar.HOUR_OF_DAY) < 8 && !presenter.hasTodayData()){
            button.setEnabled(true);
            breakfast.setEnabled(true);
            lunch.setEnabled(true);
            teatime.setEnabled(true);
            breakfast.setChecked(false);
            teatime.setChecked(false);
            lunch.setChecked(false);
            button.setBackgroundColor(Color.parseColor("#FFFFB803"));
        } else {
            String[] data = presenter.getData();
            button.setEnabled(false);
            breakfast.setEnabled(false);
            lunch.setEnabled(false);
            teatime.setEnabled(false);
            if (data[0].equals("1")) breakfast.setChecked(true); else breakfast.setChecked(false);
            if (data[1].equals("1")) teatime.setChecked(true); else teatime.setChecked(false);
            if (data[2].equals("1")) lunch.setChecked(true); else lunch.setChecked(false);
            button.setBackgroundColor(Color.parseColor("#B8A575"));
        }
        changeButton();
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
    public void showActivity(Class cl) {
        Intent intent = new Intent(getApplicationContext(), cl);
        intent.putExtra("UserClass", user);
        startActivity(intent);
    }

    @Override
    public void changeButton() {
        if (presenter.hasNext()) {
            next.setImageResource(R.drawable.back_next);
            next.setEnabled(true);
        } else {
            next.setImageResource(R.drawable.back_next_lite);
            next.setEnabled(false);
        }
        if (presenter.hasPrevious()) {
            back.setImageResource(R.drawable.back_previous);
            back.setEnabled(true);
        } else{
            back.setImageResource(R.drawable.back_previous_lite);
            back.setEnabled(false);
        }
    }

    public void sendFood(View view) {

        String breakfast = this.breakfast.isChecked()?"1":"0";
        String teatime = this.teatime.isChecked()?"1":"0";
        String lunch = this.lunch.isChecked()?"1":"0";
        presenter.sendFood(breakfast, teatime, lunch);

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
    public void showData() {
        setFood();
    }

    @Override
    public void afterSendingFood() {
        showActivity(StudentFoodActivity.class);
    }
}
