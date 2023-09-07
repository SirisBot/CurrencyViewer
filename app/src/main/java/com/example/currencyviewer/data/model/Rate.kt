package com.example.currencyviewer.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rate(
    @Expose
    val id: String,
    @Expose
    val symbol: String,
    @Expose
    val currencySymbol: String?,
    @Expose
    val type: String,
    @Expose
    val rateUsd: String,
): Parcelable