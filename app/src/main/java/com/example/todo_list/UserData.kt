package com.example.todo_list

import android.content.Context
import android.content.SharedPreferences

object UserData {
    private val myAccount: String = "account"

    fun setUserId(context: Context, input: String){
        val prefs: SharedPreferences = context. getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("myId", input)
        editor.commit()
    }

    fun getUserId(context: Context): String{
        val prefs: SharedPreferences = context.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        return prefs.getString("myId", "").toString()
    }
}