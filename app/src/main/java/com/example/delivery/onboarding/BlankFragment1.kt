package com.example.delivery.onboarding

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentAddPayMethodBinding
import com.example.delivery.databinding.FragmentBlank1Binding

class BlankFragment1 : Fragment() {
    private var _binding : FragmentBlank1Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBlank1Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()

        val btnNext = binding.f1btnNext
        val btnSkip = binding.f1btnSkip

        btnSkip.setOnClickListener {
            controller.navigate(R.id.logInFragment)
        }
        btnNext.setOnClickListener {
            controller.navigate(R.id.blankFragment2)
        }
    }
}