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
import androidx.navigation.fragment.findNavController
import com.nts.userblinkitcone.activity.UsersMainActivity
import com.nts.userblinkitcone.databinding.FragmentSignInBinding
import com.nts.userblinkitcone.utils.Utils


class SignInFragment : Fragment() {
    private lateinit var signInBinding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signInBinding =FragmentSignInBinding.inflate(layoutInflater)

        setStatusBarColor()
        getUserNumber()
        onContinueButtonClick()

        return signInBinding.root
    }

    private fun onContinueButtonClick() {

        signInBinding.btnContinue.setOnClickListener {

           val number =signInBinding.etUserNumber.text.toString()

           if (number.isEmpty() || number.length != 10){
               Utils.showToast(requireContext(),"Please enter valid phone number")

             // Just for bypass otp _ delete after Testing
//               startActivity(Intent(requireActivity(), UsersMainActivity::class.java))
//               requireActivity().finish()

           }else{
               val bundle =Bundle()
               bundle.putString("number",number)
               findNavController().navigate(R.id.action_signInFragment_to_OTPFragment,bundle)
           }
       }
    }

    private fun getUserNumber() {

      signInBinding.etUserNumber.addTextChangedListener(object :TextWatcher{

          override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

          }
          override fun onTextChanged(number: CharSequence?, start: Int, before: Int, count: Int) {
              val length = number?.length
              if (length == 10){
                  signInBinding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
              }else{
                  signInBinding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grayish_blue))
              }
          }
          override fun afterTextChanged(s: Editable?) {} //not implimnet
      })
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