package com.example.contact.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "number_table")
@Parcelize
data class NumberEntity(
    @PrimaryKey(autoGenerate = false)
    val Number_Id: Long,

    @ColumnInfo(name = "number1") var number1: String,
    @ColumnInfo(name = "name") var name: String = "",

    val Contact_Id: Long
) : Parcelable

