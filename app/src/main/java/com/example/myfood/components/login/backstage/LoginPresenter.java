package com.example.myfood.components.login.backstage;

import com.example.myfood.R;
import com.example.myfood.abstracts.presenter.BasePresenter;
import com.example.myfood.components.login.ui.UiContract;

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter, AsynсCallBack {

    @Override
    public void onClickButton(int id) {
        switch (id) {

            // Если нажата кнопка, которая перенаправляет пользователя к activity с выбором типа аккаунта
            case R.id.button_to_choose_fragment:
                ((LoginContract.View) view).showSignUpFirstFragment();
                break;

            // Если нажата кнопка, которая перенаправляет пользователя к activity с авторизацией
            case R.id.button_to_login_fragment:
                ((LoginContract.View) view).showLogInFragment();
                break;

            // Если нажата кнопка, которая возращает пользователя на прежнюю activity
            case  R.id.sign_up_back_arrow:
            case R.id.login_back_arrow:
                ((LoginContract.View) view).previousFragment();
                break;

            // Если нажата кнопка, которая перенаправляет пользователя к activity с регистрацией для дежурного аккаунта
            case R.id.sign_up_first_type:
                ((LoginContract.View) view).showSignUpFirstTypeFragment();
                break;

            // Если нажата кнопка, которая перенаправляет пользователя к activity с регистрацией для питающегося аккаунта
            case R.id.sign_up_second_type:
                ((LoginContract.View) view).showSignUpSecondTypeFragment();
                break;

            // Если нажата кнопка, которая вызывает окошко, с описанием типов аккаунта
            case R.id.sign_up_types_account_button:
                ((UiContract.Fragments.SignUpChooseAccount)((LoginContract.View) view).getFragment3()).showInformFragment();
        }
    }

    // Авторизация пользователя
    @Override
    public void login(String email, String password) {
        UiContract.Fragments.LoginFragment fragment = (UiContract.Fragments.LoginFragment) ((LoginContract.View) view).getFragmentNow();
        fragment.hideAlerts();

        // Проверка корректности данных
        if (email.matches("\\s*") || password.matches("\\s*") || checkEmail(email)) {
            if (email.matches("\\s*")) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
            } else if (checkEmail(email)) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_uncorrect));
            }
            if (password.matches("\\s*")) {
                fragment.showPasswordAlert(view.getContext().getResources().getString(R.string.password_alert));
            }
            return;
        }

        AsyncLoginForLogIn asyncLoginForLogIn = new AsyncLoginForLogIn(this, view, email, password);
        asyncLoginForLogIn.execute();
    }

    //Регистрация пользователя с типом аккаунта Дежурный
    @Override
    public void signUpFirst(String email, String password, String school, String name, String numberClass) {
        UiContract.Fragments.SignUpFirstFragment fragment = (UiContract.Fragments.SignUpFirstFragment) ((LoginContract.View) view).getFragmentNow();
        fragment.hideAlerts();

        // Проверка корректности данных
        if (email.matches("\\s*") || password.matches("\\s*") || school.matches("\\s*") || name.matches("\\s*") || numberClass.matches("\\s*") || checkEmail(email)) {
            if (email.matches("\\s*")) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
            } else if (checkEmail(email)) {
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

        AsyncLoginForSignUP asyncLoginForSignUP = new AsyncLoginForSignUP(this, view, email, password, name, "", numberClass, school);
        asyncLoginForSignUP.execute();
    }

    //Регистрация пользователя с типом аккаунта Питающийся
    @Override
    public void signUpSecond(String email, String password, String name, String ink) {
        UiContract.Fragments.SignUpSecondFragment fragment = (UiContract.Fragments.SignUpSecondFragment) ((LoginContract.View) view).getFragmentNow();
        fragment.hideAlerts();

        // Проверка корректности данных
        if (email.matches("\\s*") || password.matches("\\s*") || ink.matches("\\s*") || name.matches("\\s*") || checkEmail(email)) {
            if (email.matches("\\s*")) {
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
            } else if (checkEmail(email)) {
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

        AsyncLoginForSignUP asyncLoginForSignUP = new AsyncLoginForSignUP(this, view, email, password, name, ink, "", "");
        asyncLoginForSignUP.execute();
    }

    // Обрабатывает ответ от AsyncLoginForSignUP
    @Override
    public void getAsyncTaskSignUpResult(Integer integer) {
        switch (integer) {

            // Если все хорошо, перехожу на checkActivity
            case 200:
                ((LoginContract.View) view).ReloadApp();
                break;

            // Если пустой email. По идее, вызываться не должно, так как существует предпроверка данных
            case 102:
                UiContract.Fragments.SignUpFragment fragment = (UiContract.Fragments.SignUpFragment) ((LoginContract.View) view).getFragmentNow();
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
                fragment.showPasswordAlert(view.getContext().getResources().getString(R.string.password_alert));
                break;

            // Если пользователь ввел неправильный ИНК
            case 103:
                UiContract.Fragments.SignUpSecondFragment fragment2 = (UiContract.Fragments.SignUpSecondFragment) ((LoginContract.View) view).getFragmentNow();
                fragment2.showInkAlert(view.getContext().getResources().getString(R.string.ink_error));
                break;
            // Если пользователь с таким email уже существует
            case 104:
                UiContract.Fragments.SignUpFragment fragment3 = (UiContract.Fragments.SignUpFragment) ((LoginContract.View) view).getFragmentNow();
                fragment3.showEmailAlert(view.getContext().getResources().getString(R.string.email_isnt_free));
                break;
        }
    }

    @Override
    public void getAsyncTaskLoginResult(Integer integer) {
        switch (integer) {
            // Если пустой email. По идее, вызываться не должно, так как существует предпроверка данных
            case 102:
                UiContract.Fragments.LoginFragment fragment = (UiContract.Fragments.LoginFragment) ((LoginContract.View) view).getFragmentNow();
                fragment.showEmailAlert(view.getContext().getResources().getString(R.string.email_alert));
                break;
            // Если пользователь ввел неправильный email
            case 105:
                UiContract.Fragments.LoginFragment fragment1 = (UiContract.Fragments.LoginFragment) ((LoginContract.View) view).getFragmentNow();
                fragment1.showEmailAlert(view.getContext().getResources().getString(R.string.email_not_exist));
                break;
            // Если пользователь ввел неправильный пароль
            case 106:
                UiContract.Fragments.LoginFragment fragment2 = (UiContract.Fragments.LoginFragment) ((LoginContract.View) view).getFragmentNow();
                fragment2.showPasswordAlert(view.getContext().getResources().getString(R.string.password_not_exist));
                break;
            default:
                ((LoginContract.View) view).ReloadApp();
        }
    }

    // Проверка на корректнось email
    private boolean checkEmail(String email) {
        return !email.matches(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }
}


