package com.example.todo_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo_list.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.btnLogin.setOnClickListener {
            val intent : Intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        mainBinding.btnSignin.setOnClickListener {
            val intent : Intent = Intent(this,SigninActivity::class.java)
            startActivity(intent)
        }

    }
}