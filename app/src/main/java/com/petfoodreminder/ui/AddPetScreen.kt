package com.petfoodreminder.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddPetScreen(
    foodBagId: Int,
    onSave: (String, Float) -> Unit,
    onCancel: () -> Unit  // Nueva función para cancelar la acción
) {
    var petName by remember { mutableStateOf("") }
    var dailyConsumption by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Agregar Mascota", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = petName,
            onValueChange = { petName = it },
            label = { Text("Nombre de la mascota") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botón Cancelar
            Button(onClick = onCancel, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )) {
                Text("Cancelar")
            }

            // Botón Guardar
            Button(onClick = {
                if (petName.isNotEmpty() && dailyConsumption.isNotEmpty()) {
                    onSave(petName, dailyConsumption.toFloatOrNull() ?: 0f)
                }
            }) {
                Text("Guardar")
            }
        }
    }
}