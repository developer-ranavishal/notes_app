package com.example.notes_app

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app.databinding.FragmentListUserBinding
import com.example.notes_app.repository.UserRepository
import com.example.notes_app.room_component.UserDatabase
import com.example.notes_app.room_component.UserNote
import com.example.notes_app.viewmodel.UserViewModel
import com.example.notes_app.viewmodel.UserViewModelFactory

class ListUserFragment : Fragment(),UserListAdapter.onItemClickListener {
private lateinit var binding: FragmentListUserBinding
private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding=FragmentListUserBinding.inflate(layoutInflater)
          val userRepository=UserRepository(UserDatabase.getUserDatabase(requireContext()).userDao())
          val userViewModelFactory=UserViewModelFactory(userRepository)
        userViewModel=ViewModelProvider(this,userViewModelFactory).get(UserViewModel::class.java)
        binding.lifecycleOwner=viewLifecycleOwner
        binding.userViewModel=userViewModel
        binding.recyclerview.adapter=UserListAdapter(this)
        binding.floatingActionButton.setOnClickListener {
              findNavController().navigate(R.id.action_listUserFragment_to_createUserFragment)
        }
        // this indicate this fragment will use option menu if argument passes is true
         setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
     inflater.inflate(R.menu.option_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.menu_deleteItem)
            deleteAllUserData()
        return true
    }
    fun deleteAllUserData(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete All the Users")
        builder.setMessage("Do you want to delete All the Users?")
        builder.setPositiveButton("Yes"){ _ , _ ->
            userViewModel.deleteAllUser()
               Toast.makeText(requireContext(), "successfully removed  All the Users:", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){ _ ,_ -> }
        builder.create().show()
    }

    // cliclistener on itemView here is cardview
    override fun onClick(note: UserNote, position: Int) {
        updateOrDeleteUser(note,position)
    }

    private fun updateOrDeleteUser(note: UserNote, position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose the Operation")
        builder.setMessage("what you want delete or update?")
        builder.setPositiveButton("Delete"){ _ , _ ->
            userViewModel.deleteUser(note)
            Toast.makeText(requireContext(), "deletion successful at $position position", Toast.LENGTH_SHORT).show()
        }
              builder.setNeutralButton("nothing"){_,_ ->
                  Toast.makeText(context, "nothing happening!", Toast.LENGTH_SHORT).show()
              }
        builder.setNegativeButton("Update"){ _ ,_ ->
                    navigateToUpdate(note)
        }
        builder.create().show()
    }
    fun navigateToUpdate(note: UserNote) {
        val action=ListUserFragmentDirections.actionListUserFragmentToUpdateUserFragment(note)
        findNavController().navigate(action)
    }
}

@BindingAdapter("bindList")
fun bindRecyclerview(recyclerView: RecyclerView,userList: List<UserNote>?){
    val adapter=recyclerView.adapter as UserListAdapter
    if (userList != null)
        adapter.updateUserNoteList(userList)
}