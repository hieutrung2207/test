package com.example.myapplication.API;

import com.example.myapplication.Modal.ItemsResult;
import com.example.myapplication.Modal.UserModal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APIRetrofit {

    String url = "http://192.168.1.9/MOD403_ASM/";

    Gson gson = new GsonBuilder().setLenient().create();
    APIRetrofit apiRtf = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIRetrofit.class);

    @GET("register.php")
    Call<UserModal> Reg(
            @Query("username") String username,
            @Query("password") String password,
            @Query("email") String email
    );

    @GET("login.php")
    Call<UserModal> Login(
            @Query("email") String email,
            @Query("password") String password

    );

    @GET("changePass.php")
    Call<UserModal> ChangePass(
            @Query("email") String email,
            @Query("password") String password,
            @Query("newPassword") String passNew

    );

    @GET("showItems.php")
    Call<ItemsResult> showItems(


    );
    @GET("insertItem.php")
    Call<ItemsResult> insertItem(
            @Query("name") String name,
            @Query("price") Double price,
            @Query("brand") String brand


    );
    @GET("deleteItem.php")
    Call<ItemsResult> deleteItem(
            @Query("pid") int pid

    );
    @GET("updateItem.php")
    Call<ItemsResult> updateItem(
            @Query("pid") int pid,
            @Query("name") String name,
            @Query("price") Double price,
            @Query("brand") String brand

    );

}
