package fureverlove.ucb.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash")
    object AuthScreen : Screen("auth")
    object HomeScreen : Screen("home")
    object RegisterPetScreen : Screen("register_pet_screen")
    object  SendSimScreen: Screen( "send_sim")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
    object DogsScreen : Screen("dogs")
    object CatsScreen : Screen("cats")

    object Category : Screen("category/{category}") {
        fun passCategory(category: String) = "category/$category"
    }
}

