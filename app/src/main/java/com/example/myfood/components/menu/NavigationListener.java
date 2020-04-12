package com.example.myfood.components.menu;

import android.content.Context;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myfood.R;
import com.example.myfood.components.mainScreen.ui.MainScreenActivity;
import com.example.myfood.components.myClassInfo.ui.MyClassActivity;
import com.example.myfood.components.myINK.ui.MyInkActivity;
import com.example.myfood.components.settings.ui.SettingsActivity;
import com.example.myfood.components.studentFood.ui.StudentFoodActivity;
import com.example.myfood.components.teacherFood.ui.TeacherFoodActivity;

public class NavigationListener {

    MenuContract view;

    public NavigationListener(MenuContract view) {
        this.view = view;
    }

    public void onNavigationItemSelected(MenuItem menuItem, Context context) {
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
