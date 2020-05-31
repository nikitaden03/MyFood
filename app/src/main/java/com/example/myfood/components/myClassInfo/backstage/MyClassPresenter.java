package com.example.myfood.components.myClassInfo.backstage;

import com.example.myfood.abstracts.presenter.BasePresenter;
import com.example.myfood.data.models.User;

public class MyClassPresenter extends BasePresenter implements MyClassContract.Presenter{

    // Начинает скачку данных, посредством создания инстанса класса, наследующегося от AsyncTask-a
    @Override
    public void prepareInformMyClass(User user) {
        AsyncGetUsers asyncGetUsers = new AsyncGetUsers(user.getINK() + "", (AsyncCallBack) view, view);
        asyncGetUsers.execute();
    }

}

