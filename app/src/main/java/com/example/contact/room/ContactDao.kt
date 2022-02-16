package com.example.contact.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContact(contacts: Contact)

    @Query("SELECT * FROM contact_table WHERE name LIKE :search")
    fun getContactsAsPerSearch(search: String): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_table")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("Delete FROM contact_table WHERE name=:name")
    fun deleteContact(name: String)

}