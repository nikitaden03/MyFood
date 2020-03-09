package com.example.myfood.components;

import android.content.Context;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myfood.R;

public class NavigationListener {

    public static void onNavigationItemSelected(MenuItem menuItem, Context context) {
        switch (menuItem.getItemId()) {
            case R.id.menu_main:
                Toast.makeText(context, "Main", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "My Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_my_food:
                Toast.makeText(context, "My food", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
