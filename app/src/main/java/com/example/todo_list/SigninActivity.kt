package com.example.todo_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo_list.databinding.ActivitySigninBinding
import kotlin.math.sign

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val signBinding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(signBinding.root)


    }
}