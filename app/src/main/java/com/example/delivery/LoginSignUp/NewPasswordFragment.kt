package com.example.delivery.LogInSugnUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentForgotPasswordBinding
import com.example.delivery.databinding.FragmentNewPasswordBinding
import com.example.delivery.utils.impl.DataBaseImpl

class NewPasswordFragment : Fragment() {

    private var _binding : FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()

        val btnSetPass = view.findViewById<Button>(R.id.fnewpass_btnLogIn)

        binding.fnewpassBtnLogIn.setOnClickListener {
            if (binding.fnewpassEtPassword.text.toString() == binding.fnewpassEtPasswordConfirm.text.toString()) {
                DataBaseImpl().change_pass(binding.fnewpassEtPassword.text.toString())
                controller.navigate(R.id.mainFragment)
            }
        }
    }

}