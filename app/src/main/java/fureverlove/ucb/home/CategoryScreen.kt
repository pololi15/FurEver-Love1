
package fureverlove.ucb.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import fureverlove.ucb.home.model.Pet
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    category: String,
    viewModel: CategoryViewModel = viewModel(),
    onBack: () -> Unit
) {
    val pets by viewModel.getPetsByCategory(category).collectAsState()

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
                items(pets) { pet ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable { /* TODO: Ir al detalle */ },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(8.dp)) {
                            Image(
                                painter = rememberImagePainter(pet.imageUrl),
                                contentDescription = pet.name,
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth()
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(pet.name, style = MaterialTheme.typography.bodyLarge)
                            Text(pet.gender, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

