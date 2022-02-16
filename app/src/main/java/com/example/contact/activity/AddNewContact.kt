package com.example.contact.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.contact.R
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.mvvm.ContactViewModelFactory
import com.example.contact.room.Contact
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactDatabase
import kotlinx.android.synthetic.main.activity_add_new_contact.*

class AddNewContact : AppCompatActivity() {

    lateinit var contactViewModel: ContactViewModel
    lateinit var contactRepository: ContactRepository
    lateinit var contactDao: ContactDao
    lateinit var contactDatabase: ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)

        contactDatabase = ContactDatabase.getDatabase(this)
        contactDao = contactDatabase.contactDao()
        contactRepository = ContactRepository(contactDao, this)
        val viewModelFactory = ContactViewModelFactory(contactRepository)
        contactViewModel = ViewModelProviders.of(this, viewModelFactory)[ContactViewModel::class.java]

        // Save button
        button.setOnClickListener{
            val Name="${editTextTextPersonName.text.toString()} ${editTextTextPersonName2.text.toString()}"
            val Number=editTextTextPersonName4.text
            val contact= Contact(Name)
            contactViewModel.addContact(contact)
            Toast.makeText(this,"Contact added", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        // Cancel button
        imageView.setOnClickListener{
            onBackPressed()
        }

        val intent: Intent =getIntent()
        val name=intent.getStringExtra("changeName")
        editTextTextPersonName.setText(name)

    }

}