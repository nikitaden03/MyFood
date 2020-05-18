package com.example.myfood.components.login.ui;

public interface UiContract {
    interface Fragments {
        interface LoginFragment {
            void showEmailAlert(String text);
            void showPasswordAlert(String text);
            void hideAlerts();
        }
        interface SignUpChooseAccount {
            void showInformFragment();
        }
        interface SignUpFragment {
            void showEmailAlert(String text);
            void showPasswordAlert(String text);
            void showNameAlert(String text);
        }
        interface SignUpFirstFragment extends SignUpFragment {
            void showSchoolAlert(String text);
            void showClassAlert(String text);
            void hideAlerts();
        }
        interface SignUpSecondFragment extends SignUpFragment {
            void showInkAlert(String text);
            void hideAlerts();
        }
    }
    interface View {
        void onClick(int id);
        void onClickLogin(String email, String password);
        void onClickSignUpFirst(String email, String password, String school, String name, String numberClass);
        void onClickSignUpSecond(String toString, String toString1, String toString2, String toString3);
    }
}
