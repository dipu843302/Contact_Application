package com.example.contact.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.contact.R
import com.example.contact.fragment.ContactsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var boolean: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    private fun hasReadContact() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

    fun requestPermission() {
        var permissionToRequest = mutableListOf<String>()
        if (!hasReadContact()) {
            permissionToRequest.add(Manifest.permission.READ_CONTACTS)
        }
        if (permissionToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionToRequest.toTypedArray(), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty()) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show()
                val contactsFragment = ContactsFragment()
                setCurrentFragment(contactsFragment)
                bottomNavigationView.setOnNavigationItemSelectedListener {

                    when (it.itemId) {
                        R.id.contacts -> setCurrentFragment(contactsFragment)
                    }
                    true
                }
                boolean = true
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show()
                goToSettings()
            }
        }
    }

    fun goToSettings() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Contact Permission")
            .setMessage("Permission has denied got to settings")
            .setNegativeButton("Cancel") { dialogInterface, i -> dialogInterface.dismiss() }
            .setPositiveButton(
                "Ok"
            ) { dialog, which ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }.show()
    }

    override fun onStart() {
        super.onStart()
        if (hasReadContact())
            boolean=true

        if (boolean) {
            val contactsFragment = ContactsFragment()
            setCurrentFragment(contactsFragment)
            bottomNavigationView.setOnNavigationItemSelectedListener {

                when (it.itemId) {
                    R.id.contacts -> setCurrentFragment(contactsFragment)
                }
                true
            }
        }
    }
}