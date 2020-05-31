package com.example.myfood.data;

import com.example.myfood.data.models.Classmate;
import com.example.myfood.data.models.MenuForResponse;
import com.example.myfood.data.models.UserForResponse;
import com.example.myfood.data.models.TokenForResponse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {

    @POST("user/signup")
    Call<Integer> signUp(@Query("email") String email, @Query("password") String password, @Query("name") String name, @Query("group_num") String groupNum, @Query("class_num") String classNum, @Query("school_num") String schoolNum);

    @GET("user/login")
    Call<TokenForResponse> logIn(@Query("email") String email, @Query("password") String password);

    @GET("user/information")
    Call<UserForResponse> getInformation(@Query("token") String token);

    @GET("group/class")
    Call<ArrayList<Classmate>> getListClassmates(@Query("token") String token, @Query("group_num") String groupMun);

    @POST("group/add_price")
    Call<Integer> addPrice(@Query("breakfast") Integer breakfast, @Query("teatime") Integer teatime, @Query("lunch") Integer lunch, @Query("token") String token);

    @GET("menu/get")
    Call<MenuForResponse> getTeacherFood (@Query("token") String token);

    @GET("menu/get")
    Call<MenuForResponse> getStudentFood (@Query("token") String token);

    @POST("menu/add")
    Call<Integer> sendStudentFood(@Query("breakfast") String breakfast, @Query("teatime") String teatime, @Query("lunch") String lunch, @Query("token") String token);
}
