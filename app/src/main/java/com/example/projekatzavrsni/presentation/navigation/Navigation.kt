package com.example.projekatzavrsni.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.projekatzavrsni.presentation.screen.*

object NavRoutes {
    const val NEWBORNS = "newborns"
    const val PERSONS = "persons"
    const val SPLASHSCREEN = "splash"
    const val ONBOARDING = "onboarding"
    const val FAVORITES = "favorites"
    const val DETAILNEWBORNS = "detailnewborns"
    const val DETAILPERSONS = "detailpersons"
    const val STATISTICS = "statistics"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.SPLASHSCREEN
    ) {
        composable(NavRoutes.SPLASHSCREEN) {
            SplashScreen(navController)
        }
        composable(NavRoutes.NEWBORNS) {
            NewbornScreen(navController = navController)
        }
        composable(NavRoutes.PERSONS) {
            PersonsScreen(navController = navController)
        }
        composable(NavRoutes.ONBOARDING) {
            OnboardingScreen(navController)
        }
        composable("${NavRoutes.DETAILNEWBORNS}/{institution}/{dateUpdate}") { backStackEntry ->
            val institution = backStackEntry.arguments?.getString("institution") ?: ""
            val dateUpdate = backStackEntry.arguments?.getString("dateUpdate") ?: ""
            NewbornsDetailScreen(institution = institution, dateUpdate = dateUpdate, navController = navController)

        }
        composable("${NavRoutes.DETAILPERSONS}/{institution}/{dateUpdate}") { backStackEntry ->
            val institution = backStackEntry.arguments?.getString("institution") ?: ""
            val dateUpdate = backStackEntry.arguments?.getString("dateUpdate") ?: ""
            PersonsDetailScreen(institution = institution, dateUpdate = dateUpdate, navController = navController)
        }
        composable(NavRoutes.FAVORITES) {
            FavoritesScreen(navController)
        }

        composable(NavRoutes.STATISTICS) {
            StatisticsScreen(navController = navController)
        }

    }
}
