package com.example.randomuserapp

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.randomuserapp.room.UserViewModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import android.view.WindowManager
import com.google.zxing.WriterException

import androidmads.library.qrgenearator.QRGContents

import androidmads.library.qrgenearator.QRGEncoder
import android.graphics.Bitmap
import android.util.Log
import android.view.Menu
import android.view.MenuItem


class DetailView : AppCompatActivity() {

    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(UserViewModel::class.java)

        val firstNameText: TextView = findViewById(R.id.dfname)
        val lastNameTextView: TextView = findViewById(R.id.dlname)
//        val dobText: TextView = findViewById(R.id.ddob)
        val phoneText: TextView = findViewById(R.id.dphone)
        val pic: ImageView = findViewById(R.id.dpic)
        val cityText: TextView = findViewById(R.id.city)
        val countryText: TextView = findViewById(R.id.country)

        val bundle: Bundle? = intent.extras
        val fName = bundle?.getString("fname")
        val lName = bundle?.getString("lname")
//        val dob = bundle?.getString("dob")
        val phone = bundle?.getString("phone")
        val city = bundle?.getString("city")
        val country = bundle?.getString("country")

        val picUrl = bundle?.getString("pic")
        Glide.with(this).load(picUrl).override(370).into(pic)

        firstNameText.text = fName
        lastNameTextView.text = lName
//        dobText.text = dateTimeFormatter(dob)
        phoneText.text = phone
        cityText.text = "$city,"
        countryText.text = country

        generateQrCode(fName.toString(), lName.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_menu){
            viewModel.allUser.observe(this,{
//                viewModel.deleteUser()
            })
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dateTimeFormatter(date: String?): String{
        val odt = OffsetDateTime.parse(date)
        val dtf = DateTimeFormatter.ofPattern("MMM dd, uuuu", Locale.ENGLISH);
        val newDob = dtf.format(odt)
        return newDob
    }

    private fun generateQrCode(fname: String, lname: String){

        val bitmap: Bitmap
        val qrgEncoder: QRGEncoder
        val manager = getSystemService(WINDOW_SERVICE) as WindowManager

        val qrCodeIV: ImageView = findViewById(R.id.qrcode)

        // initializing a variable for default display.

        // initializing a variable for default display.
        val display: Display = manager.defaultDisplay

        // creating a variable for point which
        // is to be displayed in QR Code.

        // creating a variable for point which
        // is to be displayed in QR Code.
        val point = Point()
        display.getSize(point)

        // getting width and
        // height of a point

        // getting width and
        // height of a point
        val width: Int = point.x
        val height: Int = point.y

        // generating dimension from width and height.

        // generating dimension from width and height.
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4

        qrgEncoder = QRGEncoder("$fname $lname", null, QRGContents.Type.TEXT, dimen)
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap()
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qrCodeIV.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString())
        }
    }
}