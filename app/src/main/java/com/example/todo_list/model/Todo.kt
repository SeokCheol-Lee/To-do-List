package com.example.todo_list.model

import java.io.Serializable

data class Todo(
    var text : String,
    var date : String,
    var title : String,
    var completed : Int)