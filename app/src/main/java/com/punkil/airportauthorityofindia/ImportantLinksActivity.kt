package com.punkil.airportauthorityofindia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.punkil.airportauthorityofindia.databinding.ActivityImportantLinksBinding

class ImportantLinksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImportantLinksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportantLinksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val importantLinks = listOf(
            "AAI Startup Initiative",
            "Other AAI Websites Link",
            "External Links",
            "Union Election",
            "NAC",
            "GST",
            "Promote digital payments",
            "AAI EDCPS",
            "AVSECQC",
            "Arbitration & Mediation",
            "Voluntary Safety Reporting"
        )

        val adapter = ImportantLinksAdapter(importantLinks)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}
