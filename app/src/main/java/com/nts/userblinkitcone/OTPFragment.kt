package com.nts.userblinkitcone

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nts.userblinkitcone.databinding.FragmentOTPBinding
import com.nts.userblinkitcone.utils.Utils
import com.nts.userblinkitcone.viewmodels.AuthViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class OTPFragment : Fragment() {
    private val authViewModel:AuthViewModel by viewModels()
    private lateinit var otpBinding: FragmentOTPBinding
    private lateinit var userNumber:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        otpBinding =FragmentOTPBinding.inflate(layoutInflater)

        setStatusBarColor()
        getUserNumber()
        customizingEnteringOTP()
        onBackButtonClick()
        sendOTP()
        //1.18.38 timestamp
        return otpBinding.root
    }

    private fun sendOTP() {

            Utils.showDialog(requireContext(),"Sending OTP...")

            authViewModel.apply {
                sendOtp(userNumber,requireActivity())
                lifecycleScope.launch {
                    otpsend.collect {otpSent->
                        if (otpSent){
                            Utils.hideDialog()
                            Utils.showToast(requireContext(),"OTP sent to the number...")
                        }
                    }
                }
            }
    }

    private fun onBackButtonClick() {
        otpBinding.tbOtpFragment.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_OTPFragment_to_signInFragment)
        }
    }

    private fun customizingEnteringOTP() {

       val editTexts = arrayOf(otpBinding.etOtp1,otpBinding.etOtp2,otpBinding.etOtp3,otpBinding.etOtp4,otpBinding.etOtp5,otpBinding.etOtp6)
        for (i in editTexts.indices){
            editTexts[i].addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { } //not implement no need

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }//not implement no need

                override fun afterTextChanged(s: Editable?) {
                   if (s?.length==1) {
                       if (i < editTexts.size - 1) {
                           editTexts[i + 1].requestFocus()
                       }
                   } else if (s?.length==0){
                           if (i>0){
                               editTexts[i-1].requestFocus()
                           }
                       }
                   }
            })
        }
    }

    private fun getUserNumber() {
        val bundle = arguments
        userNumber=bundle?.getString("number").toString()
        otpBinding.tvUserNumber.text =userNumber
    }

    private fun setStatusBarColor(){
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(),R.color.yellow)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                decorView.systemUiVisibility =View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }



}