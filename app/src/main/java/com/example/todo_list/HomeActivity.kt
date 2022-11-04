package com.example.todo_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_list.databinding.ActivityHomeBinding
import com.example.todo_list.model.ServerResponse
import com.example.todo_list.model.Todo
import com.example.todo_list.recyclerview.TodoRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

const val BASE_URL = "http://220.149.244.206:3003/"

class HomeActivity : AppCompatActivity(), BottomDialogFragment.OnDataPassListener {

    var contentList = arrayListOf<Todo>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        val gemail = intent.getStringExtra("아이디")
        val email = gemail.toString()

        UserData.setUsermail(this,email)

        /*var bottomModifyFragment = BottomModifyFragment()
        var bundle = Bundle()
        bundle.putString("email",email)
        bottomModifyFragment.arguments = bundle*/

        val cltodo = findViewById<ConstraintLayout>(R.id.cl_todo)
        val onlyDate: LocalDate = LocalDate.now()

        homeBinding.floatingActionButton.setOnClickListener {
            val bottomDialogFragment : BottomDialogFragment = BottomDialogFragment()
            bottomDialogFragment.show(supportFragmentManager, bottomDialogFragment.tag)
        }

        homeBinding.ibtnUser.setOnClickListener {
            val intent = Intent(this,UserActivity::class.java)
            intent.putExtra("아이디",email)
            startActivity(intent)
        }

        Log.d("로그", "Home - onCreate")

        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val TodoService = retrofit.create(ApiInterface::class.java)


        // 서버로부터 데이터 가져오기 <getData와 Todo 수정 필요!!!!!!!!>
        TodoService.requestTodoRe(email).enqueue(object : Callback<List<Todo>?>{
            override fun onResponse(call: Call<List<Todo>?>, response: Response<List<Todo>?>) {
                contentList = (response.body() as ArrayList<Todo>?)!!
                Log.d("로그", "onSuccess: $contentList")
                val mAdapter = TodoRecyclerViewAdapter(this@HomeActivity,contentList, onClickDelteIcon = {deleteTask(it)}, onClickmodifyIcon = {modifyTask(it)}
                , onClickTodo = { onClickTodo(it) })
                homeBinding.rvTodo.adapter = mAdapter

                val lm = LinearLayoutManager(this@HomeActivity)
                homeBinding.rvTodo.layoutManager = lm
                homeBinding.rvTodo.setHasFixedSize(true)
            }

            override fun onFailure(call: retrofit2.Call<List<Todo>?>, t: Throwable) {
                Log.d("로그", "onFailure: " + t.message)
            }
            fun deleteTask(todo: Todo){
                TodoService.requestTodoDel(email,todo.title,todo.text,todo.date).enqueue(object : Callback<ServerResponse>{
                    override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>
                    ) {
                        val req = response.body()
                        Toast.makeText(this@HomeActivity,"[${todo.text}]가 삭제되었습니다",Toast.LENGTH_LONG).show()
                        finish() //인텐트 종료

                        overridePendingTransition(0, 0) //인텐트 효과 없애기

                        val intent = intent //인텐트

                        startActivity(intent) //액티비티 열기

                        overridePendingTransition(0, 0) //인텐트 효과 없애기
                    }

                    override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    }

                })

            }
            fun modifyTask(todo: Todo){

                val title = todo.title
                val text = todo.text
                val date = todo.date

                val bottomModifyFragment : BottomModifyFragment = BottomModifyFragment()
                bottomModifyFragment.show(supportFragmentManager, bottomModifyFragment.tag)
                bottomModifyFragment.setOnClickedListener(object : BottomModifyFragment.ButtonClickListener{
                    override fun onClicked(titles: String, todos: String, dates: String) {
                        Log.d("로그","$email,$title,$text,$date,$titles,$todos")
                        var retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

                        val TodoService = retrofit.create(ApiInterface::class.java)
                        TodoService.requestTodoUp(email,title,text,date,titles,todos).enqueue(object : Callback<ServerResponse>{
                            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>
                            ) {
                                Toast.makeText(this@HomeActivity,"수정되었습니다",Toast.LENGTH_LONG).show()
                            }

                            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                            }

                        })
                    }

                })
            }
            fun onClickTodo(todo: Todo){
                todo.completed = 1

                TodoService.requestTodoCh(email,todo.title,todo.text,todo.date,todo.completed).enqueue(object : Callback<ServerResponse>{
                    override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>
                    ) {
                        val req = response.body()

                        homeBinding.rvTodo.adapter?.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    }

                })
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Log.d("로그","HomeActivity - onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.d("로그","HomeActivity - onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("로그","HomeActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("로그","HomeActivity - onStop")
    }

    override fun onDataPass(catalog: String?, todo: String?) {
        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var TodoService = retrofit.create(ApiInterface::class.java)

        // 사용자의 이메일, 비밀번호 받아오기 <--------------------------------------------------- 여기 Preference 필요 (UserData)
        val remail = intent.getStringExtra("아이디")
        val email = remail.toString()

        val onlyDate: LocalDate = LocalDate.now()

        // 9. 할 일 생성
        TodoService.requestCreTodo(email, catalog.toString(), todo.toString()).enqueue(object: Callback<ServerResponse>{
            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.d("카테고리 생성 실패", "서버통신 실패")
            }

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                val resultCreTodo = response.body()

                if (resultCreTodo?.code == 200) {
                    finish() //인텐트 종료

                    overridePendingTransition(0, 0) //인텐트 효과 없애기

                    val intent = intent //인텐트

                    startActivity(intent) //액티비티 열기

                    overridePendingTransition(0, 0) //인텐트 효과 없애기

                } else {
                    Log.d("카테고리 생성 실패", "생성 실패")
                }
            }
        })

    }

}