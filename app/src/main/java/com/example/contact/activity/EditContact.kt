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
import androidx.lifecycle.ViewModelProviders
import com.example.contact.R
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.mvvm.ContactViewModelFactory
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactDatabase
import com.example.contact.room.NumberEntity
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_add_new_contact.editTextTextPersonName
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

        val intent: Intent = intent
        val name=intent.getStringExtra("changeName")
        editTextTextPersonName.setText(name)

        val number=intent.getStringExtra("number")
        editTextTextPersonName4.setText(number)

        buttonSave.setOnClickListener{
            val updatedName=editTextTextPersonName.text.toString()
            val updatedNumber=editTextTextPersonName4.text.toString()

          //  val numberEntity=NumberEntity(updatedName,updatedNumber,"","","","")

           // contactViewModel.contactUpdate(numberEntity)

           val intent= Intent(this,ContactDetails::class.java)
            intent.putExtra("name", updatedName)
            intent.putExtra("number",updatedNumber)
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }

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
        imageView.setOnClickListener{
            onBackPressed()
        }

        addEditTextDynamic()

    } // set image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val uri: Uri = data?.data!!
        Camera.setImageURI(uri)
    }


    private fun addEditTextDynamic() {
        val infalater = LayoutInflater.from(this).inflate(R.layout.add_number_layout, null)
        ContainerlinearLayout.addView(infalater, ContainerlinearLayout.childCount)

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
        ContainerlinearLayout.removeView(view)
    }
}