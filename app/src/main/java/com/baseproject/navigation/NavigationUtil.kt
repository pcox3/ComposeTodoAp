package com.baseproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.baseproject.ui.screens.HomeScreen
import com.baseproject.ui.screens.LoginScreen

object NavigationUtil {

    @Composable
    fun InitNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Route.LOGIN){
            composable(Route.LOGIN){ LoginScreen(navController) }
            composable(Route.HOME) { HomeScreen() }
        }

    }


}