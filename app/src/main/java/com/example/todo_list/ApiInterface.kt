package com.example.todo_list

import com.example.todo_list.model.Todo
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @POST("app_list/information/deep_sleep")
    fun getData(): Call<List<Todo>>
}