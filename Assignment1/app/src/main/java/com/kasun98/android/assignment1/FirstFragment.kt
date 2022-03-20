package com.kasun98.android.assignment1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kasun98.android.assignment1.api.UserAPIService
import com.kasun98.android.assignment1.databinding.FragmentFirstBinding


import android.util.Log
import com.kasun98.android.assignment1.model.model.User

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userAPIService = UserAPIService.create()
    //User Details
    val arrayList = ArrayList<String>()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
           super.onViewCreated(view, savedInstanceState)


            binding.buttonFirst.setOnClickListener {
            val editText = binding.editTextTextPersonName.editableText


            val user = userAPIService.getUser(editText.toString())


            user.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {

                    val body = response.body()
                    body?.let {
                        Log.i("FirstFragment", it.name)
                        arrayList.add(it.id.toString())
                        arrayList.add(it.name)
                        arrayList.add(it.username)
                        arrayList.add(it.email)//Adding


                    }
                   findNavController().navigate(R.id.SecondFragment,Bundle().apply {
                       putStringArrayList("userDetails",arrayList)
                   })
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.i("FirstFragment", "error")
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}