package com.example.contact.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
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
import java.util.*

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
        val number=intent.getStringExtra("number")
        tvNumber.text = number.toString()

        val name=intent.getStringExtra("name")
        tvName.text = name

        if (name != null) {
            val r = Random()
            val red = r.nextInt(255 - 0 + 1) + 0
            val green = r.nextInt(255 - 0 + 1) + 0
            val blue = r.nextInt(255 - 0 + 1) + 0

            val draw = GradientDrawable()
            draw.shape = GradientDrawable.OVAL
            draw.setColor(Color.rgb(red, green, blue))

            ButtonName.text = name[0].toString().uppercase()
            ButtonName.background=draw
        }

        delete.setOnClickListener{
            contactViewModel.deleteContact(name!!)
            Toast.makeText(this, "Contact Deleted", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        btnEdit.setOnClickListener{
            val intent=Intent(this,EditContact::class.java)
            intent.putExtra("changeName",name)
            intent.putExtra("number",number)
            startActivity(intent)
            finish()
        }

        back.setOnClickListener{
            onBackPressed()
        }

    }
}