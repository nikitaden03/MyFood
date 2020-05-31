package com.example.myfood.components.teacherFood.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myfood.R;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.menu.NavigationListener;
import com.example.myfood.components.teacherFood.backstage.TeacherFoodContract;
import com.example.myfood.components.teacherFood.backstage.TeacherFoodPresenter;
import com.example.myfood.components.teacherFood.backstage.AsyncCallBack;
import com.example.myfood.data.models.User;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class TeacherFoodActivity extends BaseCompatActivity implements TeacherFoodContract.View, AsyncCallBack {

    ListView listView;
    TeacherFoodPresenter presenter;
    ImageButton back, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_food);

        // Это требуется, если пользователь вышел из аккаунта и случайно попал в эту activity
        checkSession();

        // Достает данные о пользователе, которые были загружены ранее
        user = (User)getIntent().getSerializableExtra("UserClass");

        // Находит нужный presenter и прикрепляется к нему
        presenter = new TeacherFoodPresenter();
        presenter.attach(this);

        // Находит нужные элементы UI
        listView = findViewById(R.id.food_list_view);
        back = findViewById(R.id.food_previous_button);
        next = findViewById(R.id.food_next_button);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        // Настраивает работу бокового меню
        installMenu();

        // Обработчик кликов по кнопкам для просмотра истории
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
                setFoodList();
            }
        };

        // Вешает обработчик на сами кнопки
        back.setOnClickListener(onClickListener);
        next.setOnClickListener(onClickListener);

        //Подготавливает данные для отображения
        presenter.prepareData();
    }

    @Override
    public Context getContext() {
        return this;
    }

    // Выводит историю на экран
    @Override
    public void setFoodList() {
        ArrayList<String[]> data = presenter.getData();
        ((TextView) findViewById(R.id.food_data)).setText(presenter.getCursor() + "." + presenter.getCurrentMonthAndYear());

        // Если массив пустой (до 8:00)
        if (data.get(0)[0].equals("0")) {
            listView.setVisibility(View.GONE);
            findViewById(R.id.food_alert).setVisibility(View.VISIBLE);
            findViewById(R.id.food_count).setVisibility(View.GONE);
            findViewById(R.id.food_line).setVisibility(View.GONE);
        } else {
            // Если массив пустой (никто не подал заявку)
            if (data.get(0)[0].equals("5")) data = new ArrayList<>();
            listView.setVisibility(View.VISIBLE);
            findViewById(R.id.food_alert).setVisibility(View.GONE);
            MyAdapter myAdapter = new MyAdapter(getApplicationContext(), R.layout.teacher_food_item, data);
            listView.setAdapter(myAdapter);
            ((TextView) findViewById(R.id.food_count)).setText("З - " + presenter.getBreakfast() + "; П - " + presenter.getTeatime() + "; O - " + presenter.getLunch());
            findViewById(R.id.food_count).setVisibility(View.VISIBLE);
            findViewById(R.id.food_line).setVisibility(View.VISIBLE);
        }
        changeButton();
    }

    // Меняет цвет стрелки на серый, если история закончилось. И наоборот.
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
        setFoodList();
    }

    class MyAdapter extends ArrayAdapter<String[]> {

        public MyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String[]> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView =  LayoutInflater.from(getContext()).inflate(R.layout.teacher_food_item, null);
            }
            String[] item = getItem(position);
            String secondName = item[0].split(" ")[1];
            String answer = secondName + ": ";
            if (item[1].equals("1")) {
                answer += "завтрак";
            }
            if ((item[2].equals("1") || item[3].equals("1")) && item[1].equals("1")) answer += ", ";
            if (item[2].equals("1")) {
                answer += "полдник";
            }
            if (item[3].equals("1") && item[2].equals("1")) answer += ", ";
            if (item[3].equals("1")) {
                answer += "обед";
            }
            if (item[1].equals("0") && item[2].equals("0") && item[3].equals("0")) answer += "ничего";
            ((TextView)convertView.findViewById(R.id.food_item)).setText(answer);
            return convertView;
        }

    }
}
