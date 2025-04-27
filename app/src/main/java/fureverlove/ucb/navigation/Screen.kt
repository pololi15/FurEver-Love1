package fureverlove.ucb.navigation

sealed class Screen(val route: String) {
    object AuthScreen : Screen("auth_screen")
    object HomeScreen : Screen("home_screen")
}
