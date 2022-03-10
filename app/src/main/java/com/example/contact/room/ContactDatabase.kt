package com.example.contact.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ContactEntity::class,NumberEntity::class],version = 2)
abstract class ContactDatabase :RoomDatabase() {

    abstract fun contactDao():ContactDao

    companion object{
        @Volatile
        private var INSTANCE:ContactDatabase?=null
        fun getDatabase(context: Context):ContactDatabase{
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance=Room.databaseBuilder(context.applicationContext,ContactDatabase::class.java,"user_database"
                )
                instance.fallbackToDestructiveMigration()
                INSTANCE=instance.build()
                return INSTANCE!!
            }
        }
    }
}