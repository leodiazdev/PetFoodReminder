package com.petfoodreminder.data.dao

import androidx.room.*
import com.petfoodreminder.data.model.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Query("SELECT * FROM pets WHERE foodBagId = :foodBagId")
    fun getPetsForFoodBag(foodBagId: Int): Flow<List<Pet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: Pet)

    @Delete
    suspend fun deletePet(pet: Pet)
}
