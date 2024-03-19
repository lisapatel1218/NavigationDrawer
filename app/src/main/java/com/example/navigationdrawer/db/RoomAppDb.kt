package com.example.navigationdrawer.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [UserEntity::class], version = 2)
abstract class RoomAppDb : RoomDatabase() {

    abstract fun userDao(): UserDao?

    companion object {
        private var INSTANCE: RoomAppDb? = null

        fun getAppDatabase(context: Context): RoomAppDb? {
            if (INSTANCE == null) {
                synchronized(RoomAppDb::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RoomAppDb::class.java,
                        "RegisterDB"
                    ).build()
                }
            }
            return INSTANCE
        }

    }
}