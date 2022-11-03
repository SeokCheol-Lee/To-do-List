package com.example.todo_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.todo_list.databinding.ActivityUserBinding
import com.example.todo_list.model.ServerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.FieldPosition

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userbinding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(userbinding.root)

        // 서버 연동
        val url = "서버주소"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(ApiInterface::class.java)


        // 사용자의 이메일, 비밀번호 받아오기
        val uEmail = "email"
        val uPw = "pw"

        var textLayout = userbinding.muCate
        var txitem = userbinding.textItem

        // 회원탈퇴 버튼 클릭 시
        userbinding.ibtnUserdelte.setOnClickListener {
            server.requestWid(uEmail, uPw).enqueue(object: Callback<ServerResponse>{
                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    val intent = Intent(this@UserActivity, MainActivity::class.java)
                    val delUser = response.body()

                    // 메인화면으로 이동
                    if (delUser?.code == 200) {
                        startActivity(intent)
                    } else {
                        Log.d("실패", "회원가입 실패")
                    }
                }
            })
        }

        // 카테고리 삭제 시 나오는 카테고리들
        var items = listOf("과제","수업","약속")
        var itemAdapter : ArrayAdapter<String> = ArrayAdapter<String>(this,R.layout.item_list,items)
        txitem.setAdapter(itemAdapter)

        userbinding.btnDelcate.setOnClickListener {
            var delcate = txitem.text.toString()
            Toast.makeText(this,"$delcate",Toast.LENGTH_LONG).show()
        }

        // 돌아가기 버튼 클릭
        userbinding.btnBack.setOnClickListener {
            finish()
        }

    }
}