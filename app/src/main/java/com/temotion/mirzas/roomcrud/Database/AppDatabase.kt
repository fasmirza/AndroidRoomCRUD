package com.temotion.mirzas.roomcrud.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.temotion.mirzas.roomcrud.Models.StudentDao
import com.temotion.mirzas.roomcrud.Models.Students

@Database(entities = [Students ::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao() : StudentDao


    companion object{

        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context : Context) :AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}