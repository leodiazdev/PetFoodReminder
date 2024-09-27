package com.petfoodreminder.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.petfoodreminder.data.model.FoodBag
import kotlinx.coroutines.flow.Flow


@Composable
fun FoodBagListScreen(
    foodBagsFlow: Flow<List<FoodBag>>,
    onDelete: (FoodBag) -> Unit,
    onSelect: (FoodBag) -> Unit,
    onAddPetClick: (FoodBag) -> Unit
) {
    val foodBags by foodBagsFlow.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(foodBags.size) { index ->
            val foodBag = foodBags[index]
            FoodBagItem(foodBag = foodBag, onDelete = onDelete, onSelect = onSelect, onAddPetClick = { onAddPetClick(foodBag) })
        }
    }
}
