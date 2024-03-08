package com.example.delivery.authorization

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentLogInBinding
import com.example.delivery.databinding.FragmentSignUpBinding
import com.example.delivery.utils.impl.DataBaseImpl
import com.example.delivery.utils.impl.LocalRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogInFragment : Fragment() {
    private var _binding : FragmentLogInBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()

        Log.e("TEST", "Password - " + LocalRepositoryImpl(requireContext()).getRememberPassword())

        LocalRepositoryImpl(requireContext()).setIsFirstExec(false)

        if (LocalRepositoryImpl(requireContext()).getRememberPassword() != "") {
            binding.floginEtPassword.setText(LocalRepositoryImpl(requireContext()).getRememberPassword())
            binding.floginCheckBoxRemember.isChecked = true
        }

        binding.floginBtnLogIn.setOnClickListener {
            logIn()
        }
        binding.floginBtnSignIn.setOnClickListener {
            controller.navigate(R.id.signUpFragment)
        }
        binding.floginBtnForgotPassword.setOnClickListener {
            controller.navigate(R.id.forgotPasswordFragment)
        }
        binding.floginEtEmailAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.floginEtEmailAddress.backgroundTintList =
                    if (binding.floginEtEmailAddress.text.isNullOrEmpty()) ContextCompat.getColorStateList(binding.floginEtEmailAddress.context, R.color.gray_color_dark)
                    else ContextCompat.getColorStateList(binding.floginEtEmailAddress.context, R.color.text_color_light)
                checkData()
            }
            override fun afterTextChanged(s: Editable?) {  }
        })
        binding.floginEtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.floginEtPassword.backgroundTintList =
                    if (binding.floginEtPassword.text.isNullOrEmpty()) ContextCompat.getColorStateList(binding.floginEtPassword.context, R.color.gray_color_dark)
                    else ContextCompat.getColorStateList(binding.floginEtPassword.context, R.color.text_color_light)
                checkData()
            }
            override fun afterTextChanged(s: Editable?) {  }
        })
        binding.floginBtnShowPass.setOnClickListener {
            // Получаем текущее состояние видимости текста в EditText
            val isPasswordVisible = binding.floginEtPassword.transformationMethod == PasswordTransformationMethod.getInstance()

            // Меняем состояние видимости текста в EditText
            binding.floginEtPassword.transformationMethod =
                if (isPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()

            // Перемещаем курсор в конец текста
            binding.floginEtPassword.setSelection(binding.floginEtPassword.text.length)

            // Меняем иконку кнопки в зависимости от состояния видимости пароля
            val drawableRes = if (isPasswordVisible) R.drawable.img_eye
            else R.drawable.img_eye_slash
            binding.floginBtnShowPass.setImageResource(drawableRes)
        }
        binding.floginBtnLogIn.isClickable = false
    }

    fun checkData () {
        binding.floginBtnLogIn.backgroundTintList = ContextCompat.getColorStateList(binding.floginBtnLogIn.context, R.color.gray_color_dark)
        binding.floginBtnLogIn.isClickable = false

        if (binding.floginEtEmailAddress.text.toString() == "") return;
        if (binding.floginEtPassword.text.toString() == "") return;

        if (!binding.floginEtEmailAddress.text.toString().contains("@")) return;

        binding.floginBtnLogIn.backgroundTintList = null
        binding.floginBtnLogIn.isClickable = true
    }
    fun logIn() {
        val data = LocalRepositoryImpl(requireContext()).getUserData()
        if (binding.floginEtEmailAddress.text.toString() == data.get(2)) {
            CoroutineScope(Dispatchers.IO).launch {
                DataBaseImpl().auth(binding.floginEtEmailAddress.text.toString(), binding.floginEtPassword.text.toString())
            }
            DataBaseImpl().add_profile(data.get(0), data.get(1), data.get(2))
            LocalRepositoryImpl(requireContext()).setUserData("", "", "")
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                DataBaseImpl().auth(binding.floginEtEmailAddress.text.toString(), binding.floginEtPassword.text.toString())
            }
        }

        if (binding.floginCheckBoxRemember.isChecked) {
            LocalRepositoryImpl(requireContext()).setRememberPassword(binding.floginEtPassword.text.toString())
        } else {
            LocalRepositoryImpl(requireContext()).setRememberPassword("")
        }


        findNavController().navigate(R.id.mainFragment)
    }

}