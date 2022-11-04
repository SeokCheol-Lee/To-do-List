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


        // 사용자의 이메일, 비밀번호 받아오기 <--------------------------------------------------- 여기 Preference 필요 (UserData)
        val uEmail = "email"
        val uPw = "pw"

        var textLayout = userbinding.muCate
        var txitem = userbinding.textItem



        // 4. 회원탈퇴
        userbinding.ibtnUserdelte.setOnClickListener {
            server.requestWid(uEmail, uPw).enqueue(object: Callback<ServerResponse>{
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
                    val intent = Intent(this@UserActivity, LoginActivity::class.java)
                    val resultChgPw = response.body()

                    // 비밀번호 변경은 로그인 화면으로 이동
                    if (resultChgPw?.code == 200){
                        startActivity(intent)
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
                    val intent = Intent(this@UserActivity, UserActivity::class.java)
                    val resultCreCtg = response.body()

                    if(resultCreCtg?.code == 200){
                        startActivity(intent)
                    } else {
                        Log.d("카테고리 생성 실패", "생성 실패")
                    }
                }
            })
        }

        var items = mutableListOf("과제","수업","약속")

        // 6. 카테고리 불어오기
        server.requestLoadCtg(uEmail).enqueue(object :Callback<ServerResponse>{
            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.d("카테고리 불러오기 실패", "서버통신 실패")
            }

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                // 서버에서 데이터 받아오기 <----------------------------------------------------- 변경 필요
                val resultLoadCtg = response.body()

                if(resultLoadCtg?.code == 200){
                    // 변경 필요 부분! <-------------------------------------------------------
                    // 서버에서 가져온 카테고리 추가
                    items.add(resultLoadCtg?.ctg)
                }
            }
        })

        var itemAdapter : ArrayAdapter<String> = ArrayAdapter<String>(this,R.layout.item_list,items)
        txitem.setAdapter(itemAdapter)

        // 8. 카테고리 삭제
        userbinding.btnDelcate.setOnClickListener {
            var delcate = txitem.text.toString()
//            Toast.makeText(this,"$delcate",Toast.LENGTH_LONG).show()
            server.requestDelCtg(uEmail, delcate).enqueue(object : Callback<ServerResponse>{
                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    Log.d("카테고리 삭제 실패", "서버통신 실패")
                }

                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    // 새로고침 코드 <------------------------------------------------------------ 수정 필요??
                    val intent = Intent(this@UserActivity, UserActivity::class.java)
                    val resultDelCtg = response.body()

                    if(resultDelCtg?.code == 200){
                        startActivity(intent)
                    } else {
                        Log.d("카테고리 삭제 실패", "삭제 실패")
                    }
                }
            })
        }

        // 돌아가기 버튼 클릭
        userbinding.btnBack.setOnClickListener {
            finish()
        }

    }
}