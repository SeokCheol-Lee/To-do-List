package com.example.todo_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.todo_list.databinding.ActivityUserBinding
import java.text.FieldPosition

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userbinding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(userbinding.root)

        var textLayout = userbinding.muCate
        var txitem = userbinding.textItem

        var items = listOf("카테1","로아","카트")
        var itemAdapter : ArrayAdapter<String> = ArrayAdapter<String>(this,R.layout.item_list,items)
        txitem.setAdapter(itemAdapter)

        userbinding.btnDelcate.setOnClickListener {
            var delcate = txitem.text.toString()
            Toast.makeText(this,"$delcate",Toast.LENGTH_LONG).show()
        }
        userbinding.btnBack.setOnClickListener {
            finish()
        }

    }
}