package com.example.delivery.navigation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentSplashBinding
import com.example.delivery.utils.impl.LocalRepositoryImpl

class splashFragment : Fragment() {

    private var _binding :FragmentSplashBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            val controller = findNavController()
            //LocalRepositoryImpl(requireContext()).setIsFirstExec(true)

            if (LocalRepositoryImpl(requireContext()).getIsFirstExec()){
                controller.navigate(R.id.blankFragment1)
            } else {
                controller.navigate(R.id.logInFragment)
            }
            Log.e("TEST", "First - " + LocalRepositoryImpl(requireContext()).getIsFirstExec())

        }, 2000)
    }
}