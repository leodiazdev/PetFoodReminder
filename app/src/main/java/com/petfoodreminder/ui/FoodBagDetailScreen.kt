package com.petfoodreminder.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.petfoodreminder.data.model.FoodBag
import com.petfoodreminder.data.model.Pet
import kotlinx.coroutines.flow.Flow

@Composable
fun FoodBagDetailScreen(
    foodBag: FoodBag,
    petsFlow: Flow<List<Pet>>,  // Las mascotas asociadas a la bolsa
    onBack: () -> Unit, // Acción para volver a la lista de bolsas
    onDeletePet: (Pet) -> Unit
) {
    val pets by petsFlow.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Detalles de la Bolsa", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Nombre: ${foodBag.name}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Peso: ${foodBag.weight} kg", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Mascotas en esta bolsa:", style = MaterialTheme.typography.bodyMedium)

        if (pets.isEmpty()) {
            Text(text = "No hay mascotas asociadas", style = MaterialTheme.typography.bodySmall)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(pets.size) { index ->
                    val pet = pets[index]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = pet.name, style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Consumo diario: ${pet.dailyConsumption}g", style = MaterialTheme.typography.bodySmall)
                        }
                        // Botón para quitar mascota
                        Button(
                            onClick = { onDeletePet(pet) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError
                            )
                        ) {
                            Text("Quitar")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver atrás
        Button(onClick = onBack) {
            Text(text = "Volver")
        }
    }
}
