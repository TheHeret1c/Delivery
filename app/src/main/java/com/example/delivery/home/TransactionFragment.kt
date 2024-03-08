package com.example.delivery.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentTransactionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionFragment : Fragment() {
    private var _binding : FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    lateinit var rotationJob: Job
    lateinit var img :ImageView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img = binding.ftransImg

        rotationJob = viewLifecycleOwner.lifecycleScope.launch {
            startRotation()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            delay(3000)
            success()
        }

        val controller = findNavController()
        val bundle = Bundle()
        binding.ftransBtnBackHome.setOnClickListener {
            bundle.putString("pageMain", "home");
            controller.navigate(R.id.mainFragment, bundle)
        }
        binding.ftransBtnTrackItem.setOnClickListener {
            bundle.putString("pageMain", "track");
            controller.navigate(R.id.mainFragment, bundle)
        }
    }
    private suspend fun startRotation() {
        while (true) {
            withContext(Dispatchers.Main) {
                img.rotation += -4f
                delay(10)
            }
        }
    }

    override fun onDestroyView() {
        rotationJob.cancel()
        super.onDestroyView()
    }

    override fun onDestroy() {
        rotationJob.cancel()
        super.onDestroy()
    }

    fun success() {
        binding.ftransTvSuccessful.visibility = View.VISIBLE
        rotationJob.cancel()
        img.rotation = 0f
        img.setImageResource(R.drawable.img_success)
    }
}