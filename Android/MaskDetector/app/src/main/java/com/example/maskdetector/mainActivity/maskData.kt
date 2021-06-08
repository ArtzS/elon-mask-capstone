package com.example.maskdetector.mainActivity

import com.google.firebase.Timestamp

data class maskData(
    val time: Timestamp,
    val withMask: Int,
    val withoutMask: Int
    )
