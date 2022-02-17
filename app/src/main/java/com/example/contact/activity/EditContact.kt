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
import kotlinx.android.synthetic.main.activity_add_new_contact.editTextTextPersonName
import kotlinx.android.synthetic.main.activity_add_new_contact.editTextTextPersonName4
import kotlinx.android.synthetic.main.activity_add_new_contact.imageView
import kotlinx.android.synthetic.main.activity_edit_contact.*

class EditContact : AppCompatActivity() {

    lateinit var contactViewModel: ContactViewModel
    lateinit var contactRepository: ContactRepository
    lateinit var contactDao: ContactDao
    lateinit var contactDatabase: ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        contactDatabase = ContactDatabase.getDatabase(this)
        contactDao = contactDatabase.contactDao()
        contactRepository = ContactRepository(contactDao, this)
        val viewModelFactory = ContactViewModelFactory(contactRepository)
        contactViewModel = ViewModelProviders.of(this, viewModelFactory)[ContactViewModel::class.java]

        val intent: Intent =getIntent()
        val name=intent.getStringExtra("changeName")
        val oldName=name
        editTextTextPersonName.setText(name)

        val number=intent.getStringExtra("number")
        editTextTextPersonName4.setText(number)



        buttonSave.setOnClickListener{
            val updatedName=editTextTextPersonName.text.toString()
            val updatedNumber=editTextTextPersonName4.text.toString()
            contactViewModel.contactUpdate(oldName!!,updatedName,updatedNumber)
           val intent= Intent(this,ContactDetails::class.java)
            intent.putExtra("name", updatedName)
            intent.putExtra("number",updatedNumber)
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }

        // Cancel button
        imageView.setOnClickListener{
            onBackPressed()
        }


    }

}