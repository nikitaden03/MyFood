package com.example.myfood.components.login.backstage;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myfood.R;
import com.example.myfood.abstracts.presenter.BasePresenter;
import com.example.myfood.components.login.ui.UiContract;
import com.example.myfood.data.Data;

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter, AsynCallBack, AsynCallBackLogIn {

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
        if (email.matches("\\s*") || password.matches("\\s*") || !checkEmail(email)) {
            if (email.matches("\\s*")) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
            } else if (!checkEmail(email)) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_uncorrect));
            }
            if (password.matches("\\s*")) {
                fragment.showPasswordAlert(view.getContext().getResources().getString(R.string.password_alert));
            }
            return;
        }
        Log.d("GET_DATA", email + " " + password);
        AsyncLoginForLogIn asyncLogin = new AsyncLoginForLogIn(this, 3);
        asyncLogin.setVar(email, password);
        asyncLogin.execute();
    }

    @Override
    public void signUpFirst(String email, String password, String school, String name, String numberClass) {
        UiContract.Fragments.SignUpFirstFragment fragment = (UiContract.Fragments.SignUpFirstFragment) ((LoginContract.View) view).getFragmentNow();
        fragment.hideAlerts();
        if (email.matches("\\s*") || password.matches("\\s*") || school.matches("\\s*") || name.matches("\\s*") || numberClass.matches("\\s*") || !checkEmail(email)) {
            if (email.matches("\\s*")) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
            } else if (!checkEmail(email)) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_uncorrect));
            }
            if (password.matches("\\s*")) {
                fragment.showPasswordAlert(view.getContext().getResources().getString(R.string.password_alert));
            }
            if (!school.matches("^\\d+$")) {
                fragment.showSchoolAlert(view.getContext().getResources().getString(R.string.school_format_alert));
            }
            if (school.matches("\\s*")) {
                fragment.showSchoolAlert(view.getContext().getResources().getString(R.string.school_alert));
            }
            if (name.matches("\\s*")) {
                fragment.showNameAlert(view.getContext().getResources().getString(R.string.name_alert));
            }
            if (!numberClass.matches("^\\d{1,2}[А-Я]$")) {
                fragment.showClassAlert(view.getContext().getResources().getString(R.string.class_format_alert));
            }
            if (numberClass.matches("\\s*")) {
                fragment.showClassAlert(view.getContext().getResources().getString(R.string.class_alert));
            }
            return;
        }
        Log.d("GET_DATA", email + " " + password + " " + school + " " + name + " " + numberClass);
        AsyncLoginForSignUP asyncSingUpFirst = new AsyncLoginForSignUP(this, 1);
        asyncSingUpFirst.setVar(email, password, name, "", numberClass, school);
        asyncSingUpFirst.execute();
    }

    @Override
    public void signUpSecond(String email, String password, String name, String ink) {
        UiContract.Fragments.SignUpSecondFragment fragment = (UiContract.Fragments.SignUpSecondFragment) ((LoginContract.View) view).getFragmentNow();
        fragment.hideAlerts();
        if (email.matches("\\s*") || password.matches("\\s*") || ink.matches("\\s*") || name.matches("\\s*") || !checkEmail(email)) {
            if (email.matches("\\s*")) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
            } else if (!checkEmail(email)) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_uncorrect));
            }
            if (password.matches("\\s*")) {
                fragment.showPasswordAlert(view.getContext().getResources().getString(R.string.password_alert));
            }
            if (name.matches("\\s*")) {
                fragment.showNameAlert(view.getContext().getResources().getString(R.string.name_alert));
            }
            if (!ink.matches("^\\d{8}$")) {
                fragment.showInkAlert(view.getContext().getResources().getString(R.string.ink_format_alert));
            }
            if (ink.matches("\\s*")) {
                fragment.showInkAlert(view.getContext().getResources().getString(R.string.ink_alert));
            }
            return;
        }
        Log.d("GET_DATA", email + " " + password + " " + name + " " + ink);
        AsyncLoginForSignUP asyncSingUpSecond = new AsyncLoginForSignUP(this, 2);
        asyncSingUpSecond.setVar(email, password, name, ink, "", "");
        asyncSingUpSecond.execute();
    }

    @Override
    public void getAsyncTaskResult(Integer integer) {
        switch (integer) {
            case 200:
                Log.d("MYTAG", "200");
                ((LoginContract.View) view).nextSceenAfterSignUp();
                break;
            case 102:
                UiContract.Fragments.SignUpFragment fragment = (UiContract.Fragments.SignUpFragment) ((LoginContract.View) view).getFragmentNow();
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
                fragment.showPasswordAlert(view.getContext().getResources().getString(R.string.password_alert));
                break;
            case 103:
                UiContract.Fragments.SignUpSecondFragment fragment2 = (UiContract.Fragments.SignUpSecondFragment) ((LoginContract.View) view).getFragmentNow();
                fragment2.showInkAlert(view.getContext().getResources().getString(R.string.ink_error));
                break;
            case 104:
                UiContract.Fragments.SignUpFragment fragment3 = (UiContract.Fragments.SignUpFragment) ((LoginContract.View) view).getFragmentNow();
                fragment3.showEmailAlert(view.getContext().getResources().getString(R.string.email_isnt_free));
                break;
            case 106:
                break;
        }
    }

    public boolean checkEmail(String email) {
        return email.matches(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    @Override
    public void getAsyncTaskResult(String string) {
        switch (string) {
            case "102":
                UiContract.Fragments.LoginFragment fragment = (UiContract.Fragments.LoginFragment) ((LoginContract.View) view).getFragmentNow();
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
                fragment.showPasswordAlert(view.getContext().getResources().getString(R.string.password_alert));
                break;
            case "105":
                UiContract.Fragments.LoginFragment fragment1 = (UiContract.Fragments.LoginFragment) ((LoginContract.View) view).getFragmentNow();
                fragment1.showEmailAlert(view.getContext().getResources().getString(R.string.email_not_exist));
                break;
            case "106":
                UiContract.Fragments.LoginFragment fragment2 = (UiContract.Fragments.LoginFragment) ((LoginContract.View) view).getFragmentNow();
                fragment2.showPasswordAlert(view.getContext().getResources().getString(R.string.password_not_exist));
                break;
            default:
                ((LoginContract.View) view).nextSceenAfterLogIn(string);
        }
    }

    class AsyncLoginForSignUP extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog;
        AsynCallBack asynCallBack;
        int whereItIs;
        Data data;
        String email, password, name, groupNum, classNum, schoolNum;

        AsyncLoginForSignUP(AsynCallBack asynCallBack, int whereItIS) {
            this.asynCallBack = asynCallBack;
            this.whereItIs = whereItIS;
        }

        @Override
        protected void onPreExecute() {
            Log.d("MYTAG", "onPreExecute");
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
                if (whereItIs == 1 || whereItIs == 2) {
                    data = Data.getInstance();
                     return data.signUp(email, password, name, groupNum, classNum, schoolNum);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 100;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.dismiss();
            asynCallBack.getAsyncTaskResult(integer);
        }

        public void setVar(String email, String password, String name, String groupNum, String classNum, String schoolNum) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.groupNum = groupNum;
            this.classNum = classNum;
            this.schoolNum = schoolNum;
        }
    }

    class AsyncLoginForLogIn extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        AsynCallBackLogIn asynCallBack;
        int whereItIs;
        Data data;
        String email, password;

        AsyncLoginForLogIn(AsynCallBackLogIn asynCallBack, int whereItIS) {
            this.asynCallBack = asynCallBack;
            this.whereItIs = whereItIS;
        }

        @Override
        protected void onPreExecute() {
            Log.d("MYTAG", "onPreExecute");
            super.onPreExecute();
            progressDialog = new ProgressDialog(view);
            progressDialog.setTitle("Пожалуйста, подождите");
            progressDialog.setMessage("Ведется соединение с сервером!");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                if (whereItIs == 3) {
                    data = Data.getInstance();
                    return data.logIn(email, password);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "100";
        }

        @Override
        protected void onPostExecute(String integer) {
            super.onPostExecute(integer);
            progressDialog.dismiss();
            asynCallBack.getAsyncTaskResult(integer);
        }

        public void setVar(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}

interface AsynCallBack {
    void getAsyncTaskResult(Integer integer);
}

interface AsynCallBackLogIn {
    void getAsyncTaskResult(String string);
}