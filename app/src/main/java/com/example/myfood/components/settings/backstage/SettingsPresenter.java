package com.example.myfood.components.settings.backstage;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.View;

import com.example.myfood.R;
import com.example.myfood.abstracts.presenter.BasePresenter;
import com.example.myfood.data.Data;
import com.example.myfood.data.models.User;

import static android.content.Context.MODE_PRIVATE;

public class SettingsPresenter extends BasePresenter implements SettingsContract.Presenter {
    @Override
    public void onClick(View view) {
        SettingsContract.View activity = (SettingsContract.View) this.view;
        switch (view.getId()) {
            case R.id.settings_save_button:

                Resources resources = this.view.getContext().getResources();

                String breakfast, lunch, teatime;
                boolean dataCorrect = true;

                activity.hideAllAlerts();

                breakfast = activity.getBreakfastPrice();
                lunch = activity.getLunchPrice();
                teatime = activity.getTeatimePrice();

                // Проверка на соотвествие число+р

                //TODO: Надо отправлять только цифру!!!

                if (!breakfast.matches("^\\d{2,4}[рp$]$")) {
                    activity.showBreakfastPriceAlert(resources.getString(R.string.settings_format_data_alert));
                    dataCorrect = false;
                }

                if (!lunch.matches("^\\d{2,4}[рp$]$")) {
                    activity.showLunchPriceAlert(resources.getString(R.string.settings_format_data_alert));
                    dataCorrect = false;
                }

                if (!teatime.matches("^\\d{2,4}[рp$]$")) {
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

                if (breakfastNum == user.getPriceBreakfast() && teatimeNum == user.getPriceTeatime() && lunchNum == user.getPriceLunch()) {
                    return;
                }

                activity.setPricesData(breakfastNum, teatimeNum, lunchNum);

                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("token", MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");

                AsyncTasks asyncTasks = new AsyncTasks(this, breakfastNum, teatimeNum, lunchNum, token, (SettingsContract.View) this.view);
                asyncTasks.execute();

                break;

            case R.id.settings_leave_button:

                SharedPreferences sharedPreferences1 = activity.getContext().getSharedPreferences("token", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("token", "");
                editor.apply();
                activity.leaveAccount();

                break;
        }
    }

    @Override
    public void getAsyncTaskResult(int i) {

    }

    class AsyncTasks extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog;
        SettingsContract.Presenter asynCallBack;
        int breakfast, teatime, lunch;
        String token;
        SettingsContract.View baseCompatActivity;

        AsyncTasks(SettingsContract.Presenter asynCallBack, int breakfast, int teatime, int lunch, String token, SettingsContract.View baseCompatActivity) {
            this.asynCallBack = asynCallBack;
            this.breakfast = breakfast;
            this.teatime = teatime;
            this.lunch = lunch;
            this.token = token;
            this.baseCompatActivity = baseCompatActivity;
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
            Data data = Data.getInstance();
            data.addPrice(breakfast, teatime, lunch, token, baseCompatActivity);
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
