package com.example.abclauncher

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appDrawerButton: Button = findViewById(R.id.app_drawer_button)

        appDrawerButton.setOnClickListener {
            val intent = Intent(this, AppsDrawerActivity::class.java)
            startActivity(intent)
        }
    }



}