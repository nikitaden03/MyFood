package com.example.myfood.components.myClassInfo.backstage;

import com.example.myfood.abstracts.presenter.BasePresenter;
import com.example.myfood.data.User;

import java.util.ArrayList;

public class MyClassPresenter extends BasePresenter implements MyClassContract.Presenter {
    @Override
    public ArrayList<String> getInformMyClass(User user) {
        //TODO Обращение к data слою за данными
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        arrayList.add("Антон Антонов Антонович");
        return arrayList;
    }
}
