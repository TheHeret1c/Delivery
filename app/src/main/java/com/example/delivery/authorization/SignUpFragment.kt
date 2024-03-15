package com.example.delivery.authorization

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentHomeBinding
import com.example.delivery.databinding.FragmentSignUpBinding
import com.example.delivery.utils.impl.DataBaseImpl
import com.example.delivery.utils.impl.LocalRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {
    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()

        binding.fsignupBtnSignUp.setOnClickListener {
            signUp()
        }
        binding.fsignupBtnSignIn.setOnClickListener {
            controller.navigate(R.id.logInFragment)
        }
        binding.fsignupEtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.fsignupEtName.backgroundTintList =
                    if (binding.fsignupEtName.text.isNullOrEmpty()) ContextCompat.getColorStateList(binding.fsignupEtName.context, R.color.gray_color_dark)
                    else ContextCompat.getColorStateList(binding.fsignupEtName.context, R.color.text_color_light) 
                checkData()
            }
            override fun afterTextChanged(s: Editable?) {  }
        })
        binding.fsignupEtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.fsignupEtEmail.backgroundTintList =
                    if (binding.fsignupEtEmail.text.isNullOrEmpty()) ContextCompat.getColorStateList(binding.fsignupEtEmail.context, R.color.gray_color_dark)
                    else ContextCompat.getColorStateList(binding.fsignupEtEmail.context, R.color.text_color_light)
                checkData()
            }
            override fun afterTextChanged(s: Editable?) {  }
        })
        binding.fsignupEtPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.fsignupEtPhone.backgroundTintList =
                    if (binding.fsignupEtPhone.text.isNullOrEmpty()) ContextCompat.getColorStateList(binding.fsignupEtPhone.context, R.color.gray_color_dark)
                    else ContextCompat.getColorStateList(binding.fsignupEtPhone.context, R.color.text_color_light)
                checkData()
            }
            override fun afterTextChanged(s: Editable?) {  }
        })
        binding.fsignupEtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.fsignupEtPassword.backgroundTintList =
                    if (binding.fsignupEtPassword.text.isNullOrEmpty()) ContextCompat.getColorStateList(binding.fsignupEtPassword.context, R.color.gray_color_dark)
                    else ContextCompat.getColorStateList(binding.fsignupEtPassword.context, R.color.text_color_light)
                checkData()
            }
            override fun afterTextChanged(s: Editable?) {  }
        })
        binding.fsignupEtPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.fsignupEtPasswordConfirm.backgroundTintList =
                    if (binding.fsignupEtPasswordConfirm.text.isNullOrEmpty()) ContextCompat.getColorStateList(binding.fsignupEtPasswordConfirm.context, R.color.gray_color_dark)
                    else ContextCompat.getColorStateList(binding.fsignupEtPasswordConfirm.context, R.color.text_color_light)
                checkData()
            }
            override fun afterTextChanged(s: Editable?) {  }
        })
        binding.fsignupChbAgree.setOnCheckedChangeListener { buttonView, isChecked ->
            checkData()
        }
        binding.fsignupBtnShowPass.setOnClickListener {
            // Получаем текущее состояние видимости текста в EditText
            val isPasswordVisible = binding.fsignupEtPassword.transformationMethod == PasswordTransformationMethod.getInstance()

            // Меняем состояние видимости текста в EditText
            binding.fsignupEtPassword.transformationMethod =
                if (isPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()

            // Перемещаем курсор в конец текста
            binding.fsignupEtPassword.setSelection(binding.fsignupEtPassword.text.length)

            // Меняем иконку кнопки в зависимости от состояния видимости пароля
            val drawableRes = if (isPasswordVisible) R.drawable.img_eye
            else R.drawable.img_eye_slash
            binding.fsignupBtnShowPass.setImageResource(drawableRes)
        }
        binding.fsignupBtnShowPassConfirm.setOnClickListener {
            // Получаем текущее состояние видимости текста в EditText
            val isPasswordVisible = binding.fsignupEtPasswordConfirm.transformationMethod == PasswordTransformationMethod.getInstance()

            // Меняем состояние видимости текста в EditText
            binding.fsignupEtPasswordConfirm.transformationMethod =
                if (isPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()

            // Перемещаем курсор в конец текста
            binding.fsignupEtPasswordConfirm.setSelection(binding.fsignupEtPasswordConfirm.text.length)

            // Меняем иконку кнопки в зависимости от состояния видимости пароля
            val drawableRes = if (isPasswordVisible) R.drawable.img_eye
            else R.drawable.img_eye_slash
            binding.fsignupBtnShowPassConfirm.setImageResource(drawableRes)
        }
        binding.fsignupBtnSignUp.isClickable = false
    }

    fun checkData () {
        binding.fsignupBtnSignUp.backgroundTintList = ContextCompat.getColorStateList(binding.fsignupBtnSignIn.context, R.color.gray_color_dark)
        binding.fsignupBtnSignUp.isClickable = false

        if (binding.fsignupEtName.text.toString() == "") return
        if (binding.fsignupEtPhone.text.toString() == "") return
        if (binding.fsignupEtEmail.text.toString() == "") return
        if (binding.fsignupEtPassword.text.toString() == "") return
        if (binding.fsignupEtPasswordConfirm.text.toString() == "") return

        if (!binding.fsignupEtEmail.text.toString().contains("@")) return
        if (!binding.fsignupEtPasswordConfirm.text.toString().equals(binding.fsignupEtPassword.text.toString(), ignoreCase = true)) return
        if (!binding.fsignupChbAgree.isChecked) return

        binding.fsignupBtnSignUp.backgroundTintList = null
        binding.fsignupBtnSignUp.isClickable = true
    }
    fun signUp() {
        LocalRepositoryImpl(requireContext()).setUserData(binding.fsignupEtName.text.toString(), binding.fsignupEtPhone.text.toString(), binding.fsignupEtEmail.text.toString())
        CoroutineScope(Dispatchers.IO).launch {
            DataBaseImpl().register(binding.fsignupEtEmail.text.toString(), binding.fsignupEtPassword.text.toString())
        }
        findNavController().navigate(R.id.logInFragment)
    }
}