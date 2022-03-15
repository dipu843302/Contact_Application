package com.example.contact.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "contact_table")
@Parcelize
data class ContactEntity (
    @PrimaryKey(autoGenerate = false)
    val Contact_Id:Long,
    @ColumnInfo(name="name")var name: String,
    @ColumnInfo(name="photo")var photo: String?
):Parcelable

