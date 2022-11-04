package com.example.todo_list

import android.content.Context
import android.content.SharedPreferences

object UserData {
    private val myAccount: String = "account"

    fun setUserPass(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PASS", input)
        editor.commit()
    }

    fun getUserPass(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        return prefs.getString("MY_PASS", "").toString()
    }
    fun setUsermail(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_MAIL", input)
        editor.commit()
    }

    fun getUsermail(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        return prefs.getString("MY_MAIL", "").toString()
    }
}