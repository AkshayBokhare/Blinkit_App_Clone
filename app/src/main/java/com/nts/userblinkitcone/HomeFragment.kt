package com.nts.userblinkitcone

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.nts.userblinkitcone.adapters.CategoryAdapter
import com.nts.userblinkitcone.databinding.FragmentHomeBinding
import com.nts.userblinkitcone.models.Category
import com.nts.userblinkitcone.utils.Constants


class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)

        setStatusBarColor()
        setAllCategories()




        return homeBinding.root
    }

    private fun setAllCategories() {
        val categoryList =ArrayList<Category>()
        for (i in 0 until  Constants.allProductsCategory.size){
            categoryList.add(Category(Constants.allProductsCategory[i],Constants.allProductsCategoryIcon[i]))
        }
        homeBinding.rvCategories.adapter =CategoryAdapter(categoryList)
    }

    private fun setStatusBarColor(){
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(),R.color.orange)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                decorView.systemUiVisibility =View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}