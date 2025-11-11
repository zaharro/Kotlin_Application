package com.example.kotlin_application

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_application.databinding.FragmentAuthBinding
import com.example.kotlin_application.databinding.FragmentResultsBinding

private var USERNAME = "user_name"

class ResultsFragment : Fragment() {

    //Use view binding in fragments
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_results, container, false)
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val userName = sharedPref.getString(USERNAME, "")
        /*binding.userName.text = userName*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}