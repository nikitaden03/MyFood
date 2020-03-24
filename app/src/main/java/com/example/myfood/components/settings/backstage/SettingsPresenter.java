package com.example.myfood.components.settings.backstage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.myfood.CheckActivity;
import com.example.myfood.R;
import com.example.myfood.abstracts.presenter.BasePresenter;
import com.example.myfood.data.User;

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

                if (!breakfast.matches("^\\d{2,4}р$")) {
                    activity.showBreakfastPriceAlert(resources.getString(R.string.settings_format_data_alert));
                    dataCorrect = false;
                }

                if (!lunch.matches("^\\d{2,4}р$")) {
                    activity.showLunchPriceAlert(resources.getString(R.string.settings_format_data_alert));
                    dataCorrect = false;
                }

                if (!teatime.matches("^\\d{2,4}р$")) {
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

                if ((user.getPriceBreakfast() + "р").equals(breakfast) && (user.getPriceTeatime() + "р").equals(teatime) && (user.getPriceLunch() + "р").equals(lunch)) {
                    return;
                }

                int breakfastNum = Integer.parseInt(breakfast.substring(0, breakfast.length() - 1));
                int teatimeNum = Integer.parseInt(teatime.substring(0, teatime.length() - 1));
                int lunchNum = Integer.parseInt(lunch.substring(0, lunch.length() - 1));

                activity.setPricesData(breakfastNum, teatimeNum, lunchNum);

                AsyncTasks asyncTasks = new AsyncTasks(this);
                asyncTasks.execute();

                break;

            case R.id.settings_leave_button:

                SharedPreferences sharedPreferences = activity.getContext().getSharedPreferences("token", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
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

        AsyncTasks(SettingsContract.Presenter asynCallBack) {
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
                // TODO Общение с БД
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
