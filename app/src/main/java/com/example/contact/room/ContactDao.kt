package com.example.contact.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ContactDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContact(contacts: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNumber(numberEntity: NumberEntity)

    @Transaction
    @Query("SELECT * FROM contact_table WHERE  name LIKE '%' || :search || '%' " )
    fun getContactsAsPerSearch(search: String): LiveData<List<ContactRelation>>

    @Transaction
    @Query("SELECT * FROM contact_table")
    fun getAllContacts(): LiveData<List<ContactRelation>>

    @Query("Delete FROM contact_table WHERE name=:name")
    fun deleteContact(name: String)

    @Delete
    fun deleteNumber(contacts: NumberEntity)

    @Query("select * from number_table where name=:name")
    fun getAllDeleteDataNumber(name: String): LiveData<List<NumberEntity>>

//    @Query("Update number_table Set name=:NewName")
//    @Update
//    suspend fun contactUpdate(contactRelation: ContactRelation)

    @Query("Update number_table Set number1=:number ")
    suspend fun numberUpdate(number:String)

    @Transaction
    @Query("SELECT * FROM number_table WHERE number1 LIKE :search or name LIKE:search" )
    fun getNumberFromSearch(search: String): LiveData<List<ContactRelation>>

    @Transaction
    @Query("SELECT * FROM contact_table WHERE name=:name")
    fun getContactNumber(name:String):LiveData<List<ContactRelation>>
}