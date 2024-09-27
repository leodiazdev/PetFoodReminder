package com.petfoodreminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_bags")
data class FoodBag(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val weight: Float,  // Peso en kg
    val totalWeight: Float  // Peso total (para el progreso)
)
