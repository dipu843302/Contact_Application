package com.example.contact.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.contact.R
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.mvvm.ContactViewModelFactory
import com.example.contact.room.ContactEntity
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactDatabase
import com.example.contact.room.NumberEntity
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_add_new_contact.*
import kotlin.random.Random

class AddNewContact : AppCompatActivity() {

    lateinit var contactViewModel: ContactViewModel
    lateinit var contactRepository: ContactRepository
    lateinit var contactDao: ContactDao
    lateinit var contactDatabase: ContactDatabase

    lateinit var num:NumberEntity

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
            val randomNumber:Long=Random.nextLong()
            val Name =
                "${editTextTextPersonName.text.toString()} ${editTextTextPersonName2.text.toString()}"

            for(i in 0 until linearLayoutText.childCount-1){
                var number:EditText=linearLayoutText.getChildAt(i).findViewById(R.id.textNumber)
                num = NumberEntity(Random.nextLong(), number.text.toString(),Name,randomNumber)

                contactViewModel.addNumber(num)
            }

            val contact = ContactEntity(randomNumber,Name,"photo")
            contactViewModel.addContact(contact)

            Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

       // Dynamic view adding
        addEditText()

        // Cancel button
        imageView.setOnClickListener {
            onBackPressed()
        }

        // take a image from gallery
        textView5.setOnClickListener {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }
    }

    // set image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val uri: Uri = data?.data!!
        imageView3.setImageURI(uri)
    }

    private fun addEditText() {
        val infalater = LayoutInflater.from(this).inflate(R.layout.add_number_layout, null)
        linearLayoutText.addView(infalater, linearLayoutText.childCount)

        val numText: EditText = infalater.findViewById(R.id.textNumber)
        val remove: ImageView = infalater.findViewById(R.id.removeNumber)

        remove.setOnClickListener {
            remove(infalater)
        }
        numText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    if (p0.toString().length == 1) {
                        addEditText()
                    }
                }
            }

        })
    }

    private fun remove(view: View) {
        linearLayoutText.removeView(view)

    }
}