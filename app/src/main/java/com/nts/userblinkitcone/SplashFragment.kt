package com.nts.userblinkitcone

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nts.userblinkitcone.activity.UsersMainActivity
import com.nts.userblinkitcone.databinding.FragmentSplashBinding
import com.nts.userblinkitcone.viewmodels.AuthViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private lateinit var splashBinding: FragmentSplashBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        splashBinding =FragmentSplashBinding.inflate(layoutInflater)

        setStatusBarColor()

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                authViewModel.isCurrentUser.collect{
                    if (it){
                        startActivity(Intent(requireActivity(),UsersMainActivity::class.java))
                        requireActivity().finish()
                    }else{
                        findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
                    }
                }
            }
        },3000)



        return splashBinding.root
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