package fureverlove.ucb.registerpet

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import fureverlove.ucb.components.obtenerNombreUbicacion

// Paleta de colores solicitada
val BlueMain = Color(0xFF439EF4)
val YellowAccent = Color(0xFFFFD600)
val BackgroundColor = Color(0xFFF8FAFC)
val MainText = Color(0xFF2D3748)
val GrayLight = Color(0xFFF1F5F9)

// Tipografía: Usaremos SansSerif por defecto para máxima compatibilidad
val AppFontFamily = FontFamily.SansSerif

@Composable
fun RegisterPetScreen(
    viewModel: RegisterPetViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    var currentStep by remember { mutableStateOf(1) }
    
    // Estados del formulario
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") } 
    var genero by remember { mutableStateOf("") } 
    var ubicacion by remember { mutableStateOf("") }
    var latitud by remember { mutableStateOf(-16.5000) }
    var longitud by remember { mutableStateOf(-68.1500) }
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

    LaunchedEffect(latitud, longitud) {
        if (latitud != -16.5000 && longitud != -68.1500) {
            ubicacion = obtenerNombreUbicacion(context, latitud, longitud)
        }
    }

    Scaffold(
        topBar = {
            TopBarWithBack(
                title = "Registro de Mascota",
                onBackClick = onBackClick
            )
        },
        containerColor = BackgroundColor
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // 1. Indicador de Pasos (Stepper)
            StepIndicator(currentStep = currentStep)

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Contenido del Formulario por pasos
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                    } else {
                        slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
                    }
                }, label = "StepTransition"
            ) { step ->
                Column(modifier = Modifier.padding(16.dp)) {
                    when (step) {
                        1 -> BasicInfoStep(
                            nombre = nombre, onNombreChange = { nombre = it },
                            edad = edad, onEdadChange = { edad = it },
                            especie = especie, onEspecieChange = { especie = it },
                            genero = genero, onGeneroChange = { genero = it }
                        )
                        2 -> PhotoStep(
                            imageUri = imageUri.value,
                            onSelectImage = { imagePickerLauncher.launch("image/*") }
                        )
                        3 -> LocationStep(
                            latitud = latitud, longitud = longitud,
                            ubicacion = ubicacion,
                            onMapClick = { lat, lon ->
                                latitud = lat
                                longitud = lon
                                ubicacion = "Buscando dirección..."
                            }
                        )
                        4 -> ContactStep(
                            telefono = telefono, onTelefonoChange = { telefono = it },
                            categoria = categoria, onCategoriaChange = { categoria = it },
                            estado = estado,
                            onGuardarClick = {
                                if (nombre.isNotBlank() && edad.isNotBlank() && imageUri.value != null && especie.isNotBlank()) {
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
                                    Toast.makeText(context, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))

                    // 3. Botones de Navegación
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (currentStep > 1) {
                            OutlinedButton(
                                onClick = { currentStep-- },
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.height(50.dp).weight(1f),
                                border = borderStroke(1.dp, BlueMain)
                            ) {
                                Text("Anterior", color = BlueMain, fontWeight = FontWeight.Medium)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                        
                        if (currentStep < 4) {
                            Button(
                                onClick = {
                                    if (canGoNext(currentStep, nombre, edad, especie, genero, imageUri.value)) {
                                        currentStep++
                                    } else {
                                        Toast.makeText(context, "Por favor completa este paso", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BlueMain),
                                modifier = Modifier.height(50.dp).weight(1f)
                            ) {
                                Text("Siguiente", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
            
            if (mensaje.isNotBlank()) {
                Text(
                    text = mensaje,
                    modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
                    color = BlueMain,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun borderStroke(width: androidx.compose.ui.unit.Dp, color: Color) = androidx.compose.foundation.BorderStroke(width, color)

private fun canGoNext(step: Int, nombre: String, edad: String, especie: String, genero: String, uri: Uri?): Boolean {
    return when(step) {
        1 -> nombre.isNotBlank() && edad.isNotBlank() && especie.isNotBlank() && genero.isNotBlank()
        2 -> uri != null
        3 -> true
        else -> true
    }
}

@Composable
fun StepIndicator(currentStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..4) {
            StepCircle(step = i, isSelected = i <= currentStep)
            if (i < 4) {
                Box(modifier = Modifier.weight(1f).height(2.dp).background(if (i < currentStep) BlueMain else Color.LightGray))
            }
        }
    }
}

@Composable
fun StepCircle(step: Int, isSelected: Boolean) {
    val color = if (isSelected) BlueMain else Color.LightGray
    Box(
        modifier = Modifier.size(36.dp).clip(RoundedCornerShape(18.dp)).background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(text = step.toString(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun BasicInfoStep(
    nombre: String, onNombreChange: (String) -> Unit,
    edad: String, onEdadChange: (String) -> Unit,
    especie: String, onEspecieChange: (String) -> Unit,
    genero: String, onGeneroChange: (String) -> Unit
) {
    SectionCard(title = "Información Básica") {
        CustomTextField(value = nombre, onValueChange = onNombreChange, label = "Nombre de la mascota", icon = Icons.Default.Person)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(value = edad, onValueChange = onEdadChange, label = "Edad", icon = Icons.Default.Info)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Tipo de Mascota", fontWeight = FontWeight.SemiBold, color = MainText)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            ChoiceCard(label = "Perro", icon = "🐶", isSelected = especie == "perro", onSelect = { onEspecieChange("perro") }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(12.dp))
            ChoiceCard(label = "Gato", icon = "🐱", isSelected = especie == "gato", onSelect = { onEspecieChange("gato") }, modifier = Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Género", fontWeight = FontWeight.SemiBold, color = MainText)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            ChoiceCard(label = "Macho", icon = "♂️", isSelected = genero == "macho", onSelect = { onGeneroChange("macho") }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(12.dp))
            ChoiceCard(label = "Hembra", icon = "♀️", isSelected = genero == "hembra", onSelect = { onGeneroChange("hembra") }, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun PhotoStep(imageUri: Uri?, onSelectImage: () -> Unit) {
    SectionCard(title = "Foto de la Mascota") {
        Box(
            modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(24.dp)).background(GrayLight).clickable { onSelectImage() }
                .border(2.dp, if (imageUri != null) BlueMain else Color.Transparent, RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Image(painter = rememberAsyncImagePainter(imageUri), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.AddCircle, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                    Text("Subir foto", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun LocationStep(latitud: Double, longitud: Double, ubicacion: String, onMapClick: (Double, Double) -> Unit) {
    SectionCard(title = "Ubicación") {
        Text(text = if (ubicacion.isBlank()) "Selecciona en el mapa" else ubicacion, color = MainText, modifier = Modifier.padding(bottom = 12.dp))
        Box(modifier = Modifier.fillMaxWidth().height(300.dp).clip(RoundedCornerShape(24.dp))) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(LatLng(latitud, longitud), 14f) },
                onMapClick = { onMapClick(it.latitude, it.longitude) }
            ) {
                Marker(state = MarkerState(position = LatLng(latitud, longitud)))
            }
        }
    }
}

@Composable
fun ContactStep(
    telefono: String, onTelefonoChange: (String) -> Unit,
    categoria: String, onCategoriaChange: (String) -> Unit,
    estado: RegisterPetViewModel.RegisterState,
    onGuardarClick: () -> Unit
) {
    SectionCard(title = "Contacto") {
        CustomTextField(value = telefono, onValueChange = onTelefonoChange, label = "Teléfono", icon = Icons.Default.Phone)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(value = categoria, onValueChange = onCategoriaChange, label = "Categoría adicional", icon = Icons.Default.Menu)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onGuardarClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = YellowAccent),
            enabled = estado !is RegisterPetViewModel.RegisterState.Loading
        ) {
            if (estado is RegisterPetViewModel.RegisterState.Loading) {
                CircularProgressIndicator(color = BlueMain)
            } else {
                Text("PUBLICAR", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun SectionCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MainText)
            Spacer(modifier = Modifier.height(20.dp))
            content()
        }
    }
}

@Composable
fun ChoiceCard(label: String, icon: String, isSelected: Boolean, onSelect: () -> Unit, modifier: Modifier = Modifier) {
    val scale by animateFloatAsState(if (isSelected) 1.05f else 1f, label = "")
    val backgroundColor = if (isSelected) BlueMain.copy(alpha = 0.1f) else GrayLight
    val borderColor = if (isSelected) BlueMain else Color.Transparent

    Box(
        modifier = modifier.scale(scale).clip(RoundedCornerShape(20.dp)).background(backgroundColor)
            .border(2.dp, borderColor, RoundedCornerShape(20.dp)).clickable { onSelect() }.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = icon, fontSize = 32.sp)
            Text(text = label, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium, color = if (isSelected) BlueMain else MainText)
        }
    }
}

@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String, icon: ImageVector) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = BlueMain) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BlueMain, focusedLabelColor = BlueMain),
        singleLine = true
    )
}

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: androidx.compose.ui.unit.TextUnit = androidx.compose.ui.unit.TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    style: androidx.compose.ui.text.TextStyle = LocalTextStyle.current
) {
    androidx.compose.material3.Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = AppFontFamily,
        style = style
    )
}
