package com.developer.dars111021

import Utils.NetworkHelper
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.developer.dars111021.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var networkHelper: NetworkHelper
    lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        networkHelper = NetworkHelper(this)
        if (networkHelper.isNetworkConnected()) {
            binding.text.text = "Connected"
            requestQueue = Volley.newRequestQueue(this)
            imageLoad()
        } else {
            binding.text.text = "Disconnected"
        }
    }

    private fun imageLoad() {
        val imageRequest =
            ImageRequest("https://i.imgur.com/Nwk25LA.jpg", object : Response.Listener<Bitmap> {
                override fun onResponse(response: Bitmap?) {
                    binding.image.setImageBitmap(response)
                    binding.text.text = "Finished"

                }

            }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888, object : Response
            .ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    binding.text.text = error?.message
                    binding.progress.visibility = View.VISIBLE
                    binding.image.visibility = View.GONE
                }

            })
        requestQueue.add(imageRequest)
    }
}