package com.example.abclauncher

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var chromeButton: ImageView = findViewById(R.id.chromeButton)
        chromeButton.setImageDrawable(getActivityIcon(this,"com.android.chrome", "com.google.android.apps.chrome.Main"))
        chromeButton.setOnClickListener {
            onChromeButtonClick()
        }
    }

    fun onChromeButtonClick() {
        val launchIntent =
            packageManager.getLaunchIntentForPackage("com.android.chrome")
        startActivity(launchIntent)

    }
    fun getActivityIcon(
        context: Context,
        packageName: String,
        activityName: String
    ): Drawable? {
        val pm: PackageManager = context.packageManager
        val intent = Intent()
        intent.component = ComponentName(packageName, activityName)
        val resolveInfo = pm.resolveActivity(intent, 0)
        return resolveInfo.loadIcon(pm)
    }
}