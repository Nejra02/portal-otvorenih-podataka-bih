package com.example.projekatzavrsni.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projekatzavrsni.presentation.navigation.NavRoutes
import com.example.projekatzavrsni.presentation.viewmodel.NewbornViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import androidx.compose.material.icons.filled.Share
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewbornsDetailScreen(
    institution: String,
    dateUpdate: String,
    navController: NavHostController
) {
    val parentEntry = remember {
        navController.getBackStackEntry(NavRoutes.NEWBORNS)
    }
    val viewModel = viewModel<NewbornViewModel>(parentEntry)
    val newborns by viewModel.newborns.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (newborns.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val newborn = newborns.find {
        it.institution == institution && it.dateUpdate == dateUpdate
    }


    if (newborn == null) {
        Text("Podatak nije pronađen", modifier = Modifier.padding(16.dp))
        return
    }

    val isFav = remember { mutableStateOf(newborn.isFavorite) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalji novorođenčadi") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Nazad")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val shareText = """
                        Ustanova: ${newborn.institution}
                        Ukupno: ${newborn.total} (Muških: ${newborn.maleTotal}, Ženskih: ${newborn.femaleTotal})
                        Datum ažuriranja: ${newborn.dateUpdate}
                    """.trimIndent()

                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, shareText)
                            type = "text/plain"
                        }

                        context.startActivity(
                            Intent.createChooser(shareIntent, "Podijeli podatke putem:")
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
                    Text("Institucija: ${newborn.institution}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Muških: ${newborn.maleTotal}")
                    Text("Ženskih: ${newborn.femaleTotal}")
                    Text("Ukupno: ${newborn.total}")
                    Text("Entitet: ${newborn.entity}")
                    Text("Kanton: ${newborn.canton}")
                    Text("Općina: ${newborn.municipality}")
                    Text("Datum ažuriranja: ${newborn.dateUpdate}")
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
                        viewModel.toggleFavorite(newborn)
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
