package com.example.delivery.LoginSignUp

import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FCreateAccountBinding
import com.example.delivery.utils.impl.DataBaseImpl
import com.example.delivery.utils.impl.LocalRepositoryImpl


class FragmentCreateAccount : Fragment() {

    private var _binding : FCreateAccountBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FCreateAccountBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LocalRepositoryImpl(requireContext()).setIsFirstExec(false)

        binding.btnCreateSignUp.setOnClickListener {
            createAcc()
        }
        binding.tvCreateSignIn.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.fragmentAuthorization)
        }
    }

    private fun createAcc() {
        LocalRepositoryImpl(requireContext()).setUserData(binding.etCreateFullName.toString(), binding.etCreatePhoneNumber.toString(), binding.etCreateEmail.toString())
        DataBaseImpl().register(binding.etCreateEmail.text.toString(), binding.etCreatePassword.text.toString())
        val controller = findNavController()
        controller.navigate(R.id.fragmentAuthorization)
    }
}