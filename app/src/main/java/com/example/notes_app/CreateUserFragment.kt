package com.example.notes_app

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes_app.databinding.FragmentCreateUserBinding
import com.example.notes_app.repository.UserRepository
import com.example.notes_app.room_component.UserDao
import com.example.notes_app.room_component.UserDatabase
import com.example.notes_app.room_component.UserNote
import com.example.notes_app.viewmodel.UserViewModel
import com.example.notes_app.viewmodel.UserViewModelFactory

class CreateUserFragment : Fragment() {

  private lateinit var binding: FragmentCreateUserBinding
  private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCreateUserBinding.inflate(layoutInflater)
       val name= binding.crUsernameETxt.text
       val mobile=binding.crUserMobETxt.text
        val note=binding.crUserNoteETxt.text
        val userRepository= UserRepository(UserDatabase.getUserDatabase(requireContext()).userDao())
        val userViewModelFactory= UserViewModelFactory(userRepository)
         userViewModel=ViewModelProvider(this,userViewModelFactory).get(UserViewModel::class.java)
           binding.crSaveUserBtn.setOnClickListener {
             checkUserData(name,mobile,note)
           }
        return binding.root
    }
    private fun checkUserData(name: Editable, mobile: Editable, note: Editable) {

        if (name.isEmpty() || mobile.isEmpty() || note.isEmpty())
            Toast.makeText(context, "Please fill all the field !", Toast.LENGTH_SHORT).show()
            else {
                  val userNote = UserNote(
                      name =name.toString(),
                      mobile =mobile.toString().toLong(),
                      note = note.toString()
                  )
                  userViewModel.addUserNote(userNote)
                  Toast.makeText(context, "user note successfully added :", Toast.LENGTH_SHORT)
                      .show()
                  findNavController().navigate(R.id.action_createUserFragment_to_listUserFragment)
              }
    }

}