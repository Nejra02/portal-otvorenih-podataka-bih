package com.example.projekatzavrsni.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projekatzavrsni.presentation.navigation.NavRoutes
import com.example.projekatzavrsni.presentation.viewmodel.PersonsViewModel
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.ArrowBack
import android.content.Intent
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonsDetailScreen(
    institution: String,
    dateUpdate: String,
    navController: NavHostController
) {
    val parentEntry = remember {
        navController.getBackStackEntry(NavRoutes.PERSONS)
    }
    val viewModel = viewModel<PersonsViewModel>(parentEntry)
    val persons by viewModel.persons.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (persons.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val person = persons.find {
        it.institution == institution && it.dateUpdate == dateUpdate
    }

    if (person == null) {
        Text("Podatak nije pronađen", modifier = Modifier.padding(16.dp))
        return
    }

    val isFav = remember { mutableStateOf(person.isFavorite) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalji osoba") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Nazad")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val shareText = """
                        Institucija: ${person.institution}
                        Entitet: ${person.entity}
                        Kanton: ${person.canton}
                        Općina: ${person.municipality}
                        Ukupno: ${person.total}
                        Datum ažuriranja: ${person.dateUpdate}
                    """.trimIndent()

                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, shareText)
                            type = "text/plain"
                        }

                        context.startActivity(
                            Intent.createChooser(shareIntent, "Podijeli podatke preko:")
                        )
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Podijeli")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(padding)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Institucija: ${person.institution}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Sa prebivalištem: ${person.withResidenceTotal}")
                    Text("Ukupno: ${person.total}")
                    Text("Entitet: ${person.entity}")
                    Text("Kanton: ${person.canton}")
                    Text("Općina: ${person.municipality}")
                    Text("Datum ažuriranja: ${person.dateUpdate}")
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        viewModel.toggleFavorite(person)
                        coroutineScope.launch {
                            delay(300)
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = if (isFav.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFav.value) Color.Red else Color.Gray
                        )
                    }

                    Text("Dodaj u favorite")
                }
            }
        }
    }
}
