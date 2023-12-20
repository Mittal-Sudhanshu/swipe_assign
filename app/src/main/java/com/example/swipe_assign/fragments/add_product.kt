package com.example.swipe_assign.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.swipe_assign.R
import com.example.swipe_assign.api.RetrofitClient
import com.example.swipe_assign.api.apiInterface
import com.example.swipe_assign.api.models.product_modelItem
import com.example.swipe_assign.databinding.FragmentAddProductBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class add_product : Fragment() {
    private val productService = RetrofitClient.instance.create(apiInterface::class.java)


    private var imageUri: Uri?=null

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri  =it
        binding.productImage.setImageURI(it)
    }


    private lateinit var binding:FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        binding.productImage
            .setOnClickListener {
            selectImage.launch("image/*")

        }
        binding.addButton.setOnClickListener {


            validateData()



        }




        return binding.root
    }

    private fun validateData() {
        if(binding.productCtegory.text.isNotEmpty() && binding.productTax.text.isNotEmpty()
            && binding.productName.text.isNotEmpty() && binding.productPrice.text.isNotEmpty()
            && imageUri!=null){


            var name = binding.productName.text.toString()
            var price = binding.productTax.text.toString().toDoubleOrNull() ?: 0.0
            var tax = binding.productTax.text.toString().toIntOrNull() ?: 0

            var imageUrl =imageUri.toString()
            var category = binding.productCtegory.text.toString()

            val newProduct =product_modelItem(
                image = imageUrl,
                price = price,
                product_name = name,
                product_type = category,
                tax = tax

            )


            productService.addProduct(newProduct).enqueue(object:Callback<product_modelItem>{
                override fun onResponse(
                    call: Call<product_modelItem>,
                    response: Response<product_modelItem>
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Product added successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(view?.context, get_product::class.java)
                    startActivity(intent)

                }

                override fun onFailure(call: Call<product_modelItem>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to add product: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })



        }
        else{
            Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
        }
    }


}


