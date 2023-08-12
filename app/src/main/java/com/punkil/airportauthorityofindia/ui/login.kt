package com.punkil.airportauthorityofindia.ui

import android.content.Context
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.punkil.airportauthorityofindia.MainActivity
import com.punkil.airportauthorityofindia.R

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val guest : Button= findViewById(R.id.continue_as_guest)
        guest.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            this.startActivity(intent)
        }

    }
}