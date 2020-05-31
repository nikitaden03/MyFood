package com.example.myfood.components.myINK.backstage;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.example.myfood.abstracts.presenter.BasePresenter;

public class MyInkPresenter extends BasePresenter implements MyInkContract.Presenter {

    // Вставляет номер ИНК в буфер обмена пользователя
    @Override
    public void toCopy(String ink) {
        ClipboardManager clipboardManager = (ClipboardManager) view.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", ink);
        clipboardManager.setPrimaryClip(clip);
        ((MyInkContract.View)view).showToast();
    }
}
