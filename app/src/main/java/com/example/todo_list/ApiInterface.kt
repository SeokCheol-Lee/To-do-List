package com.example.todo_list

import com.example.todo_list.model.Todo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

public interface ApiInterface {
    @POST("app_list/information/deep_sleep")
    fun getData(): Call<List<Todo>>

    // 1. 회원가입
    @FormUrlEncoded
    @POST("/highdb/sign_up")
    fun requestSignUp(
        @Field("email") email:String,
        @Field("name") name:String,
        @Field("password") password:String
    ) : Call<ServerResponse>

    // 2. 로그인
    @FormUrlEncoded
    @POST("/highdb/sign_in")
    fun requestSignIn(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<ServerResponse>

    // 3. 비밀번호 변경
    @FormUrlEncoded
    @POST("/highdb/pw")
    fun requestChgPw(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("chgPw") chgPw: String
    ) : Call<ServerResponse>

    // 4. 회원탈퇴
    @FormUrlEncoded
    @POST("/highdb/sign_del")
    fun requestWid(
        @Field("email") email: String,
        @Field("password") password: String
    )  : Call<ServerResponse>

    // 5. 카테고리 생성
    @FormUrlEncoded
    @POST("/highdb/cat_cre")
    fun requestCreCtg(
        @Field("email") email: String,
        @Field("title") title: String
    ) : Call<ServerResponse>

    // 6. 카테고리 불러오기 (포스트인지 아닌지 모름!!!)
    @FormUrlEncoded
    @POST("/highdb/cat_re")
    fun requestLoadCtg(
        @Field("email") email: String
    ) : Call<ServerResponse>

    // 7. 카테고리 수정 (포스트인지 아닌지 모름!!!)
    @FormUrlEncoded
    @POST("/highdb/cat_up")
    fun requestUpCtg(
        @Field("email") email: String,
        @Field("title") title: String,
        @Field("chgTitle") chgTitle: String
    ) : Call<ServerResponse>

    // 8. 카테고리 삭제 (포스트인지 아닌지 모름!!!)
    @FormUrlEncoded
    @POST("/highdb/cat_del")
    fun requestDelCtg(
        @Field("email") email: String,
        @Field("title") title: String
    ) : Call<ServerResponse>

    // 9. 할 일 생성 (date와 text, cat_title이 중복되면 안됨)
    @FormUrlEncoded
    @POST("/highdb/todo_cre")
    fun requestCreTodo(
        @Field("email") email: String,
        @Field("category_title") ctg_title: String,
        @Field("todo_text") todo_text: String,
        @Field("todo_date") todo_date: String
    ) : Call<ServerResponse>

    // 10. 할 일 수정
    @FormUrlEncoded
    @POST("/highdb/todo_up")
    fun requestTodoUp(
        @Field("email") email: String,
        @Field("category_title") ctg_title: String,
        @Field("todo_text") todo_text: String,
        @Field("todo_date") todo_date: String,
        @Field("chgCtg") chgCtg: String
    ) : Call<ServerResponse>

    // 11. 할 일 삭제
    @FormUrlEncoded
    @POST("/highdb/todo_del")
    fun requestTodoDel(
        @Field("email") email: String,
        @Field("category_title") ctg_title: String,
        @Field("todo_text") todo_text: String,
        @Field("todo_date") todo_date: String,
    ) : Call<ServerResponse>
}