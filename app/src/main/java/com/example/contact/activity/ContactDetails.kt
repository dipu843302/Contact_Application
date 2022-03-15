package com.example.contact.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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
import kotlinx.android.synthetic.main.number_layout.*
import java.util.*


class ContactDetails : AppCompatActivity() {

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
//        val number=intent.getStringExtra("number")
//        tvNumber.text = number.toString()


        // val name=intent.getStringExtra("name")
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
            contactViewModel.
            deleteContact(tvName.toString())
            Toast.makeText(this, "Contact Deleted", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        btnEdit.setOnClickListener {
            val intent = Intent(this, EditContact::class.java)
            intent.putExtra("changeName", tvName.text)
            //  intent.putExtra("number",number)
            startActivity(intent)
            finish()
        }

        back.setOnClickListener {
            onBackPressed()
        }

//        call.setOnClickListener{
//            CallButton()
//        }
//        call2.setOnClickListener{
//            CallButton()
//        }
//        tvNumber.setOnClickListener{
//            CallButton()
//        }
//        send_message.setOnClickListener{
//            sendSMS()
//        }
//        for (i in 1..3){
//            //NumberLayout()
//        }


    }

    fun setRecyclerView() {
        numbersAdapter = NumbersAdapter(mutableList)
        numberRecyclerView.adapter = numbersAdapter
        numberRecyclerView.layoutManager = LinearLayoutManager(this)
    }
//    private fun CallButton() {
//        val PhoneNumber=tvNumber.text.toString()
//        if (PhoneNumber.trim().isNotEmpty()){
//            if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),1
//                )
//            }else{
//                val dial= "tel:$PhoneNumber"
//                startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
//            }
//        }
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode==1){
//            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                CallButton()
//            }else{
//                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun sendSMS() {
        val defaultSmsPackageName =
            Telephony.Sms.getDefaultSmsPackage(this)
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT, "text")
        if (defaultSmsPackageName != null) {
            sendIntent.setPackage(defaultSmsPackageName)
        }
        startActivity(sendIntent)
    }

    @SuppressLint("InflateParams")
    private fun NumberLayout() {
        val view: View = layoutInflater.inflate(R.layout.number_layout, null)
        number_linearlayout.addView(view)
        val vi =
            applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = vi.inflate(R.layout.number_layout, null)
        number_linearlayout.addView(v)

    }
}