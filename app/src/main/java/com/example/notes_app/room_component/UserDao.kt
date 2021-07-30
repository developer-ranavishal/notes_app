package com.example.notes_app.room_component

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

@Insert(onConflict =OnConflictStrategy.IGNORE)
 suspend fun addUserNote(userNote: UserNote)

 @Query("SELECT * FROM user_note ORDER By UserName ASC")
     fun getAllUserNote(): LiveData<List<UserNote>>

     @Query("DELETE FROM USER_NOTE")
   suspend  fun deleteAllUser()

   @Delete
   suspend fun deleteUser(userNote: UserNote)

   @Update
   suspend fun updateUser(userNote: UserNote)


}