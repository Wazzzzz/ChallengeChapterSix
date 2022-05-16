package com.example.challengechapterfive.room

import androidx.room.*

@Dao
interface UserAccountDao {
    @Update
    fun updateAccount(user: UserAccount): Int

    @Query("SELECT * FROM UserAccount WHERE email = :email AND password = :password")
    fun login(email: String, password: String):UserAccount?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserAccount(user: UserAccount):Long
}