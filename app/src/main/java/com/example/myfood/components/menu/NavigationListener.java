package com.example.myfood.components.menu;

import android.content.Context;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myfood.R;
import com.example.myfood.components.mainScreen.ui.MainScreenActivity;
import com.example.myfood.components.settings.ui.SettingsActivity;

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
                Toast.makeText(context, "Food of pupils", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_class_inform:
                Toast.makeText(context, "Information about my class", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_INK:
                Toast.makeText(context, "My INK", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_setting:
                view.showActivity(SettingsActivity.class);
                break;
            case R.id.menu_my_food:
                Toast.makeText(context, "My food", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
