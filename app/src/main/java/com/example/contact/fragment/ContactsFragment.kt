package com.example.contact.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.ItemClickListener
import com.example.contact.R
import com.example.contact.activity.AddNewContact
import com.example.contact.activity.ContactDetails
import com.example.contact.adapter.ContactAdapter
import com.example.contact.adapter.SearchAdapter
import com.example.contact.mvvm.ContactRepository
import com.example.contact.mvvm.ContactViewModel
import com.example.contact.mvvm.ContactViewModelFactory
import com.example.contact.room.ContactDao
import com.example.contact.room.ContactDatabase
import com.example.contact.room.ContactRelation
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.coroutines.launch
import com.example.contact.room.NumberEntity

class ContactsFragment : Fragment(), ItemClickListener {
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var contactRepository: ContactRepository
    private lateinit var contactDao: ContactDao
    private lateinit var contactDatabase: ContactDatabase
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var searchAdapter: SearchAdapter

    private var contactList = mutableListOf<ContactRelation>()
    private var searchList = mutableListOf<NumberEntity>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addContact.setOnClickListener {
            startActivity(Intent(this.context, AddNewContact::class.java))
        }

        contactDatabase = ContactDatabase.getDatabase(requireContext())
        contactDao = contactDatabase.contactDao()
        contactRepository = ContactRepository(contactDao, requireContext())
        val viewModelFactory = ContactViewModelFactory(contactRepository)

        contactViewModel =
            ViewModelProviders.of(this, viewModelFactory)[ContactViewModel::class.java]

        contactViewModel.storeData()
        contactViewModel.fetchAllContact().observe(viewLifecycleOwner, Observer {
            contactList.clear()
            contactList.addAll(it)
            setRecyclerView()
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isEmpty()) {
                        lifecycleScope.launch {
                            contactViewModel.searchContact(newText.toString())
                                .observe(requireActivity()) {
                                    contactList.clear()
                                    contactList.addAll(it)
                                    setRecyclerView()
                                }
                        }
                    } else {
                        lifecycleScope.launch {
                            contactViewModel.getNumberFromSearch(newText.toString())
                                .observe(requireActivity()) {
                                    searchList.clear()
                                    searchList.addAll(it)
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
        RecyclerView.adapter = contactAdapter
        RecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    fun setRecyclerViewForSearch() {
        searchAdapter = SearchAdapter(searchList, this)
        RecyclerView.adapter = searchAdapter
        RecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun clickListener(numberEntity: NumberEntity, position: Int) {
        val intent = Intent(this.context, ContactDetails::class.java)
        intent.putExtra("name", numberEntity.name)
        intent.putExtra("number", numberEntity.number1)
        startActivity(intent)
    }

}