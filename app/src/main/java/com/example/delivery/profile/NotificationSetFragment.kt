package com.example.delivery.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.delivery.R

class NotificationSetFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_set, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        val btnBack = view.findViewById<ConstraintLayout>(R.id.fnotifset_btnBack)

        val bundle = Bundle()
        btnBack.setOnClickListener {
            bundle.putString("pageMain", "profile");
            controller.navigate(R.id.mainFragment, bundle)
        }
    }
}