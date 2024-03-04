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

class AddPayMethodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_pay_method, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        val btnBack = view.findViewById<ConstraintLayout>(R.id.faddpaymet_btnBack)

        val bundle = Bundle()
        btnBack.setOnClickListener {
            bundle.putString("pageMain", "profile");
            controller.navigate(R.id.mainFragment, bundle)
        }

        view.findViewById<ConstraintLayout>(R.id.faddpay_btnWithCard).setOnClickListener {
            setWithCard(view)
        }
        view.findViewById<RadioButton>(R.id.faddpay_rbWithCard).setOnClickListener {
            setWithCard(view)
        }
        view.findViewById<ConstraintLayout>(R.id.faddpay_btnWithWallet).setOnClickListener {
            setWithWallet(view)
        }
        view.findViewById<RadioButton>(R.id.faddpay_rbWithWallet).setOnClickListener {
            setWithWallet(view)
        }

        setWithWallet(view)
    }

    fun setWithWallet(view: View) {
        view.findViewById<RadioButton>(R.id.faddpay_rbWithCard).isChecked = false
        view.findViewById<RadioButton>(R.id.faddpay_rbWithWallet).isChecked = true
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.faddpay_btnWithCard)
        val layoutParams = constraintLayout.layoutParams as ViewGroup.LayoutParams
        layoutParams.height = dpToPx(96)
        constraintLayout.layoutParams = layoutParams
    }

    var cntCards = 2
    fun setWithCard(view: View) {
        //Toast.makeText(context, "!!!", Toast.LENGTH_SHORT).show()
        view.findViewById<RadioButton>(R.id.faddpay_rbWithCard).isChecked = true
        view.findViewById<RadioButton>(R.id.faddpay_rbWithWallet).isChecked = false
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.faddpay_btnWithCard)
        val layoutParams = constraintLayout.layoutParams as ViewGroup.LayoutParams
        layoutParams.height = dpToPx(64 + cntCards * 72 + 32)
        constraintLayout.layoutParams = layoutParams

    }

    fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}