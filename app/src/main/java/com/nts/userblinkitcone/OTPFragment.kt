package com.nts.userblinkitcone

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nts.userblinkitcone.activity.UsersMainActivity
import com.nts.userblinkitcone.databinding.FragmentOTPBinding
import com.nts.userblinkitcone.models.Users
import com.nts.userblinkitcone.utils.Utils
import com.nts.userblinkitcone.viewmodels.AuthViewModel
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
        onLoginButtonClicked()



        return otpBinding.root
    }

    private fun onLoginButtonClicked() {
        otpBinding.btnLogin.setOnClickListener {


            Utils.showDialog(requireContext(),"Signing you...")
            val editTexts = arrayOf(otpBinding.etOtp1,otpBinding.etOtp2,otpBinding.etOtp3,otpBinding.etOtp4,otpBinding.etOtp5,otpBinding.etOtp6)
           val otp= editTexts.joinToString (""){it.text.toString() }
            if (otp.length < editTexts.size){
                Utils.showToast(requireContext(),"Please enter right Otp")
            }else{
                editTexts.forEach { it.text?.clear() ; it.clearFocus() }
                verifyOtp(otp)
            }

        }
    }


    private fun verifyOtp(otp: String) {

        val user = Users(uid = Utils.getCurrentUserId(), userPhoneNumber = userNumber,userAddress = null)

            authViewModel.signInWithPhoneAuthCredential(otp,userNumber,user)
            lifecycleScope.launch {
                authViewModel.isSignedInSuccessfully.collect{
                    if (it){

                        Utils.hideDialog()
                        Utils.showToast(requireContext(),"Logged In Done")
                        startActivity(Intent(requireActivity(),UsersMainActivity::class.java))
                        requireActivity().finish()
                    }
                }
            }
    }

    private fun sendOTP() {

            Utils.showDialog(requireContext(),"Sending OTP...")

            authViewModel.apply {
                sendOtp(userNumber,requireActivity())
                lifecycleScope.launch {
                    otpsent.collect {otpSent->
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