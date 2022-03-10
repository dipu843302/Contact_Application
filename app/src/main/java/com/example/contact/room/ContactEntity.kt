package com.example.contact.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class ContactEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="name")var name: String,
)

