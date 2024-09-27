package com.petfoodreminder

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.petfoodreminder.ui.theme.PetFoodReminderTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = AppDatabase.getDatabase(this)

        setContent {
            var showAddPetScreen by remember { mutableStateOf(false) }
            var selectedFoodBag by remember { mutableStateOf<FoodBag?>(null) }

            PetFoodReminderApp(
                foodBagsFlow = database.foodBagDao().getAllFoodBags(),
                onDeleteFoodBag = { foodBag ->
                    deleteFoodBag(foodBag)
                },
                onSelectFoodBag = { foodBag ->
                    // Selección de bolsa
                    selectedFoodBag = foodBag
                },
                onAddPet = { foodBag ->
                    // Navegar a la pantalla de agregar mascotas
                    selectedFoodBag = foodBag
                    showAddPetScreen = true
                },
                showAddPetScreen = showAddPetScreen,
                selectedFoodBag = selectedFoodBag
            )
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetFoodReminderApp(
    foodBagsFlow: Flow<List<FoodBag>>,
    onDeleteFoodBag: (FoodBag) -> Unit,
    onSelectFoodBag: (FoodBag) -> Unit,
    onAddPet: (FoodBag) -> Unit,  // Nuevo callback para manejar el click en "Agregar Mascota"
    showAddPetScreen: Boolean,
    selectedFoodBag: FoodBag?
) {
    if (showAddPetScreen && selectedFoodBag != null) {
        // Mostrar la pantalla de agregar mascotas
        AddPetScreen(
            foodBag = selectedFoodBag,
            onSavePet = { name, dailyConsumption ->
                // Lógica para guardar la mascota
                onAddPet(selectedFoodBag)
            }
        )
    } else {
        // Mostrar la lista de bolsas de comida
        FoodBagListScreen(
            foodBagsFlow = foodBagsFlow,
            onDelete = onDeleteFoodBag,
            onSelect = onSelectFoodBag,
            onAddPetClick = onAddPet  // Manejar el click en "Agregar Mascota"
        )
    }
}

fun navigateToAddPetScreen(
    selectedFoodBag: FoodBag,
    onSelectFoodBag: (Int) -> Unit,
    onShowAddPetScreen: (Boolean) -> Unit
) {
    // Cambia el estado para seleccionar la bolsa de comida y mostrar la pantalla de agregar mascota
    onSelectFoodBag(selectedFoodBag.id)
    onShowAddPetScreen(true)
}


@Composable
fun PetListScreen(pets: List<Pet>, onAddPetClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (pets.isEmpty()) {
            Text("No hay mascotas asociadas a esta bolsa")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(pets.size) { index ->
                    val pet = pets[index]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(text = "Mascota: ${pet.name}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Consumo diario: ${pet.dailyConsumption} gramos")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddPetClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Agregar Mascota")
        }
    }
}

