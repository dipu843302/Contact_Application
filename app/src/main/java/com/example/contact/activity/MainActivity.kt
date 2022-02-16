package com.example.contact.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.contact.R
import com.example.contact.fragment.ContactsFragment
import com.example.contact.fragment.FavoritesFragment
import com.example.contact.fragment.RecentsFragment
import kotlinx.android.synthetic.main.activity_main.*

 class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val favoritesFragment=FavoritesFragment()
        val recentsFragment=RecentsFragment()
        val contactsFragment=ContactsFragment()

        setCurrentFragment(favoritesFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.favorites ->setCurrentFragment(favoritesFragment)
                R.id.recents ->setCurrentFragment(recentsFragment)
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