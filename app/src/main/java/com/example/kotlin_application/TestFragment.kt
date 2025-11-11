package com.example.kotlin_application


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kotlin_application.databinding.FragmentTestBinding


class TestFragment : Fragment() {

    //Use view binding in fragments
    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    private var totalCorrectAnswers = 0
    private lateinit var dbHelper: QuestionsDBHelper
    private var currentId = 1
    private val RESULTS = "results"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = QuestionsDBHelper(requireContext())
        binding.questionNumber.text = "Вопрос ${currentId}"

        var correct = showQuestion(currentId)

        binding.buttonSubmit.setOnClickListener {
            if (binding.answer1.isChecked || binding.answer2.isChecked || binding.answer3.isChecked) {
                currentId++
                binding.questionNumber.text = "Вопрос ${currentId}"
                if (currentId > 5) {
                    saveSharedPreferences(totalCorrectAnswers)
                    findNavController().navigate(R.id.action_testFragment_to_resultsFragment)
                }
                val selectedOption: Int = binding.radioGroup1.checkedRadioButtonId
                /* Radiobuttons ids:
              2131230805 - 1st button
              2131230806 - 2nd button
              2131230807 - 3rd button*/

                val chosenAnswer = selectedOption - 2131230805 + 1
                if (chosenAnswer == correct)
                    totalCorrectAnswers++

                correct = showQuestion(currentId)

                binding.radioGroup1.clearCheck()
            }

        }
    }

    private fun showQuestion(id: Int): Int {
        var correct = 0
        val question = dbHelper.getQuestionById(id)
        question?.let {
            binding.question.text = it.question
            binding.answer1.text = it.firstAnswer
            binding.answer2.text = it.secondAnswer
            binding.answer3.text = it.thirdAnswer
            correct = it.correctAnswer
        }
        return correct
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun saveSharedPreferences(totalCorrectAnswers: Int) {

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt(RESULTS, totalCorrectAnswers)
            apply()
        }
    }

}