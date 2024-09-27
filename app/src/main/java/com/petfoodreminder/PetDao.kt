package com.petfoodreminder

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {

    @Insert
    suspend fun insertPet(pet: Pet)

    @Query("SELECT * FROM pet WHERE foodBagId = :foodBagId")
    fun getPetsForFoodBag(foodBagId: Int): Flow<List<Pet>>

    @Query("DELETE FROM pet WHERE foodBagId = :foodBagId")
    suspend fun deletePetsByFoodBagId(foodBagId: Int)
}
