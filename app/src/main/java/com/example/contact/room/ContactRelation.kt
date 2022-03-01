package com.example.contact.room

import androidx.room.Embedded
import androidx.room.Relation

data class ContactRelation
    (
    @Embedded var contact: Contact, @Relation(
    parentColumn = "name",
    entityColumn = "name"
)
var number: List<NumberEntity>
)
