package com.example.todo_list.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.R
import com.example.todo_list.model.Todo

class TodoRecyclerViewAdapter : RecyclerView.Adapter<TodoItemViewHolder>() {

    private var todoList = ArrayList<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val todoviewHolder = TodoItemViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.to_do,parent,false))
        return todoviewHolder
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bindWithView(this.todoList[position])
    }

    override fun getItemCount(): Int {
        return this.todoList.size
    }
    fun submitList(photoList:ArrayList<Todo>){
        this.todoList = photoList
    }
}