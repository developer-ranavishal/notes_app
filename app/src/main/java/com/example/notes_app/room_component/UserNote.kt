package com.example.notes_app.room_component

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_note")
data class UserNote(
    @ColumnInfo(name = "UserName")    val name: String,
    @PrimaryKey
    @ColumnInfo(name = "User_Mobile") val mobile: Long,
    @ColumnInfo(name = "UserNote")val note: String
): Parcelable
