package com.example.myfood.components.login.backstage;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myfood.R;
import com.example.myfood.abstracts.presenter.BasePresenter;
import com.example.myfood.components.login.ui.UiContract;

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter, AsynCallBack {

    @Override
    public void onClickButton(int id) {
        switch (id) {
            case R.id.button_to_choose_fragment:
                ((LoginContract.View) view).showSignUpFirstFragment();
                break;
            case R.id.button_to_login_fragment:
                ((LoginContract.View) view).showLogInFragment();
                break;
            case  R.id.sign_up_back_arrow:
            case R.id.login_back_arrow:
                ((LoginContract.View) view).previousFragment();
                break;
            case R.id.sign_up_first_type:
                ((LoginContract.View) view).showSignUpFirstTypeFragment();
                break;
            case R.id.sign_up_second_type:
                ((LoginContract.View) view).showSignUpSecondTypeFragment();
                break;
            case R.id.sign_up_types_account_button:
                ((UiContract.Fragments.SignUpChooseAccount)((LoginContract.View) view).getFragment3()).showInformFragment();
        }
    }

    @Override
    public void login(String email, String password) {
        UiContract.Fragments.LoginFragment fragment = (UiContract.Fragments.LoginFragment) ((LoginContract.View) view).getFragmentNow();
        fragment.hideAlerts();
        if (email.matches("\\s*") || password.matches("\\s*")) {
            if (email.matches("\\s*")) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
            }
//          Код для проверки валидности почты
            if (password.matches("\\s*")) {
                fragment.showPasswordAlert(view.getContext().getResources().getString(R.string.password_alert));
            }
            return;
        }
        Log.d("GET_DATA", email + " " + password);
        AsyncLogin asyncLogin = new AsyncLogin(this);
        asyncLogin.execute();
    }

    @Override
    public void signUpFirst(String email, String password, String school, String name, String numberClass) {
        UiContract.Fragments.SignUpFirstFragment fragment = (UiContract.Fragments.SignUpFirstFragment) ((LoginContract.View) view).getFragmentNow();
        fragment.hideAlerts();
        if (email.matches("\\s*") || password.matches("\\s*") || school.matches("\\s*") || name.matches("\\s*") || numberClass.matches("\\s*")) {
            if (email.matches("\\s*")) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
            }
//          Код для проверки валидности почты
            if (password.matches("\\s*")) {
                fragment.showPasswordAlert(view.getContext().getResources().getString(R.string.password_alert));
            }
            if (school.matches("\\s*")) {
                fragment.showSchoolAlert(view.getContext().getResources().getString(R.string.school_alert));
            }
            if (name.matches("\\s*")) {
                fragment.showNameAlert(view.getContext().getResources().getString(R.string.name_alert));
            }
            if (numberClass.matches("\\s*")) {
                fragment.showClassAlert(view.getContext().getResources().getString(R.string.class_alert));
            }
            return;
        }
        Log.d("GET_DATA", email + " " + password + " " + school + " " + name + " " + numberClass);
        AsyncLogin asyncSingUpFirst = new AsyncLogin(this);
        asyncSingUpFirst.execute();
    }

    @Override
    public void signUpSecond(String email, String password, String name, String ink) {
        UiContract.Fragments.SignUpSecondFragment fragment = (UiContract.Fragments.SignUpSecondFragment) ((LoginContract.View) view).getFragmentNow();
        fragment.hideAlerts();
        if (email.matches("\\s*") || password.matches("\\s*") || ink.matches("\\s*") || name.matches("\\s*")) {
            if (email.matches("\\s*")) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
            }
//          Код для проверки валидности почты
            if (password.matches("\\s*")) {
                fragment.showPasswordAlert(view.getContext().getResources().getString(R.string.password_alert));
            }
            if (name.matches("\\s*")) {
                fragment.showNameAlert(view.getContext().getResources().getString(R.string.name_alert));
            }
            if (ink.matches("\\s*")) {
                fragment.showInkAlert(view.getContext().getResources().getString(R.string.ink_alert));
            }
            return;
        }
        Log.d("GET_DATA", email + " " + password + " " + name + " " + ink);
        AsyncLogin asyncSingUpSecond = new AsyncLogin(this);
        asyncSingUpSecond.execute();
    }

    @Override
    public void getAsyncTaskResult(Integer integer) {
        switch (integer) {
            case 200:
                Log.d("MYTAG", "200");
        }
    }

    class AsyncLogin extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog;
        AsynCallBack asynCallBack;

        AsyncLogin(AsynCallBack asynCallBack) {
            this.asynCallBack = asynCallBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(view);
            progressDialog.setTitle("Пожалуйста, подождите");
            progressDialog.setMessage("Ведется соединение с сервером!");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try {
//              Общение с БД
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.dismiss();
            asynCallBack.getAsyncTaskResult(integer);
        }
    }
}

interface AsynCallBack {
    void getAsyncTaskResult(Integer integer);
}