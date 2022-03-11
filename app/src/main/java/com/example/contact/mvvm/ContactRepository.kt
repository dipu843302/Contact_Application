package com.example.contact.mvvm

import android.R
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Contacts
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contact.room.ContactEntity
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactRelation
import com.example.contact.room.NumberEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContactRepository(val contactDao: ContactDao, val context: Context) {

    private val contactLiveData = MutableLiveData<List<ContactRelation>>()
    val contact: LiveData<List<ContactRelation>>
        get() = contactLiveData

    private var listOfContacts = ArrayList<ContactRelation>()
    private var cursor: Cursor? = null

    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
     //  ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE.toString(),
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray()

    @SuppressLint("Range")
    fun storeAllContactsInDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            cursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            )
            var i:Long=1
            cursor?.moveToFirst()
            while (cursor?.moveToNext()!!) {
                val name =
                    cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

                val number =
                    cursor!!.getString(cursor!!.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

//                var number1 =
//                    cursor!!.getString(cursor!!.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE.toString()))
//                  if(number1.isNullOrEmpty()){
//                      number1="Empty"
//                  }
                val id=cursor!!.getLong(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Photo._ID))

                val uri: Uri = ContentUris.withAppendedId(Contacts.People.CONTENT_URI, id)
                val bitmap = Contacts.People.loadContactPhoto(context, uri, R.drawable.ic_menu_report_image, null)
                
                val contact = ContactEntity(id,name,uri.toString())
                val numberList = mutableListOf<NumberEntity>()
                val number_contact = NumberEntity(i,number,id)
                i++
                numberList.add(number_contact)

                val contactRelation = ContactRelation(contact, numberList)
                listOfContacts.add(contactRelation)
                contactDao.addContact(contact)

                contactDao.addNumber(number_contact)
                Log.d("Dipu",number_contact.toString())
            }

            contactDao.getAllContacts()
            contactLiveData.postValue(listOfContacts)

        }
    }

    fun addContact(contactEntity: ContactEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            contactDao.addContact(contactEntity)
        }
    }

    fun delete(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            contactDao.deleteContact(name)
        }
    }

    @SuppressLint("Range")
    fun getContactsAsPerSearch(search: String): LiveData<List<ContactRelation>> {
        return contactDao.getContactsAsPerSearch(search)
    }
    @SuppressLint("Range")
    fun getNumberFromSearch(search: String): LiveData<List<ContactRelation>> {
        return contactDao.getNumberFromSearch(search)
    }

    fun getAllContact(): LiveData<List<ContactRelation>> {
        return contactDao.getAllContacts()
    }

    suspend fun contactUpdate(contactRelation: ContactRelation) {
         contactDao.contactUpdate(contactRelation)
    }

    fun addNumber(numberEntity: NumberEntity){
        CoroutineScope(Dispatchers.IO).launch {
            contactDao.addNumber(numberEntity)
        }
    }
}