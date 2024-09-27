package com.petfoodreminder.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.petfoodreminder.data.model.FoodBag
import com.petfoodreminder.data.model.Pet
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetFoodReminderApp(
    foodBagsFlow: Flow<List<FoodBag>>,
    petsFlow: (Int) -> Flow<List<Pet>>,
    onDeleteFoodBag: (FoodBag) -> Unit,
    onSelectFoodBag: (FoodBag) -> Unit,
    onSavePet: (FoodBag, String, Float) -> Unit,
    showAddPetScreen: Boolean,
    selectedFoodBag: FoodBag?,
    onAddFoodBagClick: () -> Unit,  // Nueva función para abrir el cuadro de diálogo
    onCancelAddPet: () -> Unit,
    showFoodBagDetails: Boolean,  // Nuevo estado para mostrar los detalles de la bolsa
    onBackFromDetails: () -> Unit,  // Acción para volver a la lista desde los detalles
    onDeletePet: (Pet) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pet Food Reminder",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                actions = {
                    // Botón rectangular con el texto "Agregar Bolsa" y bordes redondeados
                    Button(
                        onClick = onAddFoodBagClick,
                        shape = MaterialTheme.shapes.small,  // Bordes redondeados
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,  // Color de fondo del botón
                            contentColor = MaterialTheme.colorScheme.onSecondary  // Color del texto del botón
                        ),
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(text = "Agregar Bolsa")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,  // Color de fondo
                    titleContentColor = MaterialTheme.colorScheme.onPrimary  // Color del título
                ),

            )
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when {
                    showAddPetScreen && selectedFoodBag != null -> {
                        AddPetScreen(
                            foodBagId = selectedFoodBag.id,
                            onSave = { petName, dailyConsumption ->
                                onSavePet(selectedFoodBag, petName, dailyConsumption)
                            },
                            onCancel = onCancelAddPet
                        )
                    }
                    showFoodBagDetails && selectedFoodBag != null -> {
                        FoodBagDetailScreen(
                            foodBag = selectedFoodBag,
                            petsFlow = petsFlow(selectedFoodBag.id),
                            onBack = onBackFromDetails,
                            onDeletePet = onDeletePet
                        )
                    }
                    else -> {
                        FoodBagListScreen(
                            foodBagsFlow = foodBagsFlow,
                            onDelete = onDeleteFoodBag,
                            onSelect = onSelectFoodBag,  // Ver los detalles de la bolsa
                            onAddPetClick = { foodBag -> onSelectFoodBag(foodBag) }
                        )
                    }
                }
            }
        }
    )
}