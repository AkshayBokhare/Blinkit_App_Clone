package com.nts.userblinkitcone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nts.userblinkitcone.databinding.ItemViewProductCategoryBinding
import com.nts.userblinkitcone.models.Category

class CategoryAdapter(val categoryList:ArrayList<Category>):RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
      val binding = ItemViewProductCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
      val category = categoryList[position]

       holder.binding.apply {
           ivCategoryImage.setImageResource(category.image)
           ivCategoryTitle.text=category.title
       }
    }


    inner class CategoryViewHolder( val binding:ItemViewProductCategoryBinding):RecyclerView.ViewHolder(binding.root){

    }

}