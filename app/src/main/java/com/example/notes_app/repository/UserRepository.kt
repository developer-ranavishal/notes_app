package com.example.notes_app.repository

import androidx.lifecycle.LiveData
import com.example.notes_app.room_component.UserDao
import com.example.notes_app.room_component.UserNote


class UserRepository(private val userDao: UserDao) {
    val allUserNote: LiveData<List<UserNote>> =userDao.getAllUserNote()

    suspend fun addUserNote(userNote: UserNote){
        userDao.addUserNote(userNote)
    }

    suspend fun deleteAllUser(){
        userDao.deleteAllUser()
    }
    suspend fun deleteUser(userNote: UserNote){
        userDao.deleteUser(userNote)
    }

    suspend fun updateUser(userNote: UserNote){
        userDao.updateUser(userNote)
    }
}