package com.petfoodreminder

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "pet",
    foreignKeys = [
        ForeignKey(
            entity = FoodBag::class,
            parentColumns = ["id"],
            childColumns = ["foodBagId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Pet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val dailyConsumption: Float, // Cantidad de comida que consume la mascota por día en gramos
    val foodBagId: Int // Referencia a la bolsa de comida a la que está asociada
)
