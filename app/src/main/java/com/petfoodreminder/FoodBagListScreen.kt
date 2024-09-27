package com.petfoodreminder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow

@Composable
fun FoodBagListScreen(foodBagsFlow: Flow<List<FoodBag>>,
                      onDelete: (FoodBag) -> Unit,
                      onSelect: (FoodBag) -> Unit,
                      onAddPetClick: (FoodBag) -> Unit) {
    val foodBags by foodBagsFlow.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(foodBags.size) { index ->
            val foodBag = foodBags[index]
            FoodBagItem(foodBag = foodBag,
                onDelete = onDelete,
                onSelect = onSelect,
                onAddPetClick = { onAddPetClick(foodBag) })
        }
    }
}


@Composable
fun FoodBagItem(foodBag: FoodBag,
                onDelete: (FoodBag) -> Unit,
                onSelect: (FoodBag) -> Unit,
                onAddPetClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onSelect(foodBag) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Bolsa: ${foodBag.name}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Peso: ${foodBag.weight} kg", style = MaterialTheme.typography.bodyLarge)
            }

            Button(
                onClick = { onDelete(foodBag) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Eliminar", color = MaterialTheme.colorScheme.onError)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Botón para agregar una mascota
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Botón para agregar una mascota alineado a la derecha
                Button(onClick = {
                    onAddPetClick()
                }) {
                    Text("Agregar Mascota")
                }
            }
        }
    }
}