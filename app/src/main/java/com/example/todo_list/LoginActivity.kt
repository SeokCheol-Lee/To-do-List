package com.example.todo_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.todo_list.databinding.ActivityLoginBinding
import com.example.todo_list.model.ServerResponse
import com.example.todo_list.model.Todo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lgBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(lgBinding.root)

        // 서버 연동
        val url = "서버주소"

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiInterface::class.java)



        lgBinding.btnLglogin.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)

            // 로그인 시 입력하는 이메일 & PW 받아오기
            val uid: String = findViewById<EditText>(R.id.et_email).text.toString()
            val upw: String = findViewById<EditText>(R.id.et_pw).text.toString()

            // 서버로 ID & PW 보내기
//            server.requestSignIn(uid, upw).enqueue(object : Callback<Todo>{
//                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onResponse(call: Call<Todo>, response: Response<ServerResponse>) {
//                    TODO("Not yet implemented")
//                    val userLogin = response.body()
//
//                }
//            })

            startActivity(intent)
        }
    }
}