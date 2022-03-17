package com.example.contact.activity


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.Interface.CallClickListener
import com.example.contact.R
import com.example.contact.adapter.NumbersAdapter
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.mvvm.ContactViewModelFactory
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactDatabase
import com.example.contact.room.ContactEntity
import com.example.contact.room.NumberEntity
import kotlinx.android.synthetic.main.activity_contact_details.*
import java.util.*


class ContactDetails : AppCompatActivity(),CallClickListener {

    lateinit var contactViewModel: ContactViewModel
    lateinit var contactRepository: ContactRepository
    lateinit var contactDao: ContactDao
    lateinit var contactDatabase: ContactDatabase

    lateinit var numbersAdapter: NumbersAdapter
    private var mutableList= mutableListOf<NumberEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        contactDatabase = ContactDatabase.getDatabase(this)
        contactDao = contactDatabase.contactDao()
        contactRepository = ContactRepository(contactDao, this)
        val viewModelFactory = ContactViewModelFactory(contactRepository)
        contactViewModel =
            ViewModelProviders.of(this, viewModelFactory)[ContactViewModel::class.java]


        val intent: Intent = intent
        val contactEntity = intent.getParcelableExtra<ContactEntity>("name")

        tvName.text = contactEntity?.name.toString()

        setRecyclerView()

        contactViewModel.getContactNumber(contactEntity!!.name).observe(this, Observer{
         if (it.isNotEmpty()){
             mutableList.clear()
             mutableList.addAll(it[0]?.numberEntity as MutableList<NumberEntity>)
             numbersAdapter.notifyDataSetChanged()
         }else{
             mutableList.clear()
             numbersAdapter.notifyDataSetChanged()
         }
        })

        if (tvName != null) {
            val r = Random()
            val red = r.nextInt(255 - 0 + 1) + 0
            val green = r.nextInt(255 - 0 + 1) + 0
            val blue = r.nextInt(255 - 0 + 1) + 0

            val draw = GradientDrawable()
            draw.shape = GradientDrawable.OVAL
            draw.setColor(Color.rgb(red, green, blue))

            ButtonName.text = tvName.text[0].toString().uppercase()
            ButtonName.background = draw
        }

        delete.setOnClickListener {
            contactViewModel.deleteContact(tvName.text.toString())

            contactViewModel.deleteNumber(tvName.text.toString())

            Toast.makeText(this, "Contact Deleted", Toast.LENGTH_SHORT).show()

            onBackPressed()
        }

        btnEdit.setOnClickListener {
            val intent = Intent(this, EditContact::class.java)
            intent.putExtra("changeName", tvName.text)
            startActivity(intent)
            finish()
        }

        back.setOnClickListener {
            onBackPressed()
        }
    }

    fun setRecyclerView() {
        numbersAdapter = NumbersAdapter(mutableList,this)
        numberRecyclerView.adapter = numbersAdapter
        numberRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun ClickForCall(numberEntity: NumberEntity) {
        val uri = "tel:" + numberEntity.number1.trim()
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(uri)
        startActivity(intent)

    }

    override fun sendMessage(numberEntity: NumberEntity) {
        val message = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + numberEntity.number1))
        startActivity(message)
    }
}