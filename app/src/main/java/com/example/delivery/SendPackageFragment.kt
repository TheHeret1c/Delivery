package com.example.delivery

import android.os.Bundle
import android.transition.Visibility
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController

class SendPackageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_package, container, false)
    }

    var cntDestDetails = 1;
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        val btnBack = view.findViewById<ConstraintLayout>(R.id.fsendpack_btnBack)
        val btnAddDestin = view.findViewById<LinearLayout>(R.id.fsendpack_btnAddDestin)
        val bundle = Bundle()
        btnBack.setOnClickListener {
            bundle.putString("pageMain", "profile");
            controller.navigate(R.id.mainFragment, bundle)
        }
        val btnNext = view.findViewById<Button>(R.id.fsendpack_btnNext)
        btnNext.setOnClickListener {
            nextClick(view)
        }
        val btnEdit = view.findViewById<Button>(R.id.fsendpack_btnEditPackage)
        btnEdit.setOnClickListener {
            editPackageClick(view)
        }
        val btnPayment = view.findViewById<Button>(R.id.fsendpack_btnMakePayment)
        btnPayment.setOnClickListener {
            makePaymentClick(view)
        }
        btnAddDestin.setOnClickListener {
            if (cntDestDetails == 4) {
                view.findViewById<LinearLayout>(R.id.fsendpack_btnAddDestin).visibility = View.GONE
            }
            if (cntDestDetails < 5) {
                cntDestDetails++

                when (cntDestDetails) {
                    2 -> view.findViewById<LinearLayout>(R.id.fsendpack_llDestDetail2).visibility =
                        View.VISIBLE
                    3 -> view.findViewById<LinearLayout>(R.id.fsendpack_llDestDetail3).visibility =
                        View.VISIBLE
                    4 -> view.findViewById<LinearLayout>(R.id.fsendpack_llDestDetail4).visibility =
                        View.VISIBLE
                    5 -> view.findViewById<LinearLayout>(R.id.fsendpack_llDestDetail5).visibility =
                        View.VISIBLE
                }
            }
        }

        view.findViewById<ScrollView>(R.id.fsendpack_svInputData).visibility = View.VISIBLE
        view.findViewById<ScrollView>(R.id.fsendpack_svOutputData).visibility = View.GONE
    }

    fun nextClick(view: View) {
        val svInput = view.findViewById<ScrollView>(R.id.fsendpack_svInputData)
        svInput.visibility = View.GONE
        val svOutput = view.findViewById<ScrollView>(R.id.fsendpack_svOutputData)
        svOutput.visibility = View.VISIBLE
    }
    fun editPackageClick(view: View) {
        view.findViewById<ScrollView>(R.id.fsendpack_svInputData).visibility = View.VISIBLE
        view.findViewById<ScrollView>(R.id.fsendpack_svOutputData).visibility = View.GONE
    }
    fun makePaymentClick(view: View) {
        val controller = findNavController()
        controller.navigate(R.id.transactionFragment)
    }

}