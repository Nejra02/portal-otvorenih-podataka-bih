package com.example.projekatzavrsni.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projekatzavrsni.data.api.persons.PersonInfo
import com.example.projekatzavrsni.data.api.newborns.NewbornInfo
import com.example.projekatzavrsni.presentation.viewmodel.PersonsViewModel
import com.example.projekatzavrsni.presentation.viewmodel.NewbornViewModel
import com.example.projekatzavrsni.presentation.navigation.NavRoutes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,

    personsViewModel: PersonsViewModel = viewModel(),
    newbornViewModel: NewbornViewModel = viewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var favoritePersons by remember { mutableStateOf<List<PersonInfo>>(emptyList()) }
    var favoriteNewborns by remember { mutableStateOf<List<NewbornInfo>>(emptyList()) }

    fun refreshFavorites() {
        coroutineScope.launch {
            favoritePersons = personsViewModel.getFavoritePersons()
            favoriteNewborns = newbornViewModel.getFavoriteNewborns()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                personsViewModel.initRepository(context)
                newbornViewModel.initRepository(context)
                refreshFavorites()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        personsViewModel.initRepository(context)
        newbornViewModel.initRepository(context)
        refreshFavorites()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoriti") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Nazad")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Novorođeni",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

            if (favoriteNewborns.isEmpty()) {
                item {
                    Text(
                        "Nema sačuvanih novorođenčadi.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(favoriteNewborns) { newborn ->
                    Card(
                        onClick = {
                            navController.navigate("${NavRoutes.DETAILNEWBORNS}/${newborn.institution}/${newborn.dateUpdate}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Institucija: ${newborn.institution}")
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Osobe",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

            if (favoritePersons.isEmpty()) {
                item {
                    Text(
                        "Nema sačuvanih lica.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(favoritePersons) { person ->
                    Card(
                        onClick = {
                            navController.navigate("${NavRoutes.DETAILPERSONS}/${person.institution}/${person.dateUpdate}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Institucija: ${person.institution}")
                        }
                    }
                }
            }
        }
    }
}
