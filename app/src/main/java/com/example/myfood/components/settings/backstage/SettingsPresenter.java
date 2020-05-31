package com.example.myfood.components.settings.backstage;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.View;

import com.example.myfood.R;
import com.example.myfood.abstracts.presenter.BasePresenter;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.data.Data;
import com.example.myfood.data.models.User;

import static android.content.Context.MODE_PRIVATE;

public class SettingsPresenter extends BasePresenter implements SettingsContract.Presenter {

    // Обрабатывает нажатия на все кнопки(их 2) в activity
    @Override
    public void onClick(View view) {

        // Получает activity
        SettingsContract.View activity = (SettingsContract.View) this.view;

        switch (view.getId()) {

            // Если нажата кнопка для изменения цены
            case R.id.settings_save_button:

                Resources resources = this.view.getContext().getResources();

                String breakfast, lunch, teatime;
                boolean dataCorrect = true;

                activity.hideAllAlerts();

                breakfast = activity.getBreakfastPrice();
                lunch = activity.getLunchPrice();
                teatime = activity.getTeatimePrice();

                // Проверка на соотвествие число+(англ. р||рус. p)

                if (!breakfast.matches("^\\d{2,4}[рp]$")) {
                    activity.showBreakfastPriceAlert(resources.getString(R.string.settings_format_data_alert));
                    dataCorrect = false;
                }

                if (!lunch.matches("^\\d{2,4}[рp]$")) {
                    activity.showLunchPriceAlert(resources.getString(R.string.settings_format_data_alert));
                    dataCorrect = false;
                }

                if (!teatime.matches("^\\d{2,4}[рp]$")) {
                    activity.showTeatimePriceAlert(resources.getString(R.string.settings_format_data_alert));
                    dataCorrect = false;
                }

                // Проверка на пустоту

                if (breakfast.matches("\\s*")) {
                    activity.showBreakfastPriceAlert(resources.getString(R.string.settings_breakfast_empty_alert));
                    dataCorrect = false;
                }

                if (lunch.matches("\\s*")) {
                    activity.showLunchPriceAlert(resources.getString(R.string.settings_lunch_empty_alert));
                    dataCorrect = false;
                }

                if (teatime.matches("\\s*")) {
                    activity.showTeatimePriceAlert(resources.getString(R.string.settings_teatime_empty_alert));
                    dataCorrect = false;
                }

                if (!dataCorrect) {
                    return;
                }

                User user = activity.getUser();

                int breakfastNum = Integer.parseInt(breakfast.substring(0, breakfast.length() - 1));
                int teatimeNum = Integer.parseInt(teatime.substring(0, teatime.length() - 1));
                int lunchNum = Integer.parseInt(lunch.substring(0, lunch.length() - 1));

                // Проверяет не нажата ли кнопка просто так
                if (breakfastNum == user.getPriceBreakfast() && teatimeNum == user.getPriceTeatime() && lunchNum == user.getPriceLunch()) {
                    return;
                }

                // Изменяет цену локально
                activity.setPricesData(breakfastNum, teatimeNum, lunchNum);

                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("token", MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");

                // Изменяет цену на сервере
                AsyncTasks asyncTasks = new AsyncTasks(this, breakfastNum, teatimeNum, lunchNum, token, this.view);
                asyncTasks.execute();

                break;

            // Если нажата кнопка выйти из аккаунта
            case R.id.settings_leave_button:
                SharedPreferences sharedPreferences1 = activity.getContext().getSharedPreferences("token", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("token", "");
                editor.apply();
                activity.leaveAccount();
                break;
        }
    }

    class AsyncTasks extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;
        SettingsContract.Presenter asynCallBack;
        int breakfast, teatime, lunch;
        String token;
        BaseCompatActivity baseCompatActivity;

        AsyncTasks(SettingsContract.Presenter asynCallBack, int breakfast, int teatime, int lunch, String token, BaseCompatActivity baseCompatActivity) {
            this.asynCallBack = asynCallBack;
            this.breakfast = breakfast;
            this.teatime = teatime;
            this.lunch = lunch;
            this.token = token;
            this.baseCompatActivity = baseCompatActivity;
        }

        // Создает ProgressDialog, в котором находится надпись о просьбе подождать
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(view);
            progressDialog.setTitle("Пожалуйста, подождите");
            progressDialog.setMessage("Ведется соединение с сервером!");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        // В отдельном потоке вызывает функцию класса Data, которая изменяет цены питания на сервере.
        @Override
        protected Void doInBackground(String... strings) {
            Data data = Data.getInstance();
            data.addPrice(breakfast, teatime, lunch, token, baseCompatActivity);
            return null;
        }

        // Удаляет ProgressDialog
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }
}
