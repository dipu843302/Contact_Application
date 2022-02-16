package com.example.contact.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.ContactAdapter
import com.example.contact.ItemClickListener

import com.example.contact.R
import com.example.contact.activity.AddNewContact
import com.example.contact.activity.ContactDetails
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.mvvm.ContactViewModelFactory
import com.example.contact.room.Contact
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactDatabase

import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.util.jar.Attributes


class ContactsFragment : Fragment(),ItemClickListener {
    lateinit var contactViewModel: ContactViewModel
    lateinit var contactRepository: ContactRepository
    lateinit var contactDao: ContactDao
    lateinit var contactDatabase: ContactDatabase
    lateinit var contactAdapter: ContactAdapter

    private var contactList = mutableListOf<Contact>()
    private var list = mutableListOf<Contact>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addContact.setOnClickListener {
            startActivity(Intent(this.context, AddNewContact::class.java))
        }
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_CONTACTS
                )
            } != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity, Array(1) {
                Manifest.permission.READ_CONTACTS
            }, 111)
        } else {
            //readContacts()
        }
        contactDatabase = ContactDatabase.getDatabase(requireContext())
        contactDao = contactDatabase.contactDao()
        contactRepository = ContactRepository(contactDao, requireContext())
        val viewModelFactory = ContactViewModelFactory(contactRepository)

        contactViewModel =
            ViewModelProviders.of(this, viewModelFactory)[ContactViewModel::class.java]


        contactViewModel.fetchAllContact().observe(viewLifecycleOwner, Observer {
            contactList.clear()
            contactList.addAll(it)
            setRecyclerView()
        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    contactViewModel.searchContact(newText.toString()).observe(viewLifecycleOwner) {
                        contactList.clear()
                        contactList.addAll(it)
                        contactAdapter.notifyDataSetChanged()
                    }
                }
                return false
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }


    private fun setRecyclerView() {
        contactAdapter = ContactAdapter(contactList, this)
        recyclerView.adapter = contactAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun clickListener(contact: Contact, position: Int) {
        val intent=Intent(this.context,ContactDetails::class.java)

        intent.putExtra("name",contact.name)
      //  intent.putExtra("number",contact.number)
        startActivity(intent)
    }
}

