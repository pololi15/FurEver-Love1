package fureverlove.ucb.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.ucb.domain.model.Mascota
import fureverlove.ucb.components.PetCard
import fureverlove.ucb.navigation.Screen
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onAddPet: () -> Unit,
    navController: NavController,
    onLogout: () -> Unit,
    onCategoryClick: (String) -> Unit
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

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                // 1. Encabezado con botón de logout
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Cochabamba, Bolivia", fontSize = 14.sp)
                        Button(onClick = {
                            FirebaseAuth.getInstance().signOut()
                            onLogout()
                        }) {
                            Text("Cerrar sesión")
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // 2. Banner promocional
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF439EF4))
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                "Una patita amiga\nnecesita de ti.",
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    // Aquí puedes navegar a una pantalla de "Dar en adopción"
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600))
                            ) {
                                Text("DAR EN ADOPCIÓN")
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }



                // 4. Sección de categorías
                item {
                    Text(
                        "Categorías",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { onCategoryClick("canes") },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF50E3C2)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("CANES")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { onCategoryClick("gatos") },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("GATOS")
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                // 5. Lista de mascotas en adopción
                item {
                    Text(
                        "Mascotas en adopción",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }


            }
        }
    }
}
