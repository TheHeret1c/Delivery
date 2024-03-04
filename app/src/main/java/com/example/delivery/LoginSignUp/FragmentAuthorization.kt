package com.example.delivery.LoginSignUp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FAuthorizationBinding
import com.example.delivery.utils.impl.DataBaseImpl
import com.example.delivery.utils.impl.LocalRepositoryImpl

class FragmentAuthorization : Fragment() {

    private var _binging : FAuthorizationBinding? = null

    private val binding get() = _binging!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binging = FAuthorizationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binging = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TEST", "Password - " + LocalRepositoryImpl(requireContext()).getRememberPassword())

        if (LocalRepositoryImpl(requireContext()).getRememberPassword() != "") {
            binding.etAuthPassword.setText(LocalRepositoryImpl(requireContext()).getRememberPassword())
            binding.chkBoxAuthRemember.isChecked = true
        }

        val controller = findNavController()

        binding.tvAuthSignUp.setOnClickListener {
            controller.navigate(R.id.fragmentCreateAccount)
        }

        binding.btnAuthLogIn.setOnClickListener {
            authorization()
            controller.navigate(R.id.mainFragment)
        }

        binding.tvForgot.setOnClickListener {
            controller.navigate(R.id.forgotPasswordFragment)
        }
    }

    private fun authorization() {
//        val data = LocalRepositoryImpl(requireContext()).getUserData()
//        if (binding.etAuthEmail.text.equals(data.get(2))) {
//            DataBaseImpl().auth(binding.etAuthEmail.text.toString(), binding.etAuthPassword.text.toString())
//            //DataBaseImpl().add_profile(data.get(0), data.get(1), data.get(2))
//            if (binding.chkBoxAuthRemember.isEnabled) {
//                LocalRepositoryImpl(requireContext()).setRememberPassword(binding.etAuthPassword.toString())
//            } else {
//                LocalRepositoryImpl(requireContext()).setRememberPassword("")
//            }
//        }
//        Log.e("TEST", data.toString())
        DataBaseImpl().auth(binding.etAuthEmail.text.toString(), binding.etAuthPassword.text.toString())
        if (binding.chkBoxAuthRemember.isChecked) {
            LocalRepositoryImpl(requireContext()).setRememberPassword(binding.etAuthPassword.text.toString())
        } else {
            LocalRepositoryImpl(requireContext()).setRememberPassword("")
        }
    }

}