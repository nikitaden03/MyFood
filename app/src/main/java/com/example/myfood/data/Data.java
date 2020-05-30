package com.example.myfood.data;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.settings.backstage.SettingsContract;
import com.example.myfood.components.studentFood.backstage.StudentFoodContract;
import com.example.myfood.data.models.Classmate;
import com.example.myfood.data.models.GeneralUser;
import com.example.myfood.data.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class Data {

    private static Data instance;
    Retrofit retrofit;

    private final String BASE_URL = "https://myfoodserver.herokuapp.com/";

    private Data() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public int signUp(String email, String password, String name, String groupNum, String classNum, String schoolNum) {
        DataService dataService = retrofit.create(DataService.class);
        Call<Integer> call = dataService.signUp(email, password, name, groupNum, classNum, schoolNum);
        try {
            Response<Integer> response = call.execute();
            if (response.code() == 200) {
                Log.d("MYTEG", response.body()+"");
                return response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 100;
    }

    public String logIn(String email, String password) {
        DataService dataService = retrofit.create(DataService.class);
        Call<String> call = dataService.logIn(email, password);
        try {
            Response<String> response = call.execute();
            if (response.code() == 200) {
                Log.d("MYTEG", response.body()+"");
                return response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "100";
    }

    public User getInformation(String token, BaseCompatActivity activity) {
        DataService dataService = retrofit.create(DataService.class);
        Call<GeneralUser> call = dataService.getInformation(token);
        try {
            Response<GeneralUser> response = call.execute();
            if (response.code() == 200) {
                GeneralUser user = response.body();
                if (user.getCode() == 107) {
                    SharedPreferences sharedPreferences = activity.getContext().getSharedPreferences("token", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", "");
                    editor.apply();
                    return null;
                }
                return new User(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Classmate> getClassmates (String token, String groupNum) {
        DataService dataService = retrofit.create(DataService.class);
        Call<ArrayList<Classmate>> call = dataService.getListClassmates(token, groupNum);
        try {
            Response response = call.execute();
            ArrayList<Classmate> classmates = (ArrayList<Classmate>) response.body();
            return classmates;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addPrice(int breakfast, int teatime, int lunch, String token, SettingsContract.View activity) {
        DataService dataService = retrofit.create(DataService.class);
        Call<Integer> call = dataService.addPrice(breakfast, teatime, lunch, token);
        try {
            Response response = call.execute();
            if (response.code() == 200) {
                if ((int)response.body() == 107) {
                    SharedPreferences sharedPreferences = activity.getContext().getSharedPreferences("token", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", "");
                    editor.apply();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<TreeMap<String, String[]>> getTeacherFood (String token) {
        DataService dataService = retrofit.create(DataService.class);
        Call<LinkedList<TreeMap<String, String[]>>> call = dataService.getTeacherFood(token);
        try {
            Response<LinkedList<TreeMap<String, String[]>>> response = call.execute();
            if (response.code() == 200) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LinkedList<TreeMap<String, String[]>> linkedList = new LinkedList<>();
        TreeMap<String, String[]> treeMap = new TreeMap<>();
        for (int i = 1; i <= day; i++) {
            linkedList.add(treeMap);
        }
        return linkedList;
    }

    public LinkedList<String[]> getStudentFood (String token) {
        DataService dataService = retrofit.create(DataService.class);
        Call<LinkedList<String[]>> call = dataService.getStudentFood(token);
        try {
            Response<LinkedList<String[]>> response = call.execute();
            if (response.code() == 200) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LinkedList<String[]> linkedList = new LinkedList<>();
        for (int i = 1; i <= day; i++) {
            linkedList.add(new String[]{"0", "0", "0"});
        }
        return linkedList;
    }

    public Integer sendStudentFood (String breakfast, String teatime, String lunch, String token, StudentFoodContract.View activity) {
        DataService dataService = retrofit.create(DataService.class);
        Call<Integer> call = dataService.sendStudentFood(breakfast, teatime, lunch, token);
        try {
            Response<Integer> response = call.execute();
            if (response.code() == 200) {
                if (response.body() == 107) {
                    SharedPreferences sharedPreferences = activity.getContext().getSharedPreferences("token", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", "");
                    editor.apply();
                }
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 107;
    }
}
