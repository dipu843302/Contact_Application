package com.example.contact.mvvm

import androidx.lifecycle.*
import com.example.contact.room.Contact
import com.example.contact.room.NumberEntity
import kotlinx.coroutines.launch


class ContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {

    fun fetchAllContact(): LiveData<List<NumberEntity>> {
        contactRepository.storeAllContactsInDatabase()
        return contactRepository.getAllContact()
    }

    fun addContact(contact: Contact) {
        contactRepository.addContact(contact)
    }

    fun searchContact(search: String) =
        contactRepository.getContactsAsPerSearch("%$search%")

    fun getNumberFromSearch(search: String) =
        contactRepository.getNumberFromSearch("%$search%")

    fun deleteContact(name: String) {
        contactRepository.delete(name)
    }

    fun contactUpdate(oldName:String,NewName:String,number:String){
        viewModelScope.launch {
            contactRepository.contactUpdate(oldName,NewName,number)
        }
    }

    fun addNumber(numberEntity: NumberEntity){
        contactRepository.addNumber(numberEntity)
    }
}