package com.example.contact.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "number_table")
data class NumberEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "number1") var number1: String,
    @ColumnInfo(name = "number2") var number2: String?,
    @ColumnInfo(name = "number3") var number3: String?,
    @ColumnInfo(name = "number4") var number4: String?,
    @ColumnInfo(name = "photo") var photo: String,

    )






