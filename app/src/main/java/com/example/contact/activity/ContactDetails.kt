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
import kotlinx.android.synthetic.main.activity_contact_details.*

class ContactDetails : AppCompatActivity() {

    lateinit var contactViewModel: ContactViewModel
    lateinit var contactRepository: ContactRepository
    lateinit var contactDao: ContactDao
    lateinit var contactDatabase: ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        contactDatabase = ContactDatabase.getDatabase(this)
        contactDao = contactDatabase.contactDao()
        contactRepository = ContactRepository(contactDao, this)
        val viewModelFactory = ContactViewModelFactory(contactRepository)
        contactViewModel = ViewModelProviders.of(this, viewModelFactory)[ContactViewModel::class.java]



        val intent: Intent =getIntent()
//        val number=intent.getIntExtra("number",0)
//        tvNumber.setText(number.toString())

        val name=intent.getStringExtra("name")
        tvName.setText(name)

        delete.setOnClickListener{
            contactViewModel.deleteContact(name!!)
            Toast.makeText(this, "Contact Deleted", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        btnEdit.setOnClickListener{
            val intent=Intent(this,AddNewContact::class.java)
            intent.putExtra("changeName",name)
            startActivity(intent)
        }

        back.setOnClickListener{
            onBackPressed()
        }

    }
}