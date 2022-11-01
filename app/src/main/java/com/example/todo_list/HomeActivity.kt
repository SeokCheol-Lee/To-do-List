package com.example.todo_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todo_list.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), BottomDialogFragment.OnDataPassListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        homeBinding.floatingActionButton.setOnClickListener {
            val bottomDialogFragment : BottomDialogFragment = BottomDialogFragment()
            bottomDialogFragment.show(supportFragmentManager, bottomDialogFragment.tag)
        }

    }

    override fun onDataPass(catalog: String?, todo: String?) {
        Toast.makeText(this,"카탈로그 : $catalog 할 일 : $todo", Toast.LENGTH_LONG).show()
    }
}