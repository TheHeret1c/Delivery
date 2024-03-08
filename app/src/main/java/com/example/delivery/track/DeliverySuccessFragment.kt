package com.example.delivery.track

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentDeliverySuccessBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeliverySuccessFragment : Fragment() {
    private var _binding : FragmentDeliverySuccessBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDeliverySuccessBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    lateinit var rotationJob: Job
    lateinit var img :ImageView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        img = binding.fdeliverysucImg
        rotationJob = viewLifecycleOwner.lifecycleScope.launch {
            startRotation()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            delay(3000)
            success()
        }
        val bundle = Bundle()
        binding.fdeliverysucBtnDone.setOnClickListener {
            bundle.putString("pageMain", "track");
            findNavController().navigate(R.id.mainFragment, bundle)
        }
        binding.fdeliverysucStar1.setOnClickListener {
            setStar(1)
        }
        binding.fdeliverysucStar2.setOnClickListener {
            setStar(2)
        }
        binding.fdeliverysucStar3.setOnClickListener {
            setStar(3)
        }
        binding.fdeliverysucStar4.setOnClickListener {
            setStar(4)
        }
        binding.fdeliverysucStar5.setOnClickListener {
            setStar(5)
        }
    }

    var stars = 0
    fun setStar(star: Int) {
        stars = star
        val linearLayoutStars = binding.fdeliverysucStars
        for (i in 0 until linearLayoutStars.childCount) {
            val view = linearLayoutStars.getChildAt(i)
            if (view is ImageView) {
                view.setColorFilter(ContextCompat.getColor(linearLayoutStars.context,
                    R.color.gray_color_light
                ), PorterDuff.Mode.SRC_ATOP)
            }
        }
        for (i in 0 until stars) {
            val view = linearLayoutStars.getChildAt(i)
            if (view is ImageView) {
                view.setColorFilter(ContextCompat.getColor(linearLayoutStars.context,
                    R.color.main_color
                ), PorterDuff.Mode.SRC_ATOP)
            }
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
        binding.fdeliverysucLlSuccess.visibility = View.VISIBLE
        rotationJob.cancel()
        img.rotation = 0f
        img.setImageResource(R.drawable.img_success)
    }
}
