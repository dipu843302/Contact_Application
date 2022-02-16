package com.example.contact.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
 data class Contact (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name="name")val name: String
    //@ColumnInfo(number="name")val name: String
)

