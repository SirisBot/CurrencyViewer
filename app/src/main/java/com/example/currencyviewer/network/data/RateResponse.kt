package com.example.currencyviewer.network.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class RateResponse (
    @Expose
    val data: List<Rate>
): Parcelable