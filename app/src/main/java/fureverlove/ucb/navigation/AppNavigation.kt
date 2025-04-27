package fureverlove.ucb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fureverlove.ucb.auth.AuthScreen
import fureverlove.ucb.home.HomeUI

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.AuthScreen.route) {

        composable(route = Screen.AuthScreen.route) {
            AuthScreen(
                onSuccess = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.AuthScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.HomeScreen.route) {
            HomeUI(
                onLogout = {
                    navController.navigate(Screen.AuthScreen.route) {
                        popUpTo(Screen.HomeScreen.route) { inclusive = true }
                    }
                }
            )
        }
    }
}