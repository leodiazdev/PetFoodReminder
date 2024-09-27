package com.petfoodreminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FoodBag::class, Pet::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodBagDao(): FoodBagDao
    abstract fun petDao(): PetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "food_bag_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}