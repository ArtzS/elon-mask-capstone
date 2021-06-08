package com.example.cleancore.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class Deaths(
    @SerializedName("detail")
    var detail: String,
    @SerializedName("value")
    var value: Int
)