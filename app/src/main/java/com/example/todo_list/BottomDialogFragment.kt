package com.example.todo_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomDialogFragment() :

    BottomSheetDialogFragment() {
    interface  OnDataPassListener {
        fun onDataPass(catalog: String?, todo: String?)
    }

    lateinit var dataPassListener: OnDataPassListener

    override fun onAttach(context: Context) {

        super.onAttach(context)

        dataPassListener = context as OnDataPassListener
    }
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View =
            inflater.inflate(R.layout.fragment_bottom_dialog, container, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            var catalog = view.findViewById<EditText>(R.id.et_catalog).text.toString()
            var todo = view.findViewById<EditText>(R.id.et_todo).text.toString()
            Log.d("로그","카탈로그 : $catalog 할 일 : $todo")
            dataPassListener.onDataPass(catalog, todo)
            dialog?.dismiss()
        }
    }
}