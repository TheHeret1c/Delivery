package com.example.delivery.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.delivery.R

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()

        var btnLogOut = view.findViewById<ConstraintLayout>(R.id.fprofile_btnLogOut)
        btnLogOut.setOnClickListener {
            controller.navigate(R.id.fragmentAuthorization)
        }

        val btnCard = view.findViewById<ConstraintLayout>(R.id.fprofile_btnCard)
        btnCard.setOnClickListener {
            controller.navigate(R.id.addPayMethodFragment)
        }

        val btnEditProfile = view.findViewById<ConstraintLayout>(R.id.fprofile_btnEditProfile)
        btnEditProfile.setOnClickListener {
            //controller.navigate(R.id.notificationSetFragment)
        }

        val btnStatements = view.findViewById<ConstraintLayout>(R.id.fprofile_btnStatements)
        btnStatements.setOnClickListener {
            //controller.navigate(R.id.notificationSetFragment)
        }

        val btnRefferals = view.findViewById<ConstraintLayout>(R.id.fprofile_btnRefferals)
        btnRefferals.setOnClickListener {
            //controller.navigate(R.id.transactionFragment)
        }

        val btnAboutUs = view.findViewById<ConstraintLayout>(R.id.fprofile_btnAboutUs)
        btnAboutUs.setOnClickListener {
            controller.navigate(R.id.sendPackageFragment)
        }

        val btnNotification = view.findViewById<ConstraintLayout>(R.id.fprofile_btnNotificSet)
        btnNotification.setOnClickListener {
            controller.navigate(R.id.notificationSetFragment)
        }
    }
}