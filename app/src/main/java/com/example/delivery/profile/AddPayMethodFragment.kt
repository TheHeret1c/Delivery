package com.example.delivery.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentAddPayMethodBinding

class AddPayMethodFragment : Fragment() {
    private var _binding : FragmentAddPayMethodBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddPayMethodBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        val bundle = Bundle()
        binding.faddpaymetBtnBack.setOnClickListener {
            bundle.putString("pageMain", "profile");
            controller.navigate(R.id.mainFragment, bundle)
        }
        binding.faddpayBtnWithCard.setOnClickListener {
            setWithCard()
        }
        binding.faddpayRbWithCard.setOnClickListener {
            setWithCard()
        }
        binding.faddpayBtnWithWallet.setOnClickListener {
            setWithWallet()
        }
        binding.faddpayRbWithWallet.setOnClickListener {
            setWithWallet()
        }
        setWithWallet()
    }

    fun setWithWallet() {
        binding.faddpayRbWithCard.isChecked = false
        binding.faddpayRbWithWallet.isChecked = true
        val constraintLayout = binding.faddpayBtnWithCard
        val layoutParams = constraintLayout.layoutParams as ViewGroup.LayoutParams
        layoutParams.height = dpToPx(96)
        constraintLayout.layoutParams = layoutParams
    }

    var cntCards = 2
    fun setWithCard() {
        binding.faddpayRbWithCard.isChecked = true
        binding.faddpayRbWithWallet.isChecked = false
        val constraintLayout = binding.faddpayBtnWithCard
        val layoutParams = constraintLayout.layoutParams as ViewGroup.LayoutParams
        layoutParams.height = dpToPx(64 + cntCards * 72 + 32)
        constraintLayout.layoutParams = layoutParams

    }
    fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}