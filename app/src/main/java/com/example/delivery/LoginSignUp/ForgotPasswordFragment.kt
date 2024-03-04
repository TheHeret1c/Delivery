package com.example.delivery.LogInSugnUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentForgotPasswordBinding
import com.example.delivery.utils.impl.DataBaseImpl
import com.example.delivery.utils.impl.LocalRepositoryImpl

class ForgotPasswordFragment : Fragment() {

    private var _binding : FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()

        binding.fforgotBtnSendOTP.setOnClickListener {
            LocalRepositoryImpl(requireContext()).setEmailForgotPassword(binding.fforgotEtEmailAddress.text.toString())
            DataBaseImpl().forgot_pass(binding.fforgotEtEmailAddress.text.toString())
            controller.navigate(R.id.OTPVerificationFragment)
        }
        binding.fforgotBtnSignIn.setOnClickListener {
            controller.navigate(R.id.fragmentAuthorization)
        }

    }
}