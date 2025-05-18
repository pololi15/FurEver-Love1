package fureverlove.ucb.registerpet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ucb.domain.model.Mascota
import androidx.compose.ui.graphics.Color


@Composable
fun RegisterPetScreen(viewModel: RegisterPetViewModel = hiltViewModel(), onSuccess: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val mensaje by viewModel.mensaje.collectAsState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }

    RegisterPetScreenContent(
        nombre = nombre,
        edad = edad,
        especie = especie,
        ubicacion = ubicacion,
        imageUri = imageUri.value,
        mensaje = mensaje,
        onNombreChange = { nombre = it },
        onEdadChange = { edad = it },
        onEspecieChange = { especie = it },
        onUbicacionChange = { ubicacion = it },
        onSelectImageClick = { imagePickerLauncher.launch("image/*") },
        onGuardarClick = {
            if (nombre.isNotBlank() && edad.isNotBlank() && imageUri.value != null) {
                val mascota = Mascota(
                    nombre = nombre,
                    edad = edad,
                    especie = especie,
                    ubicacion = ubicacion,
                    fotoUrl = "" // Se actualiza en el ViewModel
                )
                viewModel.subirImagenYGuardar(imageUri.value!!, mascota, context, onSuccess)
            }
        }
    )
}

@Composable
fun RegisterPetScreenContent(
    nombre: String,
    edad: String,
    especie: String,
    ubicacion: String,
    imageUri: Uri?,
    mensaje: String,
    onNombreChange: (String) -> Unit,
    onEdadChange: (String) -> Unit,
    onEspecieChange: (String) -> Unit,
    onUbicacionChange: (String) -> Unit,
    onSelectImageClick: () -> Unit,
    onGuardarClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            Text("Registrar Mascota", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            TextField(value = nombre, onValueChange = onNombreChange, label = { Text("Nombre") })
            TextField(value = edad, onValueChange = onEdadChange, label = { Text("Edad") })
            TextField(value = especie, onValueChange = onEspecieChange, label = { Text("Especie") })
            TextField(value = ubicacion, onValueChange = onUbicacionChange, label = { Text("Ubicación") })

            Spacer(Modifier.height(8.dp))

            Button(onClick = onSelectImageClick) {
                Text("Seleccionar Imagen")
            }

            imageUri?.let { uri ->
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Button(onClick = onGuardarClick) {
                Text("Guardar Mascota")
            }

            if (mensaje.isNotBlank()) {
                Spacer(Modifier.height(10.dp))
                Text(mensaje, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterPetScreenPreview() {
    RegisterPetScreenContent(
        nombre = "Firulais",
        edad = "3",
        especie = "Perro",
        ubicacion = "La Paz",
        imageUri = Uri.parse("https://placekitten.com/400/200"), // Simulación de imagen
        mensaje = "Mascota registrada correctamente",
        onNombreChange = {},
        onEdadChange = {},
        onEspecieChange = {},
        onUbicacionChange = {},
        onSelectImageClick = {},
        onGuardarClick = {}
    )
}
