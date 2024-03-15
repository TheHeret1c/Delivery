package com.example.delivery.main

import android.app.ActionBar.LayoutParams
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.delivery.R
import com.example.delivery.databinding.FragmentWalletBinding

class WalletFragment : Fragment() {
    private var _binding : FragmentWalletBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fwalletBtnBank.setOnClickListener {
            addTrans()
        }
    }

    private fun addTrans() {
        // Создаем ConstraintLayout
        val constraintLayout = ConstraintLayout(requireContext())
        constraintLayout.id = View.generateViewId()
        constraintLayout.clipToPadding = false
        constraintLayout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            resources.getDimensionPixelSize(R.dimen.transaction_height)
        )
        constraintLayout.background = ContextCompat.getDrawable(requireContext(), R.color.white)
        Log.e("TEST", "Background = " + constraintLayout.background)
        Log.e("TEST", "Background others = " + binding.fwalletTrans7.background)
        Log.e("TEST", "Background others = " + binding.fwalletTrans6.background)
        constraintLayout.elevation = resources.getDimension(R.dimen.elevation)

        // Создаем первый TextView
        val textView1 = TextView(context)
        textView1.id = View.generateViewId()
        textView1.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView1.text = "N30,000.00"
        textView1.setTextColor(ContextCompat.getColor(requireContext(), R.color.success_color))
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        textView1.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)

        // Создаем второй TextView
        val textView2 = TextView(context)
        textView2.id = View.generateViewId()
        textView2.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView2.text = "August 29, 2022"
        textView2.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_color_dark))
        textView2.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto)

        // Создаем третий TextView
        val textView3 = TextView(context)
        textView3.id = View.generateViewId()
        textView3.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView3.text = "message"
        textView3.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_color_light))
        textView3.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)

        // Добавляем все элементы в ConstraintLayout
        constraintLayout.addView(textView1)
        constraintLayout.addView(textView2)
        constraintLayout.addView(textView3)

        // Определяем привязки для каждого TextView
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        // Определяем привязки для первого TextView
        constraintSet.connect(textView1.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16.dpToPx()) // начало отступа слева
        constraintSet.connect(textView1.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 8.dpToPx()) // начало отступа сверху

        // Определяем привязки для второго TextView
        constraintSet.connect(textView2.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16.dpToPx()) // конец отступа справа
        constraintSet.centerVertically(textView2.id, ConstraintSet.PARENT_ID)

        // Определяем привязки для третьего TextView
        constraintSet.connect(textView3.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16.dpToPx()) // начало отступа слева
        constraintSet.connect(textView3.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 8.dpToPx()) // конец отступа снизу

        // Применяем привязки
        constraintSet.applyTo(constraintLayout)

        // Добавляем ConstraintLayout в LinearLayout
        binding.fwalletLayoutTrans.addView(constraintLayout)

    }

    fun Int.dpToPx(): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (this * density).toInt()
    }

}

