package com.example.recycler_view_practice.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, exportSchema = false, entities = [Food::class])
abstract class MyDatabase : RoomDatabase() {
    abstract val foodDao: FoodDao

    companion object {
        @Volatile
        private var database: MyDatabase? = null
        fun getDatabase(context: Context): MyDatabase {
            synchronized(this) {
                if (database == null) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "myDatabase.db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
                return database!!
            }
        }
    }
}