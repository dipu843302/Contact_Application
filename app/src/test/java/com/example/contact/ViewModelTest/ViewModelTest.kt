package com.example.contact.ViewModelTest

import android.support.test.runner.AndroidJUnit4
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.room.Contact
import com.example.contact.room.NumberEntity
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewModelTest {

    private val contactLiveData = MutableLiveData<List<NumberEntity>>()
    lateinit var liveData: LiveData<List<NumberEntity>>

    @MockK
    lateinit var contactRepository: ContactRepository

    @MockK
    lateinit var contact: Contact

    @MockK
    lateinit var numberEntity: NumberEntity

    lateinit var contactViewModel: ContactViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        contactViewModel = ContactViewModel(contactRepository)
        liveData = contactLiveData
    }

    @Test
    fun deleteContact() {
        every {
            contactRepository.delete("name")
        } returns Unit

        runBlocking {
            contactViewModel.deleteContact("name")
        }
        verify {
            contactRepository.delete("name")
        }
    }

    @Test
    fun addContact() {
        every {
            contactRepository.addContact(contact)
        } returns Unit
        runBlocking {
            contactViewModel.addContact(contact)
        }
        verify {
            contactRepository.addContact(contact)
        }
    }

    @Test
    fun searchContact() {
        every {
            contactRepository.getContactsAsPerSearch("name")
        } returns liveData

        runBlocking {
            contactViewModel.searchContact("name")
        }
        verify {
            contactRepository.getContactsAsPerSearch("name")
        }
    }

    @Test
    fun addNumber() {
        every {
            contactRepository.addNumber(numberEntity)
        } returns Unit
        runBlocking {
            contactViewModel.addNumber(numberEntity)
        }
        verify {
            contactRepository.addNumber(numberEntity)
        }
    }

    @Test
    fun getAllContact(){
        every {
            contactRepository.getAllContact()
        }returns liveData
        runBlocking {
            contactViewModel.fetchAllContact()
        }
        verify {
            contactRepository.getAllContact()
        }
    }
    @Test
    fun searchByNumber(){
        every {
            contactRepository.getNumberFromSearch("number")
        }returns liveData
        runBlocking {
            contactViewModel.getNumberFromSearch("number")
        }
        verify {
            contactRepository.getNumberFromSearch("number")
        }
    }

    @Test
    fun contactUpdate(){
        coEvery {
            contactRepository.contactUpdate(numberEntity)
        }returns Unit
        runBlocking {
            contactViewModel.contactUpdate(numberEntity)
        }
        coVerify {
            contactRepository.contactUpdate(numberEntity)
        }
    }
}