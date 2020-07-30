package com.example.abclauncher

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_app_info.*

class AppInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)
        prepareInfo()


    }

    fun prepareInfo(){
        val packageName = intent.getStringExtra("packageName")
        val providersInfo = this.packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_PROVIDERS
        )
        val servicesInfo = this.packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_SERVICES
        )
        val receiversInfo = this.packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_RECEIVERS
        )


        if (!providersInfo.providers.isNullOrEmpty()) {
            for (provider in providersInfo.providers) {
                textViewProviders.append(provider.name)
            }
        }
        if (!servicesInfo.services.isNullOrEmpty()) {
            for (service in servicesInfo.services) {
                textViewServices.append(service.name)
            }
        }
        if (!receiversInfo.receivers.isNullOrEmpty()) {
            for (receiver in receiversInfo.receivers) {
                textViewReceivers.append(receiver.name)
            }
        }
    }
}