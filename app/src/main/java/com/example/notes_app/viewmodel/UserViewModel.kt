package com.example.notes_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.notes_app.R
import com.example.notes_app.repository.UserRepository
import com.example.notes_app.room_component.UserNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    val getAllUser: LiveData<List<UserNote>> =userRepository.allUserNote
    fun addUserNote(note: UserNote){
               viewModelScope.launch(Dispatchers.IO) {
                   userRepository.addUserNote(note)
               }
    }
   fun deleteAllUser(){
       viewModelScope.launch (Dispatchers.IO){
           userRepository.deleteAllUser()
       }
   }
    fun deleteUser(userNote: UserNote){
        viewModelScope.launch(Dispatchers.IO){
            userRepository.deleteUser(userNote)
        }
    }

}

class UserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}