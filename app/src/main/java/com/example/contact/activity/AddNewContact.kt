package com.example.contact.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.remove
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.contact.R
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.mvvm.ContactViewModelFactory
import com.example.contact.room.Contact
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactDatabase
import com.example.contact.room.NumberEntity
import kotlinx.android.synthetic.main.activity_add_new_contact.*
import kotlinx.android.synthetic.main.add_number_layout.*

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
        contactViewModel =
            ViewModelProviders.of(this, viewModelFactory)[ContactViewModel::class.java]

        // Save button
        button.setOnClickListener {
            val Name =
                "${editTextTextPersonName.text.toString()} ${editTextTextPersonName2.text.toString()}"
            val Number = editTextTextPersonName4.text
            val number = NumberEntity(Name, Number.toString())
            val contact = Contact(Name)
            contactViewModel.addContact(contact)

            contactViewModel.addNumber(number)
            Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        // Cancel button
        imageView.setOnClickListener {
            onBackPressed()
        }

        addEditText()
    }

    private fun addEditText() {
        val infalater = LayoutInflater.from(this).inflate(R.layout.add_number_layout, null)
        linearLayout.addView(infalater, linearLayout.childCount)

        removeNumber.setOnClickListener {
            remove(infalater)
        }
        textNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    if (p0.length >= 1) {
                        addEditText()
                    }
                }
            }

        })
    }

    private fun remove(view: View) {
        linearLayout.removeView(view)

    }
}