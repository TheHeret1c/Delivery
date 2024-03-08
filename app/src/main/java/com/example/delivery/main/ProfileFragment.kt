package com.example.delivery.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentProfileBinding
import com.example.delivery.databinding.FragmentWalletBinding

class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()

        binding.fprofileBtnLogOut.setOnClickListener {
            controller.navigate(R.id.logInFragment)
        }
        binding.fprofileBtnCard.setOnClickListener {
            controller.navigate(R.id.addPayMethodFragment)
        }

        binding.fprofileBtnEditProfile.setOnClickListener {
            //controller.navigate(R.id.notificationSetFragment)
        }

        binding.fprofileBtnStatements.setOnClickListener {
            //controller.navigate(R.id.notificationSetFragment)
        }

        binding.fprofileBtnRefferals.setOnClickListener {
            //controller.navigate(R.id.transactionFragment)
        }

        binding.fprofileBtnAboutUs.setOnClickListener {
            //controller.navigate(R.id.sendPackageFragment)
        }

        binding.fprofileBtnNotificSet.setOnClickListener {
            controller.navigate(R.id.notificationSetFragment)
        }
    }
}