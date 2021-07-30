package com.example.notes_app.room_component

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserNote::class],version = 1,exportSchema=false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao   // Room uses the DAO to issue queries to its database for each table one dao use
    // make a singleton object of roomDatabase

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? =null
        fun getUserDatabase(context: Context): UserDatabase{
             return INSTANCE ?: synchronized(this){
                  val tempInstance= Room.databaseBuilder(context.applicationContext,UserDatabase::class.java,"user_database")
                     .build()
                 INSTANCE=tempInstance
                 tempInstance
            }
        }
    }

}