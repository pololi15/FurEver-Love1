package fureverlove.ucb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import fureverlove.ucb.ui.theme.FurEverLoveTheme
import fureverlove.ucb.navigation.AppNavigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(R.style.SplashTheme)
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            FurEverLoveTheme {
                AppNavigation()
            }
        }
        window.setBackgroundDrawable(null)

    }
}
