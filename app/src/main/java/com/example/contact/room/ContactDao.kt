package com.example.contact.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContact(contacts: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNumber(numberEntity: NumberEntity)

    @Query("SELECT * FROM number_table WHERE name LIKE :search" )
    fun getContactsAsPerSearch(search: String): LiveData<List<NumberEntity>>

    @Query("SELECT * FROM number_table")
    fun getAllContacts(): LiveData<List<NumberEntity>>

    @Query("Delete FROM number_table WHERE name=:name")
    fun deleteContact(name: String)

    @Query("Update number_table Set name=:NewName")
    suspend fun contactUpdate(NewName:String)

    @Query("Update number_table Set number=:number ")
    suspend fun numberUpdate(number:String)

    @Query("SELECT * FROM number_table WHERE name LIKE :search or number like :search" )
    fun getNumberFromSearch(search: String): LiveData<List<NumberEntity>>

}