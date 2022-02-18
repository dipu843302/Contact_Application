package com.example.contact.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.adapter.ContactAdapter
import com.example.contact.ItemClickListener

import com.example.contact.R
import com.example.contact.activity.AddNewContact
import com.example.contact.activity.ContactDetails
import com.example.contact.adapter.SearchAdapter
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.mvvm.ContactViewModelFactory
import com.example.contact.room.Contact
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactDatabase

import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.coroutines.launch


class ContactsFragment : Fragment(), ItemClickListener {
    lateinit var contactViewModel: ContactViewModel
    lateinit var contactRepository: ContactRepository
    lateinit var contactDao: ContactDao
    lateinit var contactDatabase: ContactDatabase
    lateinit var contactAdapter: ContactAdapter
    lateinit var searchAdapter: SearchAdapter

    private var contactList = mutableListOf<Contact>()

    companion object {
        val arr = arrayOf("android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ActivityCompat.requestPermissions(this.requireActivity(),arr,111)

        addContact.setOnClickListener {
            startActivity(Intent(this.context, AddNewContact::class.java))
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
                if (newText != null) {
                    if (newText.isEmpty()){
                        lifecycleScope.launch {
                            contactViewModel.searchContact(newText.toString()).observe(requireActivity()) {
                                contactList.clear()
                                contactList.addAll(it)
                                setRecyclerView()
                            }
                        }
                    }else{
                        lifecycleScope.launch {
                            contactViewModel.searchContact(newText.toString()).observe(requireActivity()) {
                                contactList.clear()
                                contactList.addAll(it)
                                setRecyclerViewForSearch()

                            }
                        }
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

   fun setRecyclerViewForSearch(){
        searchAdapter= SearchAdapter(contactList,this)
       recyclerView.adapter=searchAdapter
       recyclerView.layoutManager=LinearLayoutManager(requireContext())
    }

    override fun clickListener(contact: Contact, position: Int) {
        val intent = Intent(this.context, ContactDetails::class.java)

        intent.putExtra("name", contact.name)
         intent.putExtra("number",contact.number)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==111){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this.context, "Permission granted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this.context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

