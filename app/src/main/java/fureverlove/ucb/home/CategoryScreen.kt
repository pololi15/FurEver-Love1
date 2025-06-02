package fureverlove.ucb.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    category: String,
    viewModel: CategoryViewModel = viewModel(), // Usamos el correcto
    onBack: () -> Unit
) {
    // Llama a la función que filtra por categoría (solo una vez)
    LaunchedEffect(category) {
        viewModel.getPetsByCategory(category)
    }

    val pets by viewModel.filteredPets.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Categoría: ${category.uppercase()}")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(pets) { mascota ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable { /* TODO: Navegar a detalle */ },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(8.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(mascota.fotoUrl),
                                contentDescription = mascota.nombre,
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth()
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(mascota.nombre, style = MaterialTheme.typography.bodyLarge)
                            mascota.genero?.let {
                                Text(it, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}


