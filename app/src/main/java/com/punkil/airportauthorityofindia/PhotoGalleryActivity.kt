package com.punkil.airportauthorityofindia

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class PhotoGalleryActivity : AppCompatActivity() {

    private lateinit var photoImageView: ImageView
    private lateinit var changeImageButton: Button

    private val images = listOf(
        "https://www.aai.aero/sites/default/files/photo_gallery/IMG-20230605-WA0013.jpg",
        "https://www.aai.aero/sites/default/files/photo_gallery/_V0A7454.JPG"
    )

    private var currentImageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        photoImageView = findViewById(R.id.photoImageView)
        changeImageButton = findViewById(R.id.changeImageButton)

        // Load the first image by default
        loadImage(images[currentImageIndex])

        changeImageButton.setOnClickListener {
            currentImageIndex = (currentImageIndex + 1) % images.size
            loadImage(images[currentImageIndex])
        }

        photoImageView.setOnClickListener {
            // Toggle visibility of the ImageView and Button
            if (photoImageView.visibility == View.VISIBLE) {
                photoImageView.visibility = View.GONE
                changeImageButton.visibility = View.GONE
            } else {
                photoImageView.visibility = View.VISIBLE
                changeImageButton.visibility = View.VISIBLE
            }
        }
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .centerInside()
            .into(photoImageView)
    }
}
