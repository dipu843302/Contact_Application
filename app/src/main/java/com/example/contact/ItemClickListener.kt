package com.example.contact

import com.example.contact.room.NumberEntity

interface ItemClickListener {
    fun clickListener(numberEntity: NumberEntity, position:Int)

}