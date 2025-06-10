package fureverlove.ucb.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await
import java.util.Locale
import java.util.TimeZone

suspend fun obtenerUbicacionActual(context: Context): String? {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // Verificación de permisos (solo para evitar fallos)
    val permisoFine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
    val permisoCoarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)

    if (permisoFine != PackageManager.PERMISSION_GRANTED && permisoCoarse != PackageManager.PERMISSION_GRANTED) {
        return "Permisos no concedidos"
    }

    val location = fusedLocationClient.lastLocation.await()

    if (location != null) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val direcciones = try {
            geocoder.getFromLocation(location.latitude, location.longitude, 1)
        } catch (e: Exception) {
            null
        }

        direcciones?.firstOrNull()?.let { direccion ->
            val ciudad = direccion.locality ?: "Ciudad desconocida"
            val pais = direccion.countryName ?: ""
            val zona = TimeZone.getDefault().id
            return "$ciudad, $pais\nZona Horaria: $zona"
        }
    }

    return "Ubicación no disponible"
}
suspend fun obtenerNombreUbicacion(context: Context, latitud: Double, longitud: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val direcciones = geocoder.getFromLocation(latitud, longitud, 1)
        val direccion = direcciones?.firstOrNull()
        direccion?.getAddressLine(0) ?: "Dirección desconocida"
    } catch (e: Exception) {
        "Error obteniendo dirección"
    }
}
