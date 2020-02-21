package com.example.myfood.components.login.ui;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myfood.R;

public class SignUpFirstFragment extends Fragment implements UiContract.Fragments.SignUpChooseAccount{

    private UiContract.View onClickInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onClickInterface = (UiContract.View) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        android.view.View v = inflater.inflate(R.layout.fragment_sign_up_first, container, false);

        Button firstTypeAccount = v.findViewById(R.id.sign_up_first_type);
        Button secondTypeAccount = v.findViewById(R.id.sign_up_second_type);
        Button typesAccount = v.findViewById(R.id.sign_up_types_account_button);
        ImageButton backArrow = v.findViewById(R.id.sign_up_back_arrow);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.onClick(v.getId());
            }
        };
        firstTypeAccount.setOnClickListener(onClickListener);
        secondTypeAccount.setOnClickListener(onClickListener);
        typesAccount.setOnClickListener(onClickListener);
        backArrow.setOnClickListener(onClickListener);
        return v;
    }

    @Override
    public void showInformFragment() {
        TypeAccountDialog typeAccountDialog = new TypeAccountDialog();
        typeAccountDialog.show(getFragmentManager(), "");
    }
}
