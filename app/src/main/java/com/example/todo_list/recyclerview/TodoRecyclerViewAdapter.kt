package com.example.todo_list.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.R
import com.example.todo_list.model.Todo

class TodoRecyclerViewAdapter(val context: Context, val contentList: List<Todo>,
    val onClickDelteIcon:(todo:Todo)->Unit, val onClickmodifyIcon:(todo:Todo)->Unit) : RecyclerView.Adapter<TodoRecyclerViewAdapter.ViewHolder>() {


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val todoname = itemView?.findViewById<TextView>(R.id.tv_todoname)
        val cate = itemView?.findViewById<TextView>(R.id.tv_catalog)
        val date = itemView?.findViewById<TextView>(R.id.tv_todoDate)
        val delte = itemView?.findViewById<ImageButton>(R.id.ibtn_delete)
        val modi = itemView?.findViewById<ImageButton>(R.id.ibtn_modify)
        fun bind(todo: Todo, context: Context){
            todoname?.text = todo.todo
            cate?.text = todo.cate
            date?.text = todo.date
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.to_do,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listpoition = contentList[position]
       holder?.bind(contentList[position],context)
        holder.delte?.setOnClickListener {
            onClickDelteIcon.invoke(listpoition)
        }
        holder?.modi?.setOnClickListener {
            onClickmodifyIcon.invoke(listpoition)
        }
    }

    override fun getItemCount(): Int {
        return contentList.size
    }
}