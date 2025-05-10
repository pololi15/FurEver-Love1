package fureverlove.ucb.registerpet

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucb.domain.model.Mascota

@Composable
fun RegisterPetScreen(viewModel: RegisterPetViewModel = hiltViewModel(), onSuccess: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var fotoUrl by remember { mutableStateOf("") }
    val mensaje by viewModel.mensaje.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Registrar Mascota", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        TextField(value = edad, onValueChange = { edad = it }, label = { Text("Edad") })
        TextField(value = especie, onValueChange = { especie = it }, label = { Text("Especie") })
        TextField(value = ubicacion, onValueChange = { ubicacion = it }, label = { Text("Ubicación") })
        TextField(value = fotoUrl, onValueChange = { fotoUrl = it }, label = { Text("URL de la imagen") })

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            if (nombre.isNotBlank() && edad.isNotBlank()) {
                val mascota = Mascota(
                    nombre = nombre,
                    edad = edad,
                    especie = especie,
                    ubicacion = ubicacion,
                    fotoUrl = fotoUrl
                )
                viewModel.guardarMascota(mascota)
                onSuccess()
            }
        }) {
            Text("Guardar Mascota")
        }

        if (mensaje.isNotBlank()) {
            Spacer(Modifier.height(10.dp))
            Text(mensaje, color = MaterialTheme.colorScheme.primary)
        }
    }
}
