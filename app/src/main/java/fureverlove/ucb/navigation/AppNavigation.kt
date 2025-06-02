package fureverlove.ucb.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.ucb.domain.model.Mascota
import fureverlove.ucb.auth.AuthScreen
import fureverlove.ucb.favorites.FavoritesScreen
import fureverlove.ucb.home.CategoryScreen
import fureverlove.ucb.home.HomeScreen
import fureverlove.ucb.home.model.Owner
import fureverlove.ucb.profile.PetProfileScreen
import fureverlove.ucb.registerpet.RegisterPetScreen
import fureverlove.ucb.splash.SplashScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(
                    Screen.HomeScreen.route,
                    Screen.Favorites.route,
                    Screen.Profile.route
                )
            ) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.SplashScreen.route) {
                SplashScreen(
                    onNavigate = {
                        navController.navigate(Screen.AuthScreen.route) {
                            popUpTo(Screen.SplashScreen.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.AuthScreen.route) {
                AuthScreen(
                    onSuccess = {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.AuthScreen.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.HomeScreen.route) {
                HomeScreen(
                    navController = navController, //  Se pasa navController
                    onLogout = {
                        navController.navigate(Screen.AuthScreen.route) {
                            popUpTo(Screen.HomeScreen.route) { inclusive = true }
                        }
                    },
                    onCategoryClick = { category ->
                        navController.navigate(Screen.Category.passCategory(category))
                    }
                )
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen()
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
            composable(Screen.Profile.route) { backStackEntry ->
                val pet = navController.previousBackStackEntry
                    ?.savedStateHandle?.get<Mascota>("pet")
                val owner = navController.previousBackStackEntry
                    ?.savedStateHandle?.get<Owner>("owner")

                if (pet != null && owner != null) {
                    PetProfileScreen(pet = pet, owner = owner)
                } else {
                    // Puedes mostrar una pantalla de error o regresar
                    Text("No se pudieron cargar los datos del perfil.")
                }
            }

            //  Solo una definición para la ruta Category
            composable(Screen.Category.route) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: "canes"
                CategoryScreen(
                    category = category,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
