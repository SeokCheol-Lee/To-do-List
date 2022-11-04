package com.example.todo_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.todo_list.databinding.FragmentBottomModifyBinding
import com.example.todo_list.model.Category
import com.example.todo_list.model.ServerResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

class BottomModifyFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var _binding : FragmentBottomModifyBinding? = null
    private val binding get() = _binding!!
//    interface  OnDataPassListener2 {
//      fun onDataPass2(catalog: String?, todo: String?)
//    }
//
//    lateinit var dataPassListener: OnDataPassListener2

    interface ButtonClickListener{
        fun onClicked(title : String, todo : String, date:String)
    }
    private lateinit var onClickListener: ButtonClickListener
    fun setOnClickedListener(listener: ButtonClickListener){
        onClickListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onAttach(context: Context) {

        super.onAttach(context)

//        dataPassListener = context as OnDataPassListener2
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomModifyBinding.inflate(inflater,container,false)
        val view = binding.root
        return view

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = "http://220.149.244.206:3003/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(ApiInterface::class.java)
        val txitem = view.findViewById<TextView>(R.id.text_item2)

//        val uEmail = UserData.getUsermail(App.instance)
        val Email = arguments?.getString("이메일")
        val uEmail = UserData.getUsermail(view.context)
        Log.d("로그","modify에서 받은 이메일 : $uEmail")

        val email = arguments?.getString("email")
        Log.d("로그","modify에서 받은 e메일 : ${UserData.getUsermail(view.context)}")

        //카테고리 보여주기

            server.requestLoadCtg(uEmail).enqueue(object : Callback<Category> {
                override fun onResponse(call: Call<Category>, response: Response<Category>) {
                    val req = response.body()
                    Log.d("로그","할 일 수정 성공 : ${req!!.title}")
                    var itemAdapter : ArrayAdapter<String> = ArrayAdapter(view.context,R.layout.item_list, req!!.title)
                    binding.textItem2.setAdapter(itemAdapter)
                }

                override fun onFailure(call: Call<Category>, t: Throwable) {

                }
            })

        binding.btnModisubmit.setOnClickListener {
            val onlyDate: LocalDate = LocalDate.now()
            val todo = binding.etModitodo.text.toString()
            val cate = binding.textItem2.text.toString()

            onClickListener.onClicked(cate,todo,onlyDate.toString())
            dialog?.dismiss()

        }
    }



}