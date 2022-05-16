package com.example.challengechapterfive.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserAccount::class], version = 1)
abstract class NetplixDatabase (): RoomDatabase(){
    abstract fun userAccountDao(): UserAccountDao

    companion object{
        private var INSTANCE: NetplixDatabase? = null

        fun getInstance(context: Context): NetplixDatabase? {
            if(INSTANCE == null) {
                synchronized(NetplixDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NetplixDatabase::class.java, "Netplix.db").build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}