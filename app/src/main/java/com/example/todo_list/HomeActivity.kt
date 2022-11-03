package com.example.todo_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_list.databinding.ActivityHomeBinding
import com.example.todo_list.model.Todo
import com.example.todo_list.recyclerview.TodoRecyclerViewAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://서버주소/"

class HomeActivity : AppCompatActivity(), BottomDialogFragment.OnDataPassListener {

    var contentList = arrayListOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        homeBinding.floatingActionButton.setOnClickListener {
            val bottomDialogFragment : BottomDialogFragment = BottomDialogFragment()
            bottomDialogFragment.show(supportFragmentManager, bottomDialogFragment.tag)
        }

        homeBinding.ibtnUser.setOnClickListener {
            val intent = Intent(this,UserActivity::class.java)
            startActivity(intent)
        }

        Log.d("로그", "Home - onCreate")

        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var TodoService = retrofit.create(ApiInterface::class.java)
        TodoService.getData().enqueue(object : Callback<List<Todo>?>{
            override fun onResponse(call: retrofit2.Call<List<Todo>?>, response: Response<List<Todo>?>) {
                contentList = (response.body() as ArrayList<Todo>?)!!
                Log.d("로그", "onSuccess: $contentList")
                val mAdapter = TodoRecyclerViewAdapter(this@HomeActivity,contentList, onClickDelteIcon = {deleteTask(it)}, onClickmodifyIcon = {modifyTask(it)})
                homeBinding.rvTodo.adapter = mAdapter

                val lm = LinearLayoutManager(this@HomeActivity)
                homeBinding.rvTodo.layoutManager = lm
                homeBinding.rvTodo.setHasFixedSize(true)

            }

            override fun onFailure(call: retrofit2.Call<List<Todo>?>, t: Throwable) {
                Log.d("로그", "onFailure: " + t.message)
            }
            fun deleteTask(todo: Todo){
                contentList.remove(todo)
                homeBinding.rvTodo.adapter?.notifyDataSetChanged()
                Toast.makeText(baseContext,"todo가 삭제됨 : $todo",Toast.LENGTH_LONG).show()
                try {
                    val intent = intent
                    finish() //현재 액티비티 종료 실시
                    overridePendingTransition(0, 0) //인텐트 애니메이션 없애기
                    startActivity(intent) //현재 액티비티 재실행 실시
                    overridePendingTransition(0, 0) //인텐트 애니메이션 없애기
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            fun modifyTask(todo: Todo){

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
        Toast.makeText(this,"카탈로그 : $catalog 할 일 : $todo", Toast.LENGTH_LONG).show()
    }

}