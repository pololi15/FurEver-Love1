package fureverlove.ucb.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import fureverlove.ucb.auth.AuthScreen
import fureverlove.ucb.favorites.FavoritesScreen
import fureverlove.ucb.home.CategoryScreen
import fureverlove.ucb.home.HomeScreen
import fureverlove.ucb.profile.ProfileScreen
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

            composable(Screen.Profile.route) {
                ProfileScreen()
            }

            composable(Screen.Category.route) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: "canes"
                CategoryScreen(
                    category = category,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Category.route) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: "gatos"
                CategoryScreen(
                    category = category,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
