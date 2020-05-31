package com.example.myfood.components.studentFood.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myfood.R;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.studentFood.backstage.AsyncCallBack;
import com.example.myfood.components.studentFood.backstage.StudentFoodContract;
import com.example.myfood.components.studentFood.backstage.StudentFoodPresenter;
import com.example.myfood.data.models.User;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class StudentFoodActivity extends BaseCompatActivity implements StudentFoodContract.View, AsyncCallBack {

    StudentFoodPresenter presenter;
    ImageButton back, next;
    CheckBox breakfast, teatime, lunch;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_food);

        // Это требуется, если пользователь вышел из аккаунта и случайно попал в эту activity
        checkSession();

        // Достает данные о пользователе, которые были загружены ранее
        user = (User)getIntent().getSerializableExtra("UserClass");

        // Находит нужный presenter и прикрепляется к нему
        presenter = new StudentFoodPresenter();
        presenter.attach(this);

        // Находит нужные элементы UI
        breakfast = findViewById(R.id.food_checkbox_breakfast);
        lunch = findViewById(R.id.food_checkbox_lunch);
        teatime = findViewById(R.id.food_checkbox_teatime);
        button = findViewById(R.id.food_button_send);
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
                setFood();
            }
        };

        // Вешает обработчик на сами кнопки
        back.setOnClickListener(onClickListener);
        next.setOnClickListener(onClickListener);

        //Подготавливает данные для отображения
        presenter.prepareData();
    }

    // Выводит историю на экран
    private void setFood() {

        Calendar calendar = new GregorianCalendar();
        ((TextView) findViewById(R.id.food_data)).setText(presenter.getCursor() + "." + presenter.getCurrentMonthAndYear());

        // Если время до 8:00, и ученик еще не отправил заявку на питание, делает элементы кликабельными, а кнопку яркого желтого цвета
        if (calendar.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(presenter.getCursor()) && calendar.get(Calendar.HOUR_OF_DAY) < 8 && !presenter.hasTodayData()){
            button.setEnabled(true);
            breakfast.setEnabled(true);
            lunch.setEnabled(true);
            teatime.setEnabled(true);
            breakfast.setChecked(false);
            teatime.setChecked(false);
            lunch.setChecked(false);
            button.setBackgroundColor(Color.parseColor("#FFFFB803"));

        // В ином случае элементы не кликабельны, кнопка бежевого цвета, и выводит историю (ставит галочки на чекбоксах, которые соответствуют пунктам, которые пользователь выбрал в прошлом.
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

    // Превращает выбранные элементы в 0 и 1. (Допустим, если пользователь выбрал обед и полдник, будет 0, 1, 1)
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
    public  void setData(LinkedList<String[]> treeMaps){
        presenter.setData(treeMaps);
    }

    @Override
    public void afterSendingFood() {
        showActivity(StudentFoodActivity.class);
    }
}
