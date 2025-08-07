package com.baseproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.baseproject.ui.screens.HomeScreen
import com.baseproject.ui.screens.LoginScreen

object NavigationUtil {

    @Composable
    fun InitNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Route.LOGIN){
            composable(Route.LOGIN){ LoginScreen(navController) }
            composable(
                route = Route.HOME,
                deepLinks = listOf(navDeepLink { uriPattern = "com.baseproject://${Route.HOME}/{arg}" })
            ) { backStackEntry ->
                backStackEntry.arguments.apply {
                    val arg = this?.getString("arg")
                    println("arg: $arg")
                }
                HomeScreen()
            }
        }

    }


}