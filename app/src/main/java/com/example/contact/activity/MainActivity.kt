package com.example.contact.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.contact.R
import com.example.contact.fragment.ContactsFragment
import kotlinx.android.synthetic.main.activity_main.*

 class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val contactsFragment=ContactsFragment()

        setCurrentFragment(contactsFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.contacts ->setCurrentFragment(contactsFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.
        beginTransaction().apply {
        replace(R.id.flFragment,fragment)
        commit()
    }
}