package com.example.projekatzavrsni.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projekatzavrsni.presentation.screen.components.BarChartView
import com.example.projekatzavrsni.presentation.screen.components.PersonBarChartView
import com.example.projekatzavrsni.presentation.viewmodel.NewbornViewModel
import com.example.projekatzavrsni.presentation.viewmodel.PersonsViewModel
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues

@Composable
fun StatisticsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: NewbornViewModel = viewModel()
    val personViewModel: PersonsViewModel = viewModel()

    val chartData by viewModel.chartData.collectAsState()
    val cantonData by personViewModel.cantonChartData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initRepository(context)
        viewModel.loadChartData()
        personViewModel.initRepository(context)
        personViewModel.fetchPersons()
        personViewModel.loadCantonChartData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    text = "broj novorođenih po entitetima",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 25.sp,
                        lineHeight = 32.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                if (chartData.isNotEmpty()) {
                    BarChartView(chartData)
                } else {
                    CircularProgressIndicator()
                }
            }

            item {
                Text(
                    text = "broj osoba po kantonima - federacija",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 25.sp,
                        lineHeight = 32.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                if (cantonData.isNotEmpty()) {
                    PersonBarChartView(cantonData)
                } else {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
