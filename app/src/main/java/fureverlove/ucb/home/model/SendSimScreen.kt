package fureverlove.ucb.home.model


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@Composable
fun SendSimScreen() {
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var selectedLatLng by rememberSaveable { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-16.5, -68.15), 12f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Teléfono de Referencia")
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Selecciona una ubicación:")
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
        ) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = {
                    selectedLatLng = it
                }
            ) {
                selectedLatLng?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Ubicación seleccionada"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedLatLng?.let {
            Text("Latitud: ${it.latitude}")
            Text("Longitud: ${it.longitude}")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Aquí puedes guardar los datos o navegar
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    }
}
