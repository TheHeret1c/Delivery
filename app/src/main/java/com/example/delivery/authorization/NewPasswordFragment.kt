package com.example.delivery.authorization

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentNewPasswordBinding
import com.example.delivery.databinding.FragmentOTPVerificationBinding
import com.example.delivery.utils.impl.DataBaseImpl

class NewPasswordFragment : Fragment() {
    private var _binding : FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fnewpassBtnLogIn.setOnClickListener {
            logIn()
        }

        binding.fnewpassEtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.fnewpassEtPassword.backgroundTintList =
                    if (binding.fnewpassEtPassword.text.isNullOrEmpty()) ContextCompat.getColorStateList(binding.fnewpassEtPassword.context, R.color.gray_color_dark)
                    else ContextCompat.getColorStateList(binding.fnewpassEtPassword.context, R.color.text_color_light)
                checkData()
            }
            override fun afterTextChanged(s: Editable?) {  }
        })
        binding.fnewpassEtPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.fnewpassEtPasswordConfirm.backgroundTintList =
                    if (binding.fnewpassEtPasswordConfirm.text.isNullOrEmpty()) ContextCompat.getColorStateList(binding.fnewpassEtPasswordConfirm.context, R.color.gray_color_dark)
                    else ContextCompat.getColorStateList(binding.fnewpassEtPasswordConfirm.context, R.color.text_color_light)
                checkData()
            }
            override fun afterTextChanged(s: Editable?) {  }
        })
        binding.fnewpassBtnShowPass.setOnClickListener {
            // Получаем текущее состояние видимости текста в EditText
            val isPasswordVisible = binding.fnewpassEtPassword.transformationMethod == PasswordTransformationMethod.getInstance()

            // Меняем состояние видимости текста в EditText
            binding.fnewpassEtPassword.transformationMethod =
                if (isPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()

            // Перемещаем курсор в конец текста
            binding.fnewpassEtPassword.setSelection(binding.fnewpassEtPassword.text.length)

            // Меняем иконку кнопки в зависимости от состояния видимости пароля
            val drawableRes = if (isPasswordVisible) R.drawable.img_eye
            else R.drawable.img_eye_slash
            binding.fnewpassBtnShowPass.setImageResource(drawableRes)
        }
        binding.fnewpassBtnShowPassConfirm.setOnClickListener {
            // Получаем текущее состояние видимости текста в EditText
            val isPasswordVisible = binding.fnewpassEtPasswordConfirm.transformationMethod == PasswordTransformationMethod.getInstance()

            // Меняем состояние видимости текста в EditText
            binding.fnewpassEtPasswordConfirm.transformationMethod =
                if (isPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()

            // Перемещаем курсор в конец текста
            binding.fnewpassEtPasswordConfirm.setSelection(binding.fnewpassEtPasswordConfirm.text.length)

            // Меняем иконку кнопки в зависимости от состояния видимости пароля
            val drawableRes = if (isPasswordVisible) R.drawable.img_eye
            else R.drawable.img_eye_slash
            binding.fnewpassBtnShowPassConfirm.setImageResource(drawableRes)
        }
        binding.fnewpassBtnLogIn.isClickable = false
    }

    fun checkData () {
        binding.fnewpassBtnLogIn.backgroundTintList = ContextCompat.getColorStateList(binding.fnewpassBtnLogIn.context, R.color.gray_color_dark)
        binding.fnewpassBtnLogIn.isClickable = false

        if (binding.fnewpassEtPassword.text.toString() == "") return;
        if (binding.fnewpassEtPasswordConfirm.text.toString() == "") return;

        if (!binding.fnewpassEtPasswordConfirm.text.toString().equals(binding.fnewpassEtPassword.text.toString(), ignoreCase = true)) return;

        binding.fnewpassBtnLogIn.backgroundTintList = null
        binding.fnewpassBtnLogIn.isClickable = true
    }
    fun logIn() {
        DataBaseImpl().change_pass(binding.fnewpassEtPassword.text.toString())
        findNavController().navigate(R.id.mainFragment)
    }

}