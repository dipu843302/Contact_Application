package com.example.contact.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.contact.mvvm.ContactRepository
import com.example.contact.room.ContactEntity
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactRelation
import com.example.contact.room.NumberEntity
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryTest {

    private val contactLiveData = MutableLiveData<List<ContactRelation>>()
    private lateinit var userData: LiveData<List<ContactRelation>>


    @MockK
    lateinit var context: Context

    @MockK
    lateinit var contactDao: ContactDao

    @MockK
    lateinit var contactEntity: ContactEntity


    lateinit var contactRepository: ContactRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
       userData = contactLiveData
        contactRepository = ContactRepository(contactDao, context)
    }

    @Test
    fun deleteContact() {
        // Stub Calls
        coEvery {
            contactDao.deleteContact("name")
        } returns Unit
        //Calling function for testing
        runBlocking {
            contactRepository.delete("name")
        }
        // verifying the function
        coVerify {
            contactDao.deleteContact("name")

        }
    }


    @Test
    fun addContact() {
        every {
            contactDao.addContact(contactEntity)
        } returns Unit
        runBlocking {
            contactRepository.addContact(contactEntity)
        }
        coVerify {
            contactDao.addContact(contactEntity)
        }
    }

    @Test
    fun addContact_isNOtNULL() {
        every {
            contactDao.addContact(contactEntity)
        } returns Unit
        runBlocking {
            contactRepository.addContact(contactEntity)
        }
        coVerify {
           Truth.assertThat( contactDao.addContact(contactEntity)).isNotNull()
        }
    }


    @Test
    fun getContactFromSearch() {
        every {
            contactDao.getNumberFromSearch("search")
        } returns userData
        runBlocking {
            contactRepository.getNumberFromSearch("search")
        }
        coVerify {
            contactDao.getNumberFromSearch("search")
        }
    }

    @Test
    fun getContactFromSearch_isnotNUll() {
        every {
            contactDao.getNumberFromSearch("search")
        } returns userData
        runBlocking {
            contactRepository.getNumberFromSearch("search")
        }
        coVerify {
            Truth.assertThat(contactDao.getNumberFromSearch("search")).isNotNull()
        }
    }


    @Test
    fun getContactNameFromSearch() {
        every {
            contactDao.getContactsAsPerSearch("search")
        } returns userData
        runBlocking {
            contactRepository.getContactsAsPerSearch("search")
        }
        coVerify {
            contactDao.getContactsAsPerSearch("search")
        }
    }

    @Test
    fun getAllContact() {
        every {
            contactDao.getAllContacts()
        } returns userData
        runBlocking {
            contactRepository.getAllContact()
        }
        coVerify {
            contactDao.getAllContacts()
        }
    }

    @Test
    fun getAllContact_isNotNUll() {
        every {
            contactDao.getAllContacts()
        } returns userData
        runBlocking {
            contactRepository.getAllContact()
        }
        coVerify {
            Truth.assertThat(contactDao.getAllContacts()).isNotNull()
        }
    }



    @Test
    fun deleteNumber(){
        every {
            contactDao.deleteNumber("number")
        }
        runBlocking {
            contactRepository.deleteNumber("number")
        }
        coVerify {
            contactDao.deleteNumber("number")
        }
    }
    @Test
    fun deleteNumber_IsNotNull(){
        every {
            contactDao.deleteNumber("number")
        }
        runBlocking {
            contactRepository.deleteNumber("number")
        }
        coVerify {
           Truth.assertThat( contactDao.deleteNumber("number")).isNotNull()
        }
    }
}