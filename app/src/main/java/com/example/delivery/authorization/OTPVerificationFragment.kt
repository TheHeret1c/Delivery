package com.example.delivery.authorization

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentForgotPasswordBinding
import com.example.delivery.databinding.FragmentOTPVerificationBinding
import com.example.delivery.utils.impl.DataBaseImpl
import com.example.delivery.utils.impl.LocalRepositoryImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class OTPVerificationFragment : Fragment() {
    private var _binding : FragmentOTPVerificationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOTPVerificationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    lateinit var editTexts: ArrayList<EditText>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()

        binding.fotpverBtnSetPass.setOnClickListener {
            setPassword()
        }
        binding.otpverTvResend.setOnClickListener {
            sendOTP()
        }

        editTexts = ArrayList()
        editTexts.add(binding.otpverEt1)
        editTexts.add(binding.otpverEt2)
        editTexts.add(binding.otpverEt3)
        editTexts.add(binding.otpverEt4)
        editTexts.add(binding.otpverEt5)
        editTexts.add(binding.otpverEt6)


        for (i in 0 until editTexts.size) {
            val currentIndex = i
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (currentIndex < editTexts.size - 1) {
                            editTexts[currentIndex + 1].requestFocus() // Переход к следующему полю ввода
                        }
                    } else if (s?.length == 0 && currentIndex > 0) {
                        editTexts[currentIndex - 1].requestFocus() // Переход к предыдущему полю ввода при удалении символа
                    }
                    checkData()
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Не используется
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Не используется
                }
            })
        }
        binding.fotpverBtnSetPass.isClickable = false
        sendOTP()
    }

    fun checkData() {
        binding.fotpverBtnSetPass.isClickable = false
        binding.fotpverBtnSetPass.backgroundTintList = ContextCompat.getColorStateList(binding.fotpverBtnSetPass.context, R.color.gray_color_dark)

        for (i in 0 until editTexts.size) {
            editTexts[i].backgroundTintList = null
            if (!editTexts[i].text.isNullOrEmpty()) {
                editTexts[i].setBackgroundResource(R.drawable.et_style_otp_with)
                if (i == 5) checkOTP()
            } else {
                editTexts[i].setBackgroundResource(R.drawable.et_otp_without)
            }
        }

    }
    fun checkOTP() {
        if (true) {
            binding.fotpverBtnSetPass.isClickable = true
            binding.fotpverBtnSetPass.backgroundTintList = null
        }
        else {
            for (i in 0 until editTexts.size) {
               editTexts[i].backgroundTintList = ContextCompat.getColorStateList(binding.fotpverBtnSetPass.context, R.color.error_color)
            }
        }
    }
    fun setPassword() {
        var code = ""
        for (i in 0 until editTexts.size) {
            code += editTexts[i].text.toString()
        }
        DataBaseImpl().verify_code(LocalRepositoryImpl(requireContext()).getEmailForgotPassword(), code)
        findNavController().navigate(R.id.newPasswordFragment)
    }

    fun sendOTP(){
        binding.otpverTvResend.isClickable = false
        binding.otpverTvResend.setTextColor(ContextCompat.getColorStateList(binding.otpverTvResend.context, R.color.gray_color_dark))

        //send otp
        DataBaseImpl().forgot_pass(LocalRepositoryImpl(requireContext()).getEmailForgotPassword())
        val job = viewLifecycleOwner.lifecycleScope.launch {
            counting()
        }
    }

    private suspend fun counting() {
        var secs = 60
        while (secs > 0) {
            val minutes = secs / 60
            val seconds = secs % 60
            binding.otpverTvResend.setText("$minutes:${String.format("%02d", seconds)}")
            delay(1000)
            secs--
        }

        binding.otpverTvResend.isClickable = true
        binding.otpverTvResend.setText("resend")
        binding.otpverTvResend.setTextColor(ContextCompat.getColorStateList(binding.otpverTvResend.context, R.color.main_color))
        cancel()
    }
}