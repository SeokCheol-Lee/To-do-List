package com.example.todo_list.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.R
import com.example.todo_list.model.Todo

class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val todo = itemView.findViewById<TextView>(R.id.tv_todoname)
    private val tododate = itemView.findViewById<TextView>(R.id.tv_todoDate)

    fun bindWithView(todoItem : Todo){
        todo.text = todoItem.todo
        tododate.text = todoItem.date

    }
}