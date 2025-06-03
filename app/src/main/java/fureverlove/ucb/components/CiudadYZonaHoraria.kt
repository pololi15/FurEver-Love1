package fureverlove.ucb.components

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun CiudadYZonaHoraria(
    modifier: Modifier = Modifier,
    onError: (String) -> Unit = {},
    showIcon: Boolean = true
) {
    val context = LocalContext.current
    var ubicacion by remember { mutableStateOf("Obteniendo ubicación...") }
    var hasPermission by remember { mutableStateOf(false) }

    // Verificar permisos
    LaunchedEffect(Unit) {
        hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            ubicacion = "Permiso de ubicación requerido"
            onError("Permisos de ubicación no concedidos")
            return@LaunchedEffect
        }

        try {
            ubicacion = withContext(Dispatchers.IO) {
                obtenerUbicacionActual(context) ?: "Ubicación no disponible"
            }
        } catch (e: Exception) {
            ubicacion = when (e) {
                is SecurityException -> "Permiso denegado"
                is java.io.IOException -> "Error de conexión"
                else -> "Ubicación no disponible"
            }
            onError(e.message ?: "Error desconocido")
            Log.e("CiudadYZonaHoraria", "Error: ${e.message}")
        }
    }

    // Mostrar texto en pantalla
    Text(text = ubicacion, modifier = modifier)
}
