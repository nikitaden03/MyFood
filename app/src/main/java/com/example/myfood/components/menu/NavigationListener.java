package com.example.myfood.components.menu;

import android.view.MenuItem;

import com.example.myfood.R;
import com.example.myfood.components.mainScreen.ui.MainScreenActivity;
import com.example.myfood.components.myClassInfo.ui.MyClassActivity;
import com.example.myfood.components.myINK.ui.MyInkActivity;
import com.example.myfood.components.settings.ui.SettingsActivity;
import com.example.myfood.components.studentFood.ui.StudentFoodActivity;
import com.example.myfood.components.teacherFood.ui.TeacherFoodActivity;

public class NavigationListener {

    private MenuContract view;

    public NavigationListener(MenuContract view) {
        this.view = view;
    }

    // Обрабатывает нажатия на пункты в боковом меню
    public void onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_main:
                view.showActivity(MainScreenActivity.class);
                break;
            case R.id.menu_request_student:
                view.showActivity(TeacherFoodActivity.class);
                break;
            case R.id.menu_class_inform:
                view.showActivity(MyClassActivity.class);
                break;
            case R.id.menu_INK:
                view.showActivity(MyInkActivity.class);
                break;
            case R.id.menu_setting:
                view.showActivity(SettingsActivity.class);
                break;
            case R.id.menu_my_food:
                view.showActivity(StudentFoodActivity.class);
                break;
        }
    }
}
