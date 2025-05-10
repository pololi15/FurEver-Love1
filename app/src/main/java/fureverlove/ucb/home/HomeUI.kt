package fureverlove.ucb.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ucb.domain.model.Mascota
import fureverlove.ucb.components.PetCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUI(
    viewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit,
    onAddPet: () -> Unit
) {
    val mascotas by viewModel.mascotas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mascotas en adopción") },
                actions = {
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        onLogout()
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPet,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar mascota")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
            .padding(8.dp)
        ) {
            items(mascotas) { mascota ->
                PetCard(mascota)
            }
        }
    }




}
