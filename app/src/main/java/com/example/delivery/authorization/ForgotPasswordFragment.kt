package com.example.delivery.authorization

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentForgotPasswordBinding
import com.example.delivery.databinding.FragmentLogInBinding
import com.example.delivery.utils.impl.DataBaseImpl
import com.example.delivery.utils.impl.LocalRepositoryImpl

class ForgotPasswordFragment : Fragment() {
    private var _binding : FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()
        binding.fforgotBtnSendOTP.setOnClickListener {
            sendOTP()
        }
        binding.fforgotBtnSignIn.setOnClickListener {
            controller.navigate(R.id.logInFragment)
        }
        binding.fforgotEtEmailAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.fforgotEtEmailAddress.backgroundTintList =
                    if (binding.fforgotEtEmailAddress.text.isNullOrEmpty()) ContextCompat.getColorStateList(binding.fforgotEtEmailAddress.context, R.color.gray_color_dark)
                    else ContextCompat.getColorStateList(binding.fforgotEtEmailAddress.context, R.color.text_color_light)
                checkData()
            }
            override fun afterTextChanged(s: Editable?) {  }
        })
        binding.fforgotBtnSendOTP.isClickable = false
    }

    fun checkData () {
        binding.fforgotBtnSendOTP.backgroundTintList = ContextCompat.getColorStateList(binding.fforgotBtnSendOTP.context, R.color.gray_color_dark)
        binding.fforgotBtnSendOTP.isClickable = false
        if (binding.fforgotEtEmailAddress.text.toString() == "") return;
        
        if (!binding.fforgotEtEmailAddress.text.toString().contains("@")) return;

        binding.fforgotBtnSendOTP.backgroundTintList = null
        binding.fforgotBtnSendOTP.isClickable = true
    }
    fun sendOTP() {
        LocalRepositoryImpl(requireContext()).setEmailForgotPassword(binding.fforgotEtEmailAddress.text.toString())
        //DataBaseImpl().forgot_pass(binding.fforgotEtEmailAddress.text.toString())
        findNavController().navigate(R.id.OTPVerificationFragment)
    }
}