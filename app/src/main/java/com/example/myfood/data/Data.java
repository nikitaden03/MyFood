package com.example.myfood.data;

import android.content.SharedPreferences;

import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.data.models.Classmate;
import com.example.myfood.data.models.MenuForResponse;
import com.example.myfood.data.models.UserForResponse;
import com.example.myfood.data.models.TokenForResponse;
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

    // Отправляет запрос на сервер для регистрации пользоваетеля
    public int signUp(String email, String password, String name, String groupNum, String classNum, String schoolNum) {
        DataService dataService = retrofit.create(DataService.class);
        Call<Integer> call = dataService.signUp(email, password, name, groupNum, classNum, schoolNum);

        try {
            Response<Integer> response = call.execute();
            if (response.code() == 200) {
                return response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 103;
    }

    // Отправляет запрос на сервер для авторизации пользователя
    public Integer logIn(String email, String password, BaseCompatActivity activity) {

        DataService dataService = retrofit.create(DataService.class);
        Call<TokenForResponse> call = dataService.logIn(email, password);

        try {
            Response<TokenForResponse> response = call.execute();
            if (response.code() == 200) {

                // Если пользователь ууспешно авторизовался и в ответе от сервера пришел правильный, корректный токен
                TokenForResponse tokenForResponse = response.body();
                if (tokenForResponse.getCodeResponse() == 200) {
                    changeTokenText(tokenForResponse.getToken(), activity);
                }
                return tokenForResponse.getCodeResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 105;
    }

    // Отправляет запрос на сервер для получения данный о пользователи
    public User getInformation(String token, BaseCompatActivity activity) {
        DataService dataService = retrofit.create(DataService.class);
        Call<UserForResponse> call = dataService.getInformation(token);
        try {
            Response<UserForResponse> response = call.execute();
            if (response.code() == 200) {
                UserForResponse user = response.body();
                // Если токен не корректен
                if (user.getCode() == 107) {
                    changeTokenText("", activity);
                    return null;
                }
                return new User(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Отправляет запрос на сервер для получения списка имен одноклассников
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

    // Отправляет запрос на сервер для изменения цены
    public void addPrice(int breakfast, int teatime, int lunch, String token, BaseCompatActivity activity) {
        DataService dataService = retrofit.create(DataService.class);
        Call<Integer> call = dataService.addPrice(breakfast, teatime, lunch, token);
        try {
            Response<Integer> response = call.execute();
            if (response.code() == 200) {
                // Если токен не корректен
                if (response.body() == 107) {
                    changeTokenText("", activity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Отправляет запрос на сервер для получения истории заявок от учеников
    public LinkedList<TreeMap<String, String[]>> getTeacherFood (String token, BaseCompatActivity activity) {
        DataService dataService = retrofit.create(DataService.class);
        Call<MenuForResponse> call = dataService.getTeacherFood(token);
        try {
            Response<MenuForResponse> response = call.execute();
            if (response.code() == 200) {
                MenuForResponse menuForResponse = response.body();
                if (menuForResponse.getCode() == 107) {
                    changeTokenText("", activity);
                }
                return menuForResponse.getTeacherList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Если что-то произошло с данными от сервера, создает заглушку в виде пустого списка
        Calendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LinkedList<TreeMap<String, String[]>> linkedList = new LinkedList<>();
        TreeMap<String, String[]> treeMap = new TreeMap<>();
        for (int i = 1; i <= day; i++) {
            linkedList.add(treeMap);
        }
        return linkedList;
    }

    // Отправляет запрос на сервер для получения истории заявок от самого ученика
    public LinkedList<String[]> getStudentFood (String token, BaseCompatActivity activity) {
        DataService dataService = retrofit.create(DataService.class);
        Call<MenuForResponse> call = dataService.getStudentFood(token);
        try {
            Response<MenuForResponse> response = call.execute();
            if (response.code() == 200) {
                MenuForResponse menuForResponse = response.body();
                if (menuForResponse.getCode() == 107) {
                    changeTokenText("", activity);
                }
                return menuForResponse.getStudentList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Если что-то произошло с данными от сервера, создает заглушку в виде пустого списка
        Calendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LinkedList<String[]> linkedList = new LinkedList<>();
        for (int i = 1; i <= day; i++) {
            linkedList.add(new String[]{"0", "0", "0"});
        }
        return linkedList;
    }

    // Отправляет запрос на сервер с заявкой на текущий день от пользователя
    public Integer sendStudentFood (String breakfast, String teatime, String lunch, String token, BaseCompatActivity activity) {
        DataService dataService = retrofit.create(DataService.class);
        Call<Integer> call = dataService.sendStudentFood(breakfast, teatime, lunch, token);
        try {
            Response<Integer> response = call.execute();
            if (response.code() == 200) {
                if (response.body() == 107) {
                    changeTokenText("", activity);
                }
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 107;
    }

    private void changeTokenText(String text, BaseCompatActivity activity) {
        SharedPreferences sharedPreferences = activity.getContext().getSharedPreferences("token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", text);
        editor.apply();
    }
}
