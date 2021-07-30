package com.example.notes_app

import androidx.lifecycle.*
import com.example.notes_app.repository.UserRepository
import com.example.notes_app.room_component.UserNote
import com.example.notes_app.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateUserViewModel(private val userRepository: UserRepository,
                          private val freshUserNote: UserNote) : ViewModel(){

   private var _updatedUser: MutableLiveData<UserNote> =MutableLiveData<UserNote>()
    val updateUser:LiveData<UserNote>
    get() = _updatedUser

    init {
       _updatedUser.value=freshUserNote
  }

    fun updateUser(userNote: UserNote){
        viewModelScope.launch(Dispatchers.IO){
            userRepository.updateUser(userNote)
        }
    }

}

class UpdatedUserViewModelFactory(private val userRepository: UserRepository,
private val freshNote:UserNote) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateUserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpdateUserViewModel(userRepository,freshNote) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}