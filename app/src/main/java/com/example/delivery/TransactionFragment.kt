package com.example.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    lateinit var rotationJob: Job
    lateinit var img :ImageView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img = view.findViewById<ImageView>(R.id.ftrans_img)

        rotationJob = viewLifecycleOwner.lifecycleScope.launch {
            startRotation()
        }

        // Выполнение вычислений и расчетов во втором потоке
        viewLifecycleOwner.lifecycleScope.launch {
            // Ваши вычисления и расчеты
            delay(3000)
            success()
        }

        val controller = findNavController()
        val btnBackHome = view.findViewById<Button>(R.id.ftrans_btnBackHome)
        val btnTrackItem = view.findViewById<Button>(R.id.ftrans_btnTrackItem)

        val bundle = Bundle()
        btnBackHome.setOnClickListener {
            bundle.putString("pageMain", "home");
            controller.navigate(R.id.mainFragment, bundle)
        }
        btnTrackItem.setOnClickListener {
            bundle.putString("pageMain", "track");
            controller.navigate(R.id.mainFragment, bundle)
        }
    }

    // Функция для начала вращения изображения
    private suspend fun startRotation() {
        while (true) {
            withContext(Dispatchers.Main) {
                img.rotation += -4f // Увеличиваем угол поворота
                delay(10) // Пауза между кадрами
            }
        }
    }

    override fun onDestroyView() {
        rotationJob.cancel() // Останавливаем вращение при уничтожении представления фрагмента
        super.onDestroyView()
    }

    override fun onDestroy() {
        rotationJob.cancel() // Останавливаем вращение при уничтожении активности
        super.onDestroy()
    }

    fun success() {
        val img = view?.findViewById<ImageView>(R.id.ftrans_img)
        val tvSuccess = view?.findViewById<TextView>(R.id.ftrans_tvSuccessful)
        rotationJob.cancel()
        img?.rotation = 0f
        tvSuccess?.visibility = View.VISIBLE
        img?.setImageResource(R.drawable.img_success)


    }
}