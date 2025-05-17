package fureverlove.ucb.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash")
    object AuthScreen : Screen("auth")
    object HomeScreen : Screen("home")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
    object DogsScreen : Screen("dogs")
    object CatsScreen : Screen("cats")

    object Category : Screen("category/{category}") {
        fun passCategory(category: String) = "category/$category"
    }
}
