package com.petfoodreminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val dailyConsumption: Float,  // Consumo diario en gramos
    val foodBagId: Int  // Relación con la bolsa de comida
)
