package com.example.todo_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.todo_list.databinding.ActivitySigninBinding
import com.example.todo_list.model.ServerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sign

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val signBinding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(signBinding.root)

        // 서버 연동
        val url = "서버주소"

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiInterface::class.java)

        // 버튼 클릭 시
        signBinding.btnSidone.setOnClickListener {
            // 회원가입을 완료하면, 로그인 화면으로 이동
            val intent = Intent(this,LoginActivity::class.java)

            // 회원가입 시 이름 & ID & PW 받아오기
            val uName = signBinding.etRealname.text.toString()
            val uId: String = signBinding.etRealemail.text.toString()
            val uPw: String = signBinding.etRealpw.text.toString()

            // 1. 회원가입
            server.requestSignUp(uId, uName, uPw).enqueue(object : Callback<ServerResponse>{
                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    Log.d("회원가입 실패", "회원가입 실패")
                    Toast.makeText(this@SigninActivity, "서버 오류! 회원가입 실패", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    // 서버로부터 응답을 받은 변수
                    val userSignUp = response.body()

                    if(userSignUp?.code == 200){
                        Log.d("회원가입 성공", "회원가입 성공 $uName, $uId, $uPw")
                        startActivity(intent)
                    } else {
                        Log.d("회원가입 실패", "회원가입 실패 $uName, $uId, $uPw")
                    }
                }
            })

//            startActivity(intent)
        }
    }
}