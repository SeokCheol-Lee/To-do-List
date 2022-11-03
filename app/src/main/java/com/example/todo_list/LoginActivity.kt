package com.example.todo_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.todo_list.databinding.ActivityLoginBinding
import com.example.todo_list.model.ServerResponse
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
            server.requestSignIn(uid, upw).enqueue(object : Callback<ServerResponse> {
                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    Log.d("로그인 실패", "로그인 실패")
                    Toast.makeText(this@LoginActivity, "서버 오류! 로그인 실패", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    val userLogin = response.body()

                    if (userLogin?.code == 200){
                        Log.d("로그인 성공", "로그인 성공 $uid, $upw")
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "가입된 계정이 아닙니다!", Toast.LENGTH_LONG).show()
                    }
                }
            })

//            startActivity(intent)
        }
    }
}