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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.contact.R
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.mvvm.ContactViewModelFactory
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactDatabase
import kotlinx.android.synthetic.main.activity_contact_details.*
import kotlinx.android.synthetic.main.number_layout.*
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


        val intent: Intent = intent
        val number=intent.getStringExtra("number")
        tvNumber.text = number.toString()


        val number2=intent.getStringExtra("number2")
        if (number2 != null) {
            if ( number2.isNotEmpty()) {
                linearlayout2.visibility=View.VISIBLE
                tvNumber2.text = number2.toString()
            }
        }
        val number3=intent.getStringExtra("number3")
        if (number3!=null) {
            if (number3.isNotEmpty()) {
                linearlayout3.visibility = View.VISIBLE
                tvNum3.text = number3.toString()
            }
        }
        val number4=intent.getStringExtra("number4")
        if (number4!=null) {
            if (number4.isNotEmpty()) {
                linearlayout4.visibility = View.VISIBLE
                tvNumber4.text = number4.toString()
            }
        }
        // call by phone icon
        callPhone2.setOnClickListener{
            CallButton()
        }
        callPhone3.setOnClickListener{
            CallButton()
        }
        callPhone4.setOnClickListener{
            CallButton()
        }

        // call by number
        tvNumber2.setOnClickListener{
            CallButton()
        }
        tvNum3.setOnClickListener{
            CallButton()
        }
        tvNumber4.setOnClickListener{
            CallButton()
        }

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

        call.setOnClickListener{
            CallButton()
        }
        call2.setOnClickListener{
            CallButton()
        }
        tvNumber.setOnClickListener{
            CallButton()
        }
        send_message.setOnClickListener{
            sendSMS()
        }
       // for (i in 1..3){
            //NumberLayout()
      //  }

    }

    private fun CallButton() {
        val PhoneNumber=tvNumber.text.toString()
        if (PhoneNumber.trim().isNotEmpty()){
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),1
                )
            }else{
                val dial= "tel:$PhoneNumber"
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1){
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                CallButton()
            }else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendSMS() {
        val defaultSmsPackageName =
            Telephony.Sms.getDefaultSmsPackage(this)
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT, "text")
        if (defaultSmsPackageName != null)

        {
            sendIntent.setPackage(defaultSmsPackageName)
        }
        startActivity(sendIntent)
    }

    @SuppressLint("InflateParams")
    private fun NumberLayout(){
      //  val view:View=layoutInflater.inflate(R.layout.number_layout,null)
       // number_linearlayout.addView(view)
        val vi =
            applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = vi.inflate(R.layout.number_layout, null)
        number_linearlayout.addView(v)

    }
}