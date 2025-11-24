package com.example.kotlin_application

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kotlin_application.databinding.FragmentAuthBinding
import android.widget.Toast
import androidx.core.view.get
import androidx.core.content.edit

// Разместить во фрагменте форму для ввода по Material Design

class AuthFragment : Fragment() {

    //Use view binding in fragments
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val USERNAME = "user_name"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }
    fun saveSharedPreferences(userName : String) {

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit() {
            putString(USERNAME, userName)
        }


        findNavController().navigate(R.id.action_authFragment_to_testFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submit.setOnClickListener {
            val userName = binding.enterName.text.toString()
            if (userName.trim().isEmpty()) {
                binding.enterName.error = "Обязательное поле!"
                binding.enterName.requestFocus()
                return@setOnClickListener
            } else saveSharedPreferences(userName)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AuthFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}