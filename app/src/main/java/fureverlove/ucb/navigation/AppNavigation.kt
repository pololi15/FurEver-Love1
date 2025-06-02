package fureverlove.ucb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fureverlove.ucb.auth.AuthScreen
import fureverlove.ucb.home.HomeUI
import fureverlove.ucb.pet.PetDetailScreen
import fureverlove.ucb.registerpet.RegisterPetScreen

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
                },
                onAddPet = {
                    navController.navigate(Screen.RegisterPetScreen.route)
                },
                onPetClick = { petId -> navController.navigate("petDetail/$petId") }
            )
        }

        composable(route = Screen.RegisterPetScreen.route) {
            RegisterPetScreen(
                onSuccess = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.RegisterPetScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = "petDetail/{petId}",
            arguments = listOf(navArgument("petId") { type = NavType.StringType })
        ) {
            PetDetailScreen()
        }

    }
}
