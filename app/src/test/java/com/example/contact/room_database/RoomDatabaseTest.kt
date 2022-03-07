package com.example.contact.room_database

import android.content.Context
import com.example.contact.room.ContactDatabase
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import org.junit.Before
import org.junit.Test

class RoomDatabaseTest {
    @MockK
    lateinit var context: Context
    @MockK
    lateinit var contactDatabase: ContactDatabase
    @MockK
    @Before
    fun set(){
        MockKAnnotations.init(this)
    }

    @Test
    fun getContactDatabase_equalityCheck(){
        mockkObject(ContactDatabase)
        every {
            ContactDatabase.getDatabase(context)
        }returns contactDatabase

        val result=ContactDatabase.getDatabase(context)
        Truth.assertThat(result).isEqualTo(contactDatabase)
    }
    @Test
    fun getContactDatabase_NullCheck(){
        mockkObject(ContactDatabase)
        every {
            ContactDatabase.getDatabase(context)
        }returns contactDatabase
         val result=ContactDatabase.getDatabase(context)
        Truth.assertThat(result).isNotNull()
    }


}