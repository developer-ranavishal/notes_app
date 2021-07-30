package com.example.notes_app

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes_app.databinding.FragmentUpdateUserBinding
import com.example.notes_app.repository.UserRepository
import com.example.notes_app.room_component.UserDatabase
import com.example.notes_app.room_component.UserNote


class UpdateUserFragment : Fragment() {
    private lateinit var updateUserViewModel: UpdateUserViewModel
    private lateinit var currentNote:UserNote
    private lateinit var binding:FragmentUpdateUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       currentNote=UpdateUserFragmentArgs.fromBundle(requireArguments()).userNote
        binding=FragmentUpdateUserBinding.inflate(layoutInflater)
        binding.lifecycleOwner=viewLifecycleOwner
        val userRepository= UserRepository(UserDatabase.getUserDatabase(requireContext()).userDao())
        val updatedUserViewModelFactory= UpdatedUserViewModelFactory(userRepository,currentNote)
      updateUserViewModel=ViewModelProvider(this,updatedUserViewModelFactory).get(UpdateUserViewModel::class.java)
        binding.updateViewModel=updateUserViewModel

        binding.updateButton.setOnClickListener {
            val name=binding.updNameTxt.text
            val mobile=binding.updMobileTxt.text
            val note=binding.updNoteTxt.text
            updateAndNavigate(name,mobile,note)
        }
        return binding.root
    }

    private fun updateAndNavigate(name: Editable, mobile: Editable, note: Editable) {
     if (name.isEmpty() || mobile.isEmpty() || note.isEmpty())
         Toast.makeText(context, "Oops! empty field available:", Toast.LENGTH_SHORT).show()
        else{
            val freshUserNote=UserNote(name.toString(),mobile.toString().toLong(),note.toString())
         updateUserViewModel.updateUser(freshUserNote)
         Toast.makeText(context, "wellDone! User Update successfully:", Toast.LENGTH_SHORT).show()
               findNavController().navigate(R.id.action_updateUserFragment_to_listUserFragment)
     }
    }


    }
