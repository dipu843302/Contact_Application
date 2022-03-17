package com.example.contact.ViewModelTest

import android.support.test.runner.AndroidJUnit4
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.room.ContactEntity
import com.example.contact.room.ContactRelation
import com.example.contact.room.NumberEntity
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class ViewModelTest {

    private val contactLiveData = MutableLiveData<List<ContactRelation>>()
    lateinit var liveData: LiveData<List<ContactRelation>>

    @MockK
    lateinit var contactRepository: ContactRepository

    @MockK
    lateinit var contactEntity: ContactEntity

    @MockK
    lateinit var numberEntity: NumberEntity

    lateinit var contactViewModel: ContactViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        contactViewModel = ContactViewModel(contactRepository)
        // liveData = contactLiveData
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
        coEvery {
            contactRepository.addContact(contactEntity)
        } returns Unit
        runBlocking {
            contactViewModel.addContact(contactEntity)
        }
        coVerify {
            contactRepository.addContact(contactEntity)
        }
    }

    @Test
    fun searchContact() {
        val mutableList = mutableListOf<ContactRelation>()
        coEvery {
            contactRepository.getContactsAsPerSearch("name").value
        } returns mutableList

        runBlocking {
            contactViewModel.searchContact("name").value
        }
        coVerify {
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
    fun getAllContact() {
        val mutableList3 = mutableListOf<ContactRelation>()
        coEvery {
            contactRepository.getAllContact().value
        } returns mutableList3
        runBlocking {
            contactViewModel.fetchAllContact()
        }
        coVerify {
            contactRepository.getAllContact()
        }
    }

    @Test
    fun storeData() {
        every {
            contactRepository.storeAllContactsInDatabase()
        } returns Unit
        runBlocking {
            contactViewModel.storeData()
        }
        verify {
            contactRepository.storeAllContactsInDatabase()
        }
    }

    @Test
    fun searchByNumber() {
        val mutableList2 = mutableListOf<ContactRelation>()
        coEvery {
            contactRepository.getNumberFromSearch("number").value
        } returns mutableList2
        runBlocking {
            contactViewModel.getNumberFromSearch("number").value
        }
        coVerify {
            contactRepository.getNumberFromSearch("number").value
        }
    }

    @Test
    fun deleteNumber(){
        every {
            contactRepository.deleteNumber("number")
        } returns Unit
        runBlocking {
            contactViewModel.deleteNumber("number")
        }
        coVerify {
            contactRepository.deleteNumber("number")
        }
    }

//    @Test
//    fun contactUpdate() {
//        coEvery {
//            contactRepository.contactUpdate(numberEntity)
//        } returns Unit
//        runBlocking {
//            contactViewModel.contactUpdate(numberEntity)
//        }
//        coVerify {
//            contactRepository.contactUpdate(numberEntity)
//        }
//    }
}