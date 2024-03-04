package com.example.delivery.onboarding

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.delivery.R
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.delivery.utils.impl.LocalRepositoryImpl

class SplashScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            val controller = findNavController()

            if (LocalRepositoryImpl(requireContext()).getIsFirstExec()){
                controller.navigate(R.id.fragment1)
            } else {
                controller.navigate(R.id.fragmentAuthorization)
            }
            Log.e("TEST", "First - " + LocalRepositoryImpl(requireContext()).getIsFirstExec())

        }, 2000)
    }
}