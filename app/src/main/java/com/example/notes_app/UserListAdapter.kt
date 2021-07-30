package com.example.notes_app

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app.databinding.ListItemBinding
import com.example.notes_app.room_component.UserNote

class UserListAdapter(private val itemClickListener: onItemClickListener) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    private var userNoteList= listOf<UserNote>()
    class UserViewHolder(private val binding:ListItemBinding) : RecyclerView.ViewHolder(binding.root){
          fun bindUserNote(userNote: UserNote){
              binding.userNote=userNote
              binding.executePendingBindings()
          }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
           return UserViewHolder( ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
      val currentUserNote=userNoteList[position]
        holder.bindUserNote(currentUserNote)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(currentUserNote,position)
        }
    }

    override fun getItemCount()=userNoteList.size

    fun updateUserNoteList(newUserList:List<UserNote>){
        userNoteList=newUserList
        notifyDataSetChanged()
    }
   interface onItemClickListener{
          fun onClick(note: UserNote,position: Int)
   }
}