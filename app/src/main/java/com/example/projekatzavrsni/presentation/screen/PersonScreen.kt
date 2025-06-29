package com.example.projekatzavrsni.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projekatzavrsni.presentation.navigation.NavRoutes
import com.example.projekatzavrsni.presentation.viewmodel.PersonsViewModel
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonsScreen(
    viewModel: PersonsViewModel = viewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.initRepository(context)
        viewModel.fetchPersons()

    }
    val filteredList by viewModel.filtered.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val cantonQuery by viewModel.cantonQuery.collectAsState()
    val sortOption by viewModel.sortOption.collectAsState()

    val sortOptions = listOf("Ukupno", "Sa prebivalištem", "Entitet", "Institucija")
    var sortExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(start = 20.dp, top = 55.dp, end = 20.dp, bottom = 20.dp)
    ) {

        ExposedDropdownMenuBox(
            expanded = sortExpanded,
            onExpandedChange = { sortExpanded = !sortExpanded }
        ) {
            TextField(
                value = sortOption,
                onValueChange = {},
                readOnly = true,
                label = { Text("Sortiraj po") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = sortExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = sortExpanded,
                onDismissRequest = { sortExpanded = false }
            ) {
                sortOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            viewModel.updateSortOption(option)
                            sortExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = cantonQuery,
            onValueChange = { viewModel.updateCantonQuery(it) },
            label = { Text("Pretraži po kantonu") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        TextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            label = { Text("Pretraži po općini") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )


        Text(
            text = "Prikaz podataka iz posljednjeg dostupnog ažuriranja: 03.06.2025.",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navController.navigate(NavRoutes.FAVORITES)
            }) {
                Icon(Icons.Default.Favorite, contentDescription = "Favoriti")
            }
            Text("Favoriti")
        }
        LazyColumn {
            itemsIndexed(filteredList) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("${NavRoutes.DETAILPERSONS}/${item.institution}/${item.dateUpdate}")
                        }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("${item.institution}")
                    }
                }
            }
        }
    }
}
