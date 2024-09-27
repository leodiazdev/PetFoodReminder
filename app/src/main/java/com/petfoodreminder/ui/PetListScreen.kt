package com.petfoodreminder.ui

import androidx.compose.runtime.Composable
import com.petfoodreminder.data.model.Pet
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PetListScreen(
    pets: List<Pet>,
    onDeletePet: (Pet) -> Unit,
    onAddPetClick: () -> Unit
) {
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
                        Button(
                            onClick = { onDeletePet(pet) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Eliminar", color = MaterialTheme.colorScheme.onError)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onAddPetClick) {
            Text("Agregar Mascota")
        }
    }
}
