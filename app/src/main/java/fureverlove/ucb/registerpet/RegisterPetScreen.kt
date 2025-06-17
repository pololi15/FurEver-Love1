package fureverlove.ucb.registerpet

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ucb.domain.model.Mascota
import fureverlove.ucb.components.TopBarWithBack
import fureverlove.ucb.home.FondoConPatitas
import fureverlove.ucb.components.obtenerNombreUbicacion

@Composable
fun RegisterPetScreen(
    viewModel: RegisterPetViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var latitud by remember { mutableStateOf(-16.5000) }
    var longitud by remember { mutableStateOf(-68.1500) }
    var genero by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val mensaje by viewModel.mensaje.collectAsState()
    val estado by viewModel.estado.collectAsState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }
    // Efecto para actualizar la ubicación cuando cambian las coordenadas
    LaunchedEffect(latitud, longitud) {
        if (latitud != -16.5000 && longitud != -68.1500) { // Valores iniciales
            ubicacion = obtenerNombreUbicacion(context, latitud, longitud)
        }
    }

    FondoConPatitas {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarWithBack(
                title = "Registrar Mascota",
                onBackClick = onBackClick
            )

            RegisterPetScreenContent(
                nombre = nombre,
                edad = edad,
                especie = especie,
                ubicacion = ubicacion,
                genero = genero,
                categoria = categoria,
                telefono = telefono,
                imageUri = imageUri.value,
                mensaje = mensaje,
                estado = estado,
                latitud = latitud,
                longitud = longitud,
                onNombreChange = { nombre = it },
                onEdadChange = { edad = it },
                onEspecieChange = { especie = it },
                onGeneroChange = { genero = it },
                onCategoriaChange = { categoria = it },
                onTelefonoChange = { telefono = it },
                onUbicacionChange = { ubicacion = it },
                onSelectImageClick = { imagePickerLauncher.launch("image/*") },
                onGuardarClick = {
                    if (nombre.isNotBlank() && edad.isNotBlank() && imageUri.value != null) {
                        val mascota = Mascota(
                            nombre = nombre,
                            edad = edad,
                            especie = especie,
                            ubicacion = ubicacion,
                            fotoUrl = "",
                            genero = genero,
                            categoria = categoria,
                            telefono = telefono
                        )
                        viewModel.subirImagenYGuardar(imageUri.value!!, mascota, context, onSuccess)
                    } else {
                        Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                onMapClick = { lat, lon ->
                    latitud = lat
                    longitud = lon
                    ubicacion = "Buscando dirección..."
                }
            )
        }
    }
}

@Composable
fun RegisterPetScreenContent(
    nombre: String,
    edad: String,
    especie: String,
    ubicacion: String,
    genero: String,
    categoria: String,
    telefono: String,
    imageUri: Uri?,
    mensaje: String,
    estado: RegisterPetViewModel.RegisterState,
    latitud: Double,
    longitud: Double,
    onNombreChange: (String) -> Unit,
    onEdadChange: (String) -> Unit,
    onEspecieChange: (String) -> Unit,
    onGeneroChange: (String) -> Unit,
    onCategoriaChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onUbicacionChange: (String) -> Unit,
    onSelectImageClick: () -> Unit,
    onGuardarClick: () -> Unit,
    onMapClick: (Double, Double) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = nombre,
                onValueChange = onNombreChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = edad,
                onValueChange = onEdadChange,
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = especie,
                onValueChange = onEspecieChange,
                label = { Text("Especie") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = ubicacion,
                onValueChange = onUbicacionChange,
                label = { Text("Ubicación") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false // Desactivamos la edición manual, ya que se selecciona con el mapa
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Selecciona una ubicación en el mapa", style = MaterialTheme.typography.labelMedium)

            val selectedPosition = LatLng(latitud, longitud)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(selectedPosition, 12f)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(LatLng(latitud, longitud), 12f)
                    },
                    onMapClick = { latLng ->
                        onMapClick(latLng.latitude, latLng.longitude)
                    }
                ) {
                    Marker(
                        state = MarkerState(position = LatLng(latitud, longitud)),
                        title = "Ubicación seleccionada"
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = genero,
                onValueChange = onGeneroChange,
                label = { Text("Género") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = categoria,
                onValueChange = onCategoriaChange,
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = telefono,
                onValueChange = onTelefonoChange,
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSelectImageClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seleccionar Imagen")
            }

            imageUri?.let { uri ->
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onGuardarClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Mascota")
            }

            when (estado) {
                RegisterPetViewModel.RegisterState.Loading -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is RegisterPetViewModel.RegisterState.Error -> {
                    Text(
                        text = (estado as RegisterPetViewModel.RegisterState.Error).mensaje,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                RegisterPetViewModel.RegisterState.Success -> {
                    Text(
                        text = "Registro exitoso",
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                else -> {}
            }

            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}