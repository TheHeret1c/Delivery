package com.example.delivery.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentAddPayMethodBinding
import com.example.delivery.databinding.FragmentBlank3Binding

class BlankFragment3 : Fragment() {
    private var _binding : FragmentBlank3Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBlank3Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()

        val btnSignup = binding.f3btnSignUp
        val btnSignin = binding.f3btnSignIn

        btnSignup.setOnClickListener {
            controller.navigate(R.id.signUpFragment)
        }
        btnSignin.setOnClickListener {
            controller.navigate(R.id.logInFragment)
        }
    }
}