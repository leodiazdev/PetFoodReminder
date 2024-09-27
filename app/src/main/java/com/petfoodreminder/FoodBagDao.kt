package com.petfoodreminder

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodBagDao {

    @Insert
    suspend fun insertFoodBag(foodBag: FoodBag)

    @Query("SELECT * FROM food_bag")
    fun getAllFoodBags(): Flow<List<FoodBag>>

    @Delete
    suspend fun deleteFoodBag(foodBag: FoodBag)
}
