package com.example.contact.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.contact.mvvm.ContactRepository
import com.example.contact.room.Contact
import com.example.contact.room.ContactDao
import com.example.contact.room.NumberEntity
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

    private val contactLiveData = MutableLiveData<List<NumberEntity>>()
    private lateinit var userData: LiveData<List<NumberEntity>>

    @MockK
    lateinit var mockRepository: ContactRepository

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var contactDao: ContactDao

    @MockK
    lateinit var contact: Contact

    @MockK
    lateinit var numberEntity: NumberEntity
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
            contactDao.addContact(contact)
        } returns Unit
        runBlocking {
            contactRepository.addContact(contact)
        }
        coVerify {
            contactDao.addContact(contact)
        }
    }

    @Test
    fun contactUpdate() {
        coEvery {
            contactDao.contactUpdate(numberEntity)
        } returns Unit
        runBlocking {
            contactRepository.contactUpdate(numberEntity)
        }
        coVerify {
            contactDao.contactUpdate(numberEntity)
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
    fun storeAllContactsInDatabase() {
        every {
            mockRepository.storeAllContactsInDatabase()
        } returns Unit
        runBlocking {
            contactRepository.storeAllContactsInDatabase()
        }
        coVerify {
            mockRepository.storeAllContactsInDatabase()
        }
    }
}