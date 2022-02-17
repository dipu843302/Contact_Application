package com.example.contact.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContact(contacts: Contact)

    @Query("SELECT * FROM contact_table WHERE name LIKE :search or number like :search" )
    fun getContactsAsPerSearch(search: String): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_table")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("Delete FROM contact_table WHERE name=:name")
    fun deleteContact(name: String)

    @Query("Update contact_table Set name=:NewName,number=:number WHERE name=:oldName")
    suspend fun contactUpdate(oldName:String,NewName:String,number:String)

}