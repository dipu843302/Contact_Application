package com.example.contact.Interface

import com.example.contact.room.NumberEntity

interface CallClickListener {
    fun ClickForCall(numberEntity: NumberEntity)
    fun sendMessage(numberEntity: NumberEntity)
}