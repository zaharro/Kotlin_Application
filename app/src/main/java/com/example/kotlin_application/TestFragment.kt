package com.example.kotlin_application

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kotlin_application.databinding.FragmentTestBinding
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class TestFragment : Fragment() {

    //Use view binding in fragments
    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    var totalCorrectAnswers = 0

    //private lateinit var dbHelper: QuestionsDBHelper
    private var currentId = 1
    private val RESULTS = "results"
    private lateinit var db: AppDatabase

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

        db = AppDatabase.getDatabase(requireActivity())

        //dbHelper = QuestionsDBHelper(requireContext())

        // Инициализируем базу, если пустая
        lifecycleScope.launch {
            val dao = db.questionsDao()
            val count = dao.getQuestionById(1) // проверим элемент с ID 1
            if (count == null) {
                val questions = listOf(
                    Questions(
                        1,
                        "Создает и разрабатывает компьютерные программы, приложения и веб-сайты. Часто работает с кодом и решает технические задачи.",
                        "Программист",
                        "Врач",
                        "Учитель",
                        1
                    ),

                    Questions(
                        2,
                        "Занимается лечением больных, ставит диагнозы и назначает лечение. Работает в больницах, поликлиниках или частных клиниках.",
                        "Архитектор",
                        "Врач",
                        "Юрист",
                        2
                    ),

                    Questions(
                        3,
                        "Проектирует и строит здания, дома и сооружения. Учитывает функциональность, безопасность и эстетику.",
                        "Архитектор",
                        "Повар",
                        "Стоматолог",
                        1
                    ),

                    Questions(
                        4,
                        "Защищает интересы клиентов в суде, консультирует по юридическим вопросам и составляет юридические документы.",
                        "Юрист",
                        "Актер",
                        "Фермер",
                        1
                    ),

                    Questions(
                        5,
                        "Передаёт знания, обучает и воспитывает детей в школе или других образовательных учреждениях.",
                        "Инженер",
                        "Дизайнер",
                        "Педагог",
                        3
                    )
                )

                dao.insertAll(questions)
            }
            showQuestion(currentId)
        }


        totalCorrectAnswers = 0
        binding.questionNumber.text = "Вопрос ${currentId}"

        var correct = 0
        lifecycleScope.launch {
            correct = showQuestion(currentId)
        }

        binding.buttonSubmit.setOnClickListener {
            if (binding.answer1.isChecked || binding.answer2.isChecked || binding.answer3.isChecked) {
                currentId++
                binding.questionNumber.text = "Вопрос ${currentId}"
                if (currentId > 5) {
                    selectAnswer(correct)
                    saveSharedPreferences(totalCorrectAnswers)
                    findNavController().navigate(R.id.action_testFragment_to_resultsFragment)
                }
                selectAnswer(correct)
                lifecycleScope.launch {
                    correct = showQuestion(currentId)
                }

                binding.radioGroup1.clearCheck()
            }

        }
    }

    private fun selectAnswer(correct: Int) {

        val selectedOption: Int = binding.radioGroup1.checkedRadioButtonId
        /* Radiobuttons ids:
      2131230805 - 1st button
      2131230806 - 2nd button
      2131230807 - 3rd button*/

        val chosenAnswer = selectedOption - 2131230805 + 1
        if (chosenAnswer == correct)
            totalCorrectAnswers++
    }

    private suspend fun showQuestion(id: Int): Int {
        var correct = 0
        val question = db.questionsDao().getQuestionById(id)
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

        val sharedPreferences =
            requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit() {

            putInt(RESULTS, totalCorrectAnswers)
        }

    }

}