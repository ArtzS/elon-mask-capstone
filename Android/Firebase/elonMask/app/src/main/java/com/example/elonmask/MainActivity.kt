package com.example.elonmask

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : Activity() {
    private val TAG = "MyActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSubmit = findViewById<Button>(R.id.button)
        val inputMaskNum = findViewById<EditText>(R.id.mask_count)
        buttonSubmit.setOnClickListener{
            //val dataMaskNum: String = maskCount.text.toString()
            val dataMaskNum: Int = inputMaskNum.text.toString().toInt()
            val linkVideo = "www.yahoo.com"

            uploadData(dataMaskNum, linkVideo )
            findViewById<EditText>(R.id.mask_count).setText("")

            Log.v(TAG, "-----------"+dataMaskNum+"------------")
        }

    }
    private fun uploadData(varCount: Int, varVideo: String){
        //val pDialog = ProgressDialog(this)
        val dbMask = FirebaseDatabase.getInstance("https://elonmask-b89af-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("data/id${UUID.randomUUID().toString()}")
        val calendar: Calendar = Calendar.getInstance()
        val varTimestamp = SimpleDateFormat("EEEE, dd-MMM-yyyy HH:mm:ss z").format(calendar.time)

        val postData = postData(
            maskCount = varCount,
            timeStamp = varTimestamp,
            videoAddr = varVideo
        )

        dbMask.setValue(postData).addOnSuccessListener {
            //refresh recyclerview
            Log.v(TAG, "==========="+postData+"===================")
        }
    }
}