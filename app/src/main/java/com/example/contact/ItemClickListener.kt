package com.example.contact

import com.example.contact.room.ContactRelation
import com.example.contact.room.NumberEntity

interface ItemClickListener {
    fun clickListener(contactRelation: ContactRelation, position:Int)

}