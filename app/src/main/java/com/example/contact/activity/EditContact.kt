package com.example.contact.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.contact.R
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.mvvm.ContactViewModelFactory
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactDatabase
import com.example.contact.room.Delete
import com.example.contact.room.NumberEntity
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_add_new_contact.editTextTextPersonName
import kotlinx.android.synthetic.main.activity_add_new_contact.imageView
import kotlinx.android.synthetic.main.activity_edit_contact.*
import kotlin.random.Random

class EditContact : AppCompatActivity() {

    lateinit var contactViewModel: ContactViewModel
    lateinit var contactRepository: ContactRepository
    lateinit var contactDao: ContactDao
    lateinit var contactDatabase: ContactDatabase

    var a = 0
    var nameG = ""
    var list = mutableListOf<Delete>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        contactDatabase = ContactDatabase.getDatabase(this)
        contactDao = contactDatabase.contactDao()
        contactRepository = ContactRepository(contactDao, this)
        val viewModelFactory = ContactViewModelFactory(contactRepository)
        contactViewModel =
            ViewModelProviders.of(this, viewModelFactory)[ContactViewModel::class.java]

        val intent: Intent = intent
        val name = intent.getStringExtra("changeName")
        editTextTextPersonName.setText(name)

        buttonSave.setOnClickListener {
            val updatedName = editTextTextPersonName.text.toString()

            if (ContainerLinearLayout.childCount > 0) {
                val num: EditText =
                    ContainerLinearLayout.getChildAt(0).findViewById(R.id.textNumber)
                val numberEntity =
                    NumberEntity(Random.nextLong(), num.toString(), updatedName, Random.nextLong())
                contactViewModel.addNumber(numberEntity)

            }
            for (i in 0 + a until ContainerLinearLayout.childCount - 1) {
                val num: EditText =
                    ContainerLinearLayout.getChildAt(0).findViewById(R.id.textNumber)
                val numberEntity =
                    NumberEntity(Random.nextLong(), num.toString(), updatedName, Random.nextLong())
                contactViewModel.addNumber(numberEntity)
            }

            val intent = Intent(this, ContactDetails::class.java)
            intent.putExtra("name", updatedName)
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }
        contactViewModel.fetchNumberForEdit(editTextTextPersonName.text.toString()).observe(this,
            Observer {
                if (it.isNotEmpty()) {
                    a += it.size
                    nameG = it[0].name
                    editTextTextPersonName.setText(it[0].name)
                    it.forEach {
                        addNew(it.number1)
                    }
                }

            })


        Camera.setOnClickListener {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }
        // Cancel button
        imageView.setOnClickListener {
            onBackPressed()
        }


        addEditTextDynamic()


    }

    private fun addNew(number1: String) {

        val infalater = LayoutInflater.from(this).inflate(R.layout.add_number_layout, null)

        ContainerLinearLayout.addView(infalater, ContainerLinearLayout.childCount)
        val delete: ImageView = infalater.findViewById(R.id.removeNumber)
        var a = ""
        for (i in 0 until 10) {
            a += number1[i]
        }
        val num: EditText = infalater.findViewById(R.id.textNumber)
        num.setText(a)
        delete.setOnClickListener {
            val d = Delete(a, nameG)
            list.add(d)
            removeEditText(infalater)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val uri: Uri = data?.data!!
        Camera.setImageURI(uri)
    }


    private fun addEditTextDynamic() {
        val infalater = LayoutInflater.from(this).inflate(R.layout.add_number_layout, null)
        ContainerLinearLayout.addView(infalater, ContainerLinearLayout.childCount)

        val numText: EditText = infalater.findViewById(R.id.textNumber)
        val remove: ImageView = infalater.findViewById(R.id.removeNumber)

        remove.setOnClickListener {
            removeEditText(infalater)
        }
        numText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    if (p0.length == 1) {
                        addEditTextDynamic()
                    }
                }
            }
        })
    }

    private fun removeEditText(view: View) {
        ContainerLinearLayout.removeView(view)
    }
}