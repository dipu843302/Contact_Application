package com.example.contact.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class ContactRelation
    (
    @Embedded var contactEntity: ContactEntity,
    @Relation(
        parentColumn = "Contact_Id",
        entityColumn = "Contact_Id"
    )
    var numberEntity: List<NumberEntity>
)
