package com.example.todo_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.todo_list.databinding.ActivityUserBinding
import com.example.todo_list.model.Category
import com.example.todo_list.model.ServerResponse
import com.google.gson.JsonElement
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
        val url = "http://220.149.244.206:3003/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(ApiInterface::class.java)


        // 사용자의 이메일, 비밀번호 받아오기 <--------------------------------------------------- 여기 Preference 필요 (UserData)
        val gemail = intent.getStringExtra("아이디")
        val uEmail = gemail.toString()
        val uPw = UserData.getUserPass(this)

        var textLayout = userbinding.muCate
        var txitem = userbinding.textItem



        // 4. 회원탈퇴
        userbinding.ibtnUserdelte.setOnClickListener {
            server.requestWid(uEmail).enqueue(object: Callback<ServerResponse>{
                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    Log.d("회원탈퇴 실패", "서버 통신 실패")
                }

                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    val intent = Intent(this@UserActivity, MainActivity::class.java)
                    val resultDelUser = response.body()

                    // 회원탈퇴 시 메인화면으로 이동
                    if (resultDelUser?.code == 200) {
                        startActivity(intent)
                    } else {
                        Log.d("회원탈퇴 실패", "회원탈퇴 실패")
                    }
                }
            })
        }



        // 3. 비밀번호 변경
        userbinding.btnModipw.setOnClickListener{
            val originEmail = findViewById<EditText>(R.id.et_useremail).text.toString()
            val originPw = findViewById<EditText>(R.id.et_conpw).text.toString()
            val chgPw = findViewById<EditText>(R.id.et_modipw).text.toString()

            server.requestChgPw(originEmail, originPw, chgPw).enqueue(object : Callback<ServerResponse>{
                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    Log.d("비밀번호 변경 실패", "서버통신 실패")
                }

                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    val resultChgPw = response.body()
                    // 비밀번호 변경은 로그인 화면으로 이동
                    if (resultChgPw?.code == 200){
                        Toast.makeText(this@UserActivity,"비밀번호가 변경되었습니다",Toast.LENGTH_LONG).show()
                    } else {
                        Log.d("비밀번호 변경 실패", "변경 실패")
                    }
                }
            })
        }



        // 5. 카테고리 생성
        userbinding.btnAddcate.setOnClickListener{
            val addCtg = findViewById<EditText>(R.id.et_addcate).text.toString()

            server.requestCreCtg(uEmail, addCtg).enqueue(object : Callback<ServerResponse>{
                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    Log.d("카테고리 생성 실패", "서버통신 실패")
                }

                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    // 새로고침 코드 <------------------------------------------------------------ 수정 필요??
                    val resultCreCtg = response.body()
                    if(resultCreCtg?.code == 200){
                        Toast.makeText(this@UserActivity,"[$addCtg]카테고리가 생성되었습니다",Toast.LENGTH_LONG).show()
                    } else {
                        Log.d("카테고리 생성 실패", "생성 실패")
                    }
                }
            })
        }
        val itemList : ArrayList<String>
        // 6. 카테고리 불어오기

            Log.d("로그","카테고리 리스트 Press")
            server.requestLoadCtg(uEmail).enqueue(object : Callback<Category>{
                override fun onResponse(call: Call<Category>, response: Response<Category>) {
                    val req = response.body()
                    Log.d("로그","카테고리 리스트 : ${req?.title}")
                    var itemAdapter : ArrayAdapter<String> = ArrayAdapter(this@UserActivity,R.layout.item_list, req!!.title)
                    txitem.setAdapter(itemAdapter)

                }

                override fun onFailure(call: Call<Category>, t: Throwable) {
                    Log.d("로그","카테고리 불러오기 실패 : ${t.message}")
                }
            })


        // 8. 카테고리 삭제
        userbinding.btnDelcate.setOnClickListener {
            var delcate = txitem.text.toString()
            server.requestDelCtg(uEmail, delcate).enqueue(object : Callback<ServerResponse>{
                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    Log.d("카테고리 삭제 실패", "서버통신 실패")
                }

                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    // 새로고침 코드 <------------------------------------------------------------ 수정 필요??
                    val resultDelCtg = response.body()
                    if(resultDelCtg?.code == 200){
                        Toast.makeText(this@UserActivity,"[$delcate]카테고리가 삭제되었습니다",Toast.LENGTH_LONG).show()
                    } else {
                        Log.d("카테고리 삭제 실패", "삭제 실패")
                    }
                }
            })
        }
        userbinding.btn1.setOnClickListener {
            server.sql1("sql1").enqueue(object : Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                }

            })
        }
        userbinding.btn2.setOnClickListener {
            server.sql2("sql2").enqueue(object : Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                }

            })
        }
        userbinding.btn3.setOnClickListener {
            server.sql3("sql3").enqueue(object : Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                }

            })
        }
        userbinding.btn4.setOnClickListener {
            server.sql1("sql4").enqueue(object : Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                }

            })
        }
        userbinding.btn5.setOnClickListener {
            server.sql1("sql5").enqueue(object : Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                }

            })
        }

        // 돌아가기 버튼 클릭
        userbinding.btnBack.setOnClickListener {
            finish()
        }

    }
}