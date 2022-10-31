package com.example.todo_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo_list.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lgbinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(lgbinding.root)
    }
}