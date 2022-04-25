package com.example.randomuserapp

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.randomuserapp.data.*
import com.example.randomuserapp.room.UserViewModel
import android.os.Build

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import java.io.IOException

import com.mikhaellopez.circularimageview.CircularImageView

class AddUserActivity : AppCompatActivity() {

    val PICK_PHOTO_CODE = 1046

    lateinit var viewModel: UserViewModel
    var image: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val button: Button = findViewById(R.id.addUser)
        val circularImageView: CircularImageView = findViewById(R.id.iv_camera)
        circularImageView.setOnClickListener{
            onPickPhoto(it)
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(UserViewModel::class.java)

        val fNameText :TextView = findViewById(R.id.afname)
        val lNameText :TextView = findViewById(R.id.alname)
        val phoneText :TextView = findViewById(R.id.aphone)
        val cityText :TextView = findViewById(R.id.acity)
        val countryText :TextView = findViewById(R.id.acountry)

        val fName = fNameText.text
        val lName = lNameText.text
        val phone = phoneText.text
        val city = cityText.text
        val country = countryText.text

        button.setOnClickListener{
            if (fName.isEmpty() || lName.isEmpty() || city.isEmpty() || country.isEmpty() || phone.isEmpty()){
                Toast.makeText(this, "Please fill all deatils", Toast.LENGTH_SHORT).show()
            }
            else{
                val name = Name(fName.toString(), lName.toString(), "mr")
                val date = Dob(12, "12 june")
                val picture = Picture("large")
                val location = Location(city.toString(), country.toString())
                val result = Result(0, date, location, name, phone.toString(), picture)
                viewModel.addUser(result)

                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun onPickPhoto(view: View?) {
        // Create intent for picking a photo from the gallery
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(packageManager) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE)
        }
    }

    fun loadFromUri(photoUri: Uri?): Bitmap? {
        var image: Bitmap? = null
        try {
            // check version of Android on device
            image = if (Build.VERSION.SDK_INT > 27) {
                // on newer versions of Android, use the new decodeBitmap method
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(this.contentResolver, photoUri!!)
                ImageDecoder.decodeBitmap(source)
            } else {
                // support older versions of Android by using getBitmap
                MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == PICK_PHOTO_CODE) {
            val photoUri = data.data

            // Load the image located at photoUri into selectedImage
//            val selectedImage = loadFromUri(photoUri)
            image = loadFromUri(photoUri)

            // Load the selected image into a preview
            val ivPreview: CircularImageView = findViewById(R.id.profilePic)
            ivPreview.setImageBitmap(image)
        }
    }
}