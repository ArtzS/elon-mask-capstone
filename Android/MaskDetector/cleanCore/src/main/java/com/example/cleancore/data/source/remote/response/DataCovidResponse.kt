package com.example.cleancore.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class DataCovidResponse(
    @SerializedName("confirmed")
    var confirmed: Confirmed,
    @SerializedName("deaths")
    var deaths: Deaths,
    @SerializedName("lastUpdate")
    var lastUpdate: String,
    @SerializedName("recovered")
    var recovered: Recovered
)