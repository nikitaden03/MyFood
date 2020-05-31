package com.example.myfood.components.teacherFood.backstage;

import java.util.LinkedList;
import java.util.TreeMap;

public interface AsyncCallBack {
    void showData();
    void setData(LinkedList<TreeMap<String, String[]>> treeMaps);
}
