package com.example.maskdetector.utils

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.widget.Toast
import com.example.maskdetector.R

object Utils {

        fun permissionDenied(context: Context){
        Toast.makeText(context, context.getString(R.string.permissionDenied), Toast.LENGTH_SHORT).show()
    }

        fun galleryAddPic(context: Context,currentPhotoPath : String) {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(currentPhotoPath),
            arrayOf("*/*"),
            object : MediaScannerConnection.MediaScannerConnectionClient {
                override fun onMediaScannerConnected() {}
                override fun onScanCompleted(path: String, uri: Uri) {
                }
            }
        )
    }


}