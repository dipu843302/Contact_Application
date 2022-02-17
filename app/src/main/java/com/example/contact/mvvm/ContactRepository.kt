package com.example.contact.mvvm

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contact.room.Contact
import com.example.contact.room.ContactDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContactRepository(val contactDao: ContactDao, val context: Context) {

    private val contactLiveData = MutableLiveData<List<Contact>>()

    val contact: LiveData<List<Contact>>
        get() = contactLiveData


    @SuppressLint("Range")
     fun getContactsAsPerSearch(search: String): LiveData<List<Contact>> {
        return contactDao.getContactsAsPerSearch(search)
    }


    private var listOfContacts = ArrayList<Contact>()
    private var rs: Cursor? = null

    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray()

    @SuppressLint("Range")
    fun storeAllContactsInDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            rs = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            )
           rs?.moveToFirst()
            while (rs?.moveToNext()!!) {
                val name =
                    rs!!.getString(rs!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
               rs!!.getString(rs!!.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val contact = Contact(name,number)
                listOfContacts.add(contact)
                contactDao.addContact(contact)
            }

            contactDao.getAllContacts()
            contactLiveData.postValue(listOfContacts)

        }
    }

    fun addContact(contact: Contact) {
        CoroutineScope(Dispatchers.IO).launch {
            contactDao.addContact(contact)
        }
    }

    fun delete(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            contactDao.deleteContact(name)
        }
    }

    fun getAllContact(): LiveData<List<Contact>> {
        return contactDao.getAllContacts()
    }
   suspend fun contactUpdate(oldName:String,NewName:String,number:String){
        contactDao.contactUpdate(oldName,NewName, number)
    }
}