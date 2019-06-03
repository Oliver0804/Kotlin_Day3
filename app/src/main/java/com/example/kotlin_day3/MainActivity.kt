package com.example.kotlin_day3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val ACTION_CAMERA_REQUEST_CODE = 100
    private val ACTION_ALBUM_REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //cameraAppButton.setOnClickListener(cameraAppButtonHandler)
        cam_button.setOnClickListener(cameraAppButtonHandler)
        //album_button.setOnClickListener(albumAppButtonHandler)
        album_button.setOnClickListener(albumAppButtonHandler)
        //imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.scaleType= ImageView.ScaleType.CENTER_CROP

    }

    // 通過 intent 使用 Camera
    private fun takeImageFromCameraWithIntent() {
        println("take image from camera")
        //val intent = Intent()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, ACTION_CAMERA_REQUEST_CODE)
    }

    // 通過 intent 使用 album
    private fun takeImageFromAlbumWithIntent() {
        println("take image from album")

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, ACTION_ALBUM_REQUEST_CODE)
    }

    private val cameraAppButtonHandler = View.OnClickListener { view ->
        takeImageFromCameraWithIntent()
    }

    private val albumAppButtonHandler = View.OnClickListener { view ->
        takeImageFromAlbumWithIntent()
    }

    private fun displayImage(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("收到 result code $requestCode")

        when(requestCode) {
            ACTION_CAMERA_REQUEST_CODE -> {
                if(resultCode == Activity.RESULT_OK && data != null){
                    displayImage(data.extras.get("data") as Bitmap)
                }
            }

            ACTION_ALBUM_REQUEST_CODE -> {
                if(resultCode == Activity.RESULT_OK && data != null){
                    val resolver = this.contentResolver
                    val bitmap = MediaStore.Images.Media.getBitmap(resolver, data?.data)
                    displayImage(bitmap)

                }
            }
            else -> {
                println("no handler onActivityReenter")
            }
        }
    }
}