package com.example.delivery.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FOnboarding1Binding


class Fragment1 : Fragment() {

    private var _binding : FOnboarding1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FOnboarding1Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()

        binding.btnOnb1Skip.setOnClickListener {
            controller.navigate(R.id.fragmentAuthorization)
        }

        binding.btnOnb1Next.setOnClickListener {
            controller.navigate(R.id.fragment2)
        }
    }
}