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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${foodBag.name} - ${foodBag.weight} kg",
                style = MaterialTheme.typography.headlineMedium
            )
            /* Button(
                onClick = { *//* TODO *//* },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Agregar Mascota")
            }*/
        }

        Spacer(modifier = Modifier.height(16.dp))


        if (pets.isEmpty()) {
            Text(text = "No hay mascotas asociadas", style = MaterialTheme.typography.bodySmall)
        } else {
            Text(text = "Mascotas en esta bolsa:", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(pets.size) { index ->
                    val pet = pets[index]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = pet.name, style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    text = "Consumo diario: ${pet.dailyConsumption}g",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            // Botón para quitar mascota
                            IconButton(
                                onClick = { onDeletePet(pet) },

                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar Bolsa",
                                    tint =  Color(0xFFFF6F61) // Color del ícono
                                )
                            }
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver atrás
        /*Button(onClick = onBack) {
            Text(text = "Volver")
        }*/
    }
}
