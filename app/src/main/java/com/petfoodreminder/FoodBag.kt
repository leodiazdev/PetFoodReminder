package com.petfoodreminder

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_bag")
data class FoodBag(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val weight: Float
)