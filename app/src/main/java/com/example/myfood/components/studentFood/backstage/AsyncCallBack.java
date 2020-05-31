package com.example.myfood.components.studentFood.backstage;

import java.util.LinkedList;

public interface AsyncCallBack {
    void setData(LinkedList<String[]> treeMaps);
    void showData();
    void afterSendingFood();
}
