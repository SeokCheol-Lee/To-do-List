package com.example.todo_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import com.example.todo_list.databinding.FragmentBottomDialogBinding
import com.example.todo_list.databinding.FragmentBottomModifyBinding
import com.example.todo_list.model.Category
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BottomDialogFragment() :

    BottomSheetDialogFragment() {

    private var _binding : FragmentBottomDialogBinding? = null
    private val binding get() = _binding!!

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
        ): View?{
            _binding = FragmentBottomDialogBinding.inflate(inflater,container,false)
            val view = binding.root
            return view
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = "http://220.149.244.206:3003/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(ApiInterface::class.java)
        val uEmail = UserData.getUsermail(view.context)

        view.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            var catalog = binding.etCatalog.text.toString()
            var todo = view.findViewById<EditText>(R.id.et_todo).text.toString()
            Log.d("로그","카탈로그 : $catalog 할 일 : $todo")
            dataPassListener.onDataPass(catalog, todo)
            dialog?.dismiss()
        }
        server.requestLoadCtg(uEmail).enqueue(object : Callback<Category> {
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                val req = response.body()
                Log.d("로그","할 일 수정 성공 : ${req!!.title}")
                var itemAdapter : ArrayAdapter<String> = ArrayAdapter(view.context,R.layout.item_list, req!!.title)
                binding.etCatalog.setAdapter(itemAdapter)
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {

            }
        })
    }
}