package com.company.app.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val id: String) : Parcelable
