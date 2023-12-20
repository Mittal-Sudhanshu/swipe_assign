package com.example.swipe_assign.api.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.swipe_assign.R
import com.example.swipe_assign.api.models.product_modelItem
import com.squareup.picasso.Picasso
class product_adapter(val context: Activity, private var originalProductList: List<product_modelItem>) :
    RecyclerView.Adapter<product_adapter.MyViewHolder>() {

    private var filteredProductList: List<product_modelItem> = originalProductList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.eachitem, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filteredProductList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = filteredProductList[position]
        holder.title.text = currentItem.product_name
        holder.price.text = "price : ${currentItem.price}"
        holder.categor.text = "category: ${currentItem.product_type}"
        holder.tax.text = "Tax : ${currentItem.tax}"

        if (currentItem.image.isNotEmpty()) {
            Picasso.get().load(currentItem.image).into(holder.image);
        }
    }

    fun filterProducts(query: String) {
        filteredProductList = if (query.isEmpty()) {
            originalProductList
        } else {
            originalProductList.filter {
                it.product_name.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.productName)
        var image: ImageView = itemView.findViewById(R.id.productImage)
        var price: TextView = itemView.findViewById(R.id.productPrice)
        var categor: TextView = itemView.findViewById(R.id.productCtegory)
        var tax: TextView = itemView.findViewById(R.id.productTax)
    }
}
