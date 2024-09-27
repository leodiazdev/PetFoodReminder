package com.petfoodreminder.data.dao

import androidx.room.*
import com.petfoodreminder.data.model.FoodBag
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodBagDao {
    @Query("SELECT * FROM food_bags")
    fun getAllFoodBags(): Flow<List<FoodBag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodBag(foodBag: FoodBag)

    @Delete
    suspend fun deleteFoodBag(foodBag: FoodBag)
}
