package com.example.abclauncher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_app_info.*

class AppInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)
        val providersList = intent.getStringArrayListExtra("providersList")
        val servicesList = intent.getStringArrayListExtra("servicesList")
        val receiversList = intent.getStringArrayListExtra("receiversList")

        Log.d("!@#", "AppInfoActivity: ${providersList.isNullOrEmpty()} ${servicesList.isNullOrEmpty()} ${receiversList.isNullOrEmpty()}")
        if (!providersList.isNullOrEmpty()) {
            for (item in providersList) {
                textViewProviders.append(item + "\n")
            }
        } else{
            textViewProviders.text = "No info about Providers"
        }


        if (!servicesList.isNullOrEmpty()) {
            for (item in servicesList) {
                textViewServices.append(item + "\n")
            }
        } else{
            textViewServices.text = "No info about Services"
        }

        if (!receiversList.isNullOrEmpty()) {
            for (item in receiversList) {
                textViewReceivers.append(item + "\n")
            }
        } else{
            textViewReceivers.text = "No info about Receivers"
        }
    }
}