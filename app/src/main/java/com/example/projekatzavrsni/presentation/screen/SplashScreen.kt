package com.example.projekatzavrsni.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projekatzavrsni.presentation.navigation.NavRoutes
import kotlinx.coroutines.delay
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projekatzavrsni.ui.theme.ProjekatZavrsniTheme


@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000) // 2 sekunde splash
        navController.navigate(NavRoutes.ONBOARDING) {
            popUpTo(0) // izbaci splash iz stacka
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Dobrodošli",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        )
    }
}

/* samo za testiranje frontenda */
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ProjekatZavrsniTheme {
        SplashScreen(rememberNavController())
    }
}



