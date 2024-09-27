package com.petfoodreminder.ui

import androidx.compose.runtime.Composable
import com.petfoodreminder.data.model.FoodBag
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FoodBagItem(
    foodBag: FoodBag,
    onDelete: (FoodBag) -> Unit,
    onSelect: (FoodBag) -> Unit,
    onAddPetClick: () -> Unit
) {
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
                Text(text = "${foodBag.name}", style = MaterialTheme.typography.titleLarge)
                Text(text = "${foodBag.weight} kg", style = MaterialTheme.typography.bodyLarge)
            }



            Spacer(modifier = Modifier.height(8.dp))

            // Botón para agregar mascota
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onDelete(foodBag) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.onError)
                }
                Button(onClick = onAddPetClick) {
                    Text("Agregar Mascota")
                }
            }
        }
    }
}
