package com.example.contact.Interface

import com.example.contact.room.ContactEntity
import com.example.contact.room.ContactRelation
import com.example.contact.room.NumberEntity

interface ItemClickListener {
    fun clickListener(contactEntity: ContactEntity)

}