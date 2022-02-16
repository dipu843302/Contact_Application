package com.example.contact

import com.example.contact.room.Contact

interface ItemClickListener {
    fun clickListener(contact: Contact, position:Int)

}