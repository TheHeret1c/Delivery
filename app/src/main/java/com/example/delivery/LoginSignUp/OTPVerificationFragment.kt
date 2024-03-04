package com.example.delivery.LogInSugnUp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentForgotPasswordBinding
import com.example.delivery.databinding.FragmentOTPVerificationBinding
import com.example.delivery.utils.impl.DataBaseImpl
import com.example.delivery.utils.impl.LocalRepositoryImpl

class OTPVerificationFragment : Fragment() {

    private var _binding : FragmentOTPVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOTPVerificationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    lateinit var editTexts: ArrayList<EditText>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()


//        var editText1 = view.findViewById<EditText>(R.id.otpver_et1)
//        var editText2 = view.findViewById<EditText>(R.id.otpver_et2)
//        var editText3 = view.findViewById<EditText>(R.id.otpver_et3)
//        var editText4 = view.findViewById<EditText>(R.id.otpver_et4)
//        var editText5 = view.findViewById<EditText>(R.id.otpver_et5)
//        var editText6 = view.findViewById<EditText>(R.id.otpver_et6)

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
                    if (!s.isNullOrEmpty()) {
                        editTexts[i].setBackgroundResource(R.drawable.et_style_otp_with)
                    } else {
                        editTexts[i].setBackgroundResource(R.drawable.edit_text_otp_without)
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Не используется
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Не используется
                }
            })
        }

        binding.fotpverBtnSetPass.setOnClickListener {
            var code = ""
            for (i in 0 until editTexts.size) {
                code += editTexts[i].text.toString()
            }

            DataBaseImpl().verify_code(LocalRepositoryImpl(requireContext()).getEmailForgotPassword(), code)
            controller.navigate(R.id.newPasswordFragment)
        }
    }
}