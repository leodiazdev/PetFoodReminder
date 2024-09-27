package com.petfoodreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.petfoodreminder.data.AppDatabase
import com.petfoodreminder.data.model.FoodBag
import com.petfoodreminder.data.model.Pet
import com.petfoodreminder.ui.AddFoodBagDialog
import com.petfoodreminder.ui.PetFoodReminderApp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar la base de datos
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "pet_food_reminder_db"
        ).build()

        // Renderizar el contenido de la app
        setContent {
            var showAddPetScreen by remember { mutableStateOf(false) }
            var selectedFoodBag by remember { mutableStateOf<FoodBag?>(null) }
            var showAddFoodBagDialog by remember { mutableStateOf(false) }
            var showFoodBagDetails by remember { mutableStateOf(false) }

            PetFoodReminderApp(
                foodBagsFlow = database.foodBagDao().getAllFoodBags(),
                petsFlow = { foodBagId -> database.petDao().getPetsForFoodBag(foodBagId) },
                onDeleteFoodBag = { foodBag ->
                    deleteFoodBag(foodBag)
                },
                onSelectFoodBag = { foodBag ->
                    selectedFoodBag = foodBag
                    showFoodBagDetails = true
                },
                onSavePet = { foodBag, petName, dailyConsumption ->
                    savePet(petName, dailyConsumption, foodBag.id)
                    showAddPetScreen = false
                },
                showAddPetScreen = showAddPetScreen,
                selectedFoodBag = selectedFoodBag,
                onAddFoodBagClick = {
                    showAddFoodBagDialog = true
                },
                onCancelAddPet = {
                    showAddPetScreen = false  // Restablecer el estado para volver a la lista
                },
                showFoodBagDetails = showFoodBagDetails,
                onBackFromDetails = {
                    showFoodBagDetails = false  // Volver a la lista de bolsas
                },
                onDeletePet = { pet ->
                    deletePet(pet)  // Eliminar la mascota de la base de datos
                }
            )
            // Mostrar diálogo para agregar bolsa si el estado es verdadero
            if (showAddFoodBagDialog) {
                AddFoodBagDialog(
                    onDismiss = { showAddFoodBagDialog = false },
                    onAdd = { name, weight ->
                        saveFoodBag(name, weight)
                        showAddFoodBagDialog = false  // Cerrar el diálogo después de agregar
                    }
                )
            }
        }
    }

    private fun deleteFoodBag(foodBag: FoodBag) {
        lifecycleScope.launch {
            database.foodBagDao().deleteFoodBag(foodBag)
        }
    }

    private fun savePet(petName: String, dailyConsumption: Float, foodBagId: Int) {
        lifecycleScope.launch {
            database.petDao().insertPet(
                Pet(name = petName, dailyConsumption = dailyConsumption, foodBagId = foodBagId)
            )
        }
    }

    private fun saveFoodBag(name: String, weight: Float) {
        lifecycleScope.launch {
            database.foodBagDao().insertFoodBag(
                FoodBag(name = name, weight = weight, totalWeight = weight)
            )
        }
    }

    private fun deletePet(pet: Pet) {
        lifecycleScope.launch {
            database.petDao().deletePet(pet)
        }
    }

}
