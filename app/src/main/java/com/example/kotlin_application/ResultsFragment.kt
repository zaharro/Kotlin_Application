package com.example.kotlin_application

import android.content.Context

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlin.system.exitProcess

import com.example.kotlin_application.databinding.FragmentResultsBinding

private val USERNAME = "user_name"
private val RESULTS = "results"

class ResultsFragment : Fragment() {

    //Use view binding in fragments
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractResults()

        binding.yesButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultsFragment_to_testFragment)
        }

        binding.noButton.setOnClickListener { exitProcess(0) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun extractResults() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString(USERNAME, " ")
        val userResult = sharedPreferences.getInt(RESULTS, 0).toInt()

        when (userResult) {
            5 -> excellentResults(userName.toString())
            in 0..2 -> badResults(userName.toString())
            else -> moderateResults(userName.toString())
        }

       /* val question = when (userResult) {
            5 -> "вопросов"
            in 2..4 -> "вопроса"
            1 -> "вопрос"
            0 -> "вопросов"
            else -> "вопросов"
        }*/
        binding.results.text = userResult.toString()//"Вы правильно ответили на\n ${userResult}\n $question"
    }

    fun excellentResults(userName: String) {
        binding.userName.text = "Отличный результат, ${userName}!"
    }

    fun moderateResults(userName: String) {
        binding.userName.text = "Неплохой результат, ${userName}!"
    }

    fun badResults(userName: String) {
        binding.userName.text = "Не отчаивайтесь, ${userName}!\nПопробуйте еще раз!"
    }

}



