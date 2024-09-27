package com.petfoodreminder.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.petfoodreminder.data.dao.FoodBagDao
import com.petfoodreminder.data.dao.PetDao
import com.petfoodreminder.data.model.FoodBag
import com.petfoodreminder.data.model.Pet

@Database(entities = [FoodBag::class, Pet::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodBagDao(): FoodBagDao
    abstract fun petDao(): PetDao
}
