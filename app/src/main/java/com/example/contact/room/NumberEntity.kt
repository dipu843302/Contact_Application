package com.example.contact.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "number_table")
data class NumberEntity(
    @PrimaryKey(autoGenerate = false)
    val Number_Id: Long,

    @ColumnInfo(name = "number1") var number1: String,

    val Contact_Id: Long


)






