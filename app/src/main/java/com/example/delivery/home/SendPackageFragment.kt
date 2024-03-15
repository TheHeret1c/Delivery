package com.example.delivery.home

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentSendPackageBinding
import com.example.delivery.utils.impl.DataBaseImpl
import com.example.delivery.utils.impl.LocalRepositoryImpl
import com.example.delivery.utils.impl.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID

class SendPackageFragment : Fragment() {
    private var _binding : FragmentSendPackageBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSendPackageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    var instantDelivery = true
    var cntDestDetails = 1;
    var uuid_code: String = ""
    var dest = mutableListOf<destinationDetails>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        binding.fsendpackBtnBack.setOnClickListener {
            controller.navigate(R.id.mainFragment)
        }
        binding.fsendpackBtnNext.setOnClickListener {
            nextClick(view)
        }
        binding.fsendpackBtnEditPackage.setOnClickListener {
            editPackageClick(view)
        }
        binding.fsendpackBtnMakePayment.setOnClickListener {
            makePaymentClick(view)
        }
        binding.fsendpackBtnAddDestin.setOnClickListener {
            if (cntDestDetails == 4) {
                binding.fsendpackBtnAddDestin.visibility = View.GONE
            }
            if (cntDestDetails < 5) {
                cntDestDetails++

                when (cntDestDetails) {
                    2 -> {
                        binding.fsendpackLlDestDetail2.visibility =
                            View.VISIBLE
                        binding.fsendpackLayoutDestin2.visibility =
                            View.VISIBLE
                    }
                    3 -> {
                        binding.fsendpackLlDestDetail3.visibility =
                            View.VISIBLE
                        binding.fsendpackLayoutDestin3.visibility =
                            View.VISIBLE
                    }
                    4 -> {
                        binding.fsendpackLlDestDetail4.visibility =
                            View.VISIBLE
                        binding.fsendpackLayoutDestin4.visibility =
                            View.VISIBLE
                    }
                    5 -> {
                        binding.fsendpackLlDestDetail5.visibility =
                            View.VISIBLE
                        binding.fsendpackLayoutDestin5.visibility =
                            View.VISIBLE
                    }
                }
            }
        }

        binding.fsendpackBtnInstant.setOnClickListener {
            instantDelivery = true
        }

        binding.fsendpackBtnScheduled.setOnClickListener {
            instantDelivery = false
        }

        editPackageClick(view)

    }

    fun nextClick(view: View) {

        // Точка отправки посылки
        val origin = originDetails(binding.fsendpackEtAddressOrigin.text.toString(), binding.fsendpackEtCountryOrgin.text.toString(),
            binding.fsendpackEtPhoneOrigin.text.toString(), binding.fsendpackEtOthersOrigin.text.toString())

        // Переборка точек доставки
        for (i in 0..cntDestDetails) {

            val childLinear: LinearLayout = binding.fsendpackLlDestDetails.getChildAt(i) as LinearLayout

            val address: EditText = childLinear.getChildAt(1) as EditText
            val country: EditText = childLinear.getChildAt(2) as EditText
            val phone: EditText = childLinear.getChildAt(3) as EditText
            val others: EditText = childLinear.getChildAt(4) as EditText

            // Добавление точки доставки в глобальную переменную с точками доставки
            val destinationDetail = destinationDetails(address.text.toString(), country.text.toString(), phone.text.toString(), others.text.toString())
            dest.add(destinationDetail)
        }

        // Дата класс посылки для переноса на след. окно
        var packageDetail = packageDetails(binding.fsendpackEtPackageItems.text.toString(), binding.fsendpackEtWeightItem.text.toString().toFloat(),
            binding.fsendpackEtWorthItems.text.toString().toFloat())

        binding.fsendpackSvInputData.visibility = View.GONE
        binding.fsendpackSvOutputData.visibility = View.VISIBLE


        // Запись в новом окне адреса отправки
        binding.fsendpackTvOriginAddress.text = origin.address + " " + origin.state_country
        binding.fsendpackTvOriginPhone.text = origin.phone_number

        // Перебор точек доставки
        for (i in 1..cntDestDetails) {


            val childLinear: LinearLayout = binding.fsendpackLayoutDestinations.getChildAt(i) as LinearLayout

            val address: TextView = childLinear.getChildAt(0) as TextView
            val phone: TextView = childLinear.getChildAt(1) as TextView

            address.text = i.toString() + ". " + dest[i - 1].address + " " + dest[i - 1].state_country
            phone.text = dest[i - 1].phone_number
        }

        // Трек-код
        val patternUUID = "RXXXX-XXXX-XXXX-XXXX"
        uuid_code = generateFormattedUUID(patternUUID)

        // Запись данных о доставке в окно
        binding.fsendpackTvPackage.text = packageDetail.package_items
        binding.fsendpackTvWeight.text = packageDetail.weight_item.toString()
        binding.fsendpackTvWorth.text = packageDetail.worth_item.toString()
        binding.fsendpackTvTrack.text = uuid_code

        val delivery_charges : Float = cntDestDetails * 2500.0f
        val instant_delivery_charges = 2500.0f
        val tax_service_charges : Float = (delivery_charges + instant_delivery_charges) * 0.05f
        val amount = delivery_charges + instant_delivery_charges + tax_service_charges

        binding.fsendpackTvDeliveryCharges.text = delivery_charges.toString()
        binding.fsendpackTvInstantDelivery.text = instant_delivery_charges.toString()
        binding.fsendpackTvTax.text = tax_service_charges.toString()
        binding.fsendpackTvAmount.text = amount.toString()
    }
    fun editPackageClick(view: View) {
        binding.fsendpackSvInputData.visibility = View.VISIBLE
        binding.fsendpackSvOutputData.visibility = View.GONE
    }
    fun makePaymentClick(view: View) {

//        var profile_id: Int?
//
//        val result = runBlocking {
//            profile_id = DataBaseImpl().get_profile(LocalRepositoryImpl(requireContext()).getEmail())
//        }
//        checkResult(result, "Профиля не существует")
//
//        var location_id: Int?
//
//        // Проверка наличия в базе локации отправки. Если нет, добавляем, если есть, получаем её id
//        if (DataBaseImpl().get_location(profile_id!!, binding.fsendpackEtAddressOrigin.text.toString()) == null) {
//            Log.e("TEST", "Try add location")
//            location_id = DataBaseImpl().add_location(profile_id!!, binding.fsendpackEtAddressOrigin.text.toString(), binding.fsendpackEtCountryOrgin.text.toString(),
//                binding.fsendpackEtPhoneOrigin.text.toString(), binding.fsendpackEtOthersOrigin.text.toString())
//        } else {
//            Log.e("TEST", "Try get location")
//            location_id = DataBaseImpl().get_location(profile_id!!, binding.fsendpackEtAddressOrigin.text.toString())
//        }
//
//        // Переменные для облегчения жизни
//        var weight_item = binding.fsendpackEtWeightItem.text.toString()
//        var worth_item = binding.fsendpackEtWorthItems.text.toString()
//        var package_items = binding.fsendpackEtPackageItems.text.toString()
//        var delivery_charge = binding.fsendpackTvDeliveryCharges.text.toString().toFloat()
//        var instant_delivery_charges = binding.fsendpackTvInstantDelivery.text.toString().toFloat()
//        var tax = binding.fsendpackTvTax.text.toString().toFloat()
//
//
//        Log.e("TEST", "Try add package")
//        // Добавление в бд записи о посылке и возврат её id
//        var package_id = DataBaseImpl().add_package(profile_id, package_items, weight_item, worth_item, location_id!!, instantDelivery, delivery_charge,
//            instant_delivery_charges, tax, uuid_code)
//
//        Log.e("TEST", "Try add destination")
//        // Добавление в бд локации финальной доставки и возврат её id
//        var destination_id = DataBaseImpl().add_location(profile_id, dest.last().address, dest.last().state_country, dest.last().phone_number, dest.last().others)
//
//        Log.e("TEST", "Try add transaction")
//        // Добавление в бд транзакции, доставки и пути доставки
//        DataBaseImpl().add_transaction(profile_id, package_id!!, binding.fsendpackTvAmount.text.toString().toFloat(), "")
//        Log.e("TEST", "Try add delivery")
//        DataBaseImpl().add_delivery(package_id!!, destination_id!!)
//        Log.e("TEST", "Try add routes")
//        DataBaseImpl().add_routes(profile_id, DataBaseImpl().get_delivery(package_id!!)!!)

//        CoroutineScope(Dispatchers.IO).launch {
//            var profile_id = DataBaseImpl().get_profile(LocalRepositoryImpl(requireContext()).getEmail())
//
//            var location_id: Int?
//
//            // Проверка наличия в базе локации отправки. Если нет, добавляем, если есть, получаем её id
//            if (DataBaseImpl().get_location(profile_id!!, binding.fsendpackEtAddressOrigin.text.toString()) == null) {
//                Log.e("TEST", "Try add location")
//                location_id = DataBaseImpl().add_location(profile_id!!, binding.fsendpackEtAddressOrigin.text.toString(), binding.fsendpackEtCountryOrgin.text.toString(),
//                    binding.fsendpackEtPhoneOrigin.text.toString(), binding.fsendpackEtOthersOrigin.text.toString())
//            } else {
//                Log.e("TEST", "Try get location")
//                location_id = DataBaseImpl().get_location(profile_id!!, binding.fsendpackEtAddressOrigin.text.toString())
//            }
//
//            // Переменные для облегчения жизни
//            var weight_item = binding.fsendpackEtWeightItem.text.toString()
//            var worth_item = binding.fsendpackEtWorthItems.text.toString()
//            var package_items = binding.fsendpackEtPackageItems.text.toString()
//            var delivery_charge = binding.fsendpackTvDeliveryCharges.text.toString().toFloat()
//            var instant_delivery_charges = binding.fsendpackTvInstantDelivery.text.toString().toFloat()
//            var tax = binding.fsendpackTvTax.text.toString().toFloat()
//
//
//            Log.e("TEST", "Try add package")
//            // Добавление в бд записи о посылке и возврат её id
//            var package_id = DataBaseImpl().add_package(profile_id, package_items, weight_item, worth_item, location_id!!, instantDelivery, delivery_charge,
//                instant_delivery_charges, tax, uuid_code)
//
//            Log.e("TEST", "Try add destination")
//            // Добавление в бд локации финальной доставки и возврат её id
//            var destination_id = DataBaseImpl().add_location(profile_id, dest.last().address, dest.last().state_country, dest.last().phone_number, dest.last().others)
//
//            Log.e("TEST", "Try add transaction")
//            // Добавление в бд транзакции, доставки и пути доставки
//            DataBaseImpl().add_transaction(profile_id, package_id!!, binding.fsendpackTvAmount.text.toString().toFloat(), "")
//            Log.e("TEST", "Try add delivery")
//            DataBaseImpl().add_delivery(package_id!!, destination_id!!)
//            Log.e("TEST", "Try add routes")
//            DataBaseImpl().add_routes(profile_id, DataBaseImpl().get_delivery(package_id!!)!!)
//        }

        val controller = findNavController()
        controller.navigate(R.id.transactionFragment)
    }

    private fun checkResult(result: Result<Int, String>, message: String) {
        when(result) {
            is Result.Success -> {

            }
            is Result.Error -> {

            }
        }
    }

    // Функция для генерации UUID кода (Трек-код)
    fun generateFormattedUUID(pattern: String): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val formattedUUID = StringBuilder()
        var uuidIndex = 0

        for (char in pattern) {
            when (char) {
                'R' -> formattedUUID.append("R-")
                'X' -> {
                    if (uuidIndex < uuid.length) {
                        formattedUUID.append(uuid[uuidIndex])
                        uuidIndex++
                    } else {
                        formattedUUID.append((0..9).random())
                    }
                }
                else -> formattedUUID.append(char)
            }
        }

        return formattedUUID.toString()
    }


    // Дата классы для облегчения жизни
    data class originDetails(
        val address: String,
        val state_country: String,
        val phone_number: String,
        val others: String = ""
    )

    data class destinationDetails(
        val address: String,
        val state_country: String,
        val phone_number: String,
        val others: String = ""
    )

    data class packageDetails(
        val package_items: String,
        val weight_item: Float,
        val worth_item: Float
    )

}