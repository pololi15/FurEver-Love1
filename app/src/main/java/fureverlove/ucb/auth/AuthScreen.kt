package fureverlove.ucb.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fureverlove.ucb.R

// Paleta de colores
val BlueMain = Color(0xFF439EF4)
val BackgroundColor = Color(0xFFF8FAFC)
val MainText = Color(0xFF2D3748)

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    var isLoginMode by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        when (state) {
            is AuthViewModel.AuthState.Success -> {
                viewModel.reset()
                onSuccess()
            }
            is AuthViewModel.AuthState.Error -> {
                val error = (state as AuthViewModel.AuthState.Error).message
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                viewModel.reset()
            }
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(BackgroundColor)) {
        // Decoración superior (Azul)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .background(BlueMain)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo o Imagen principal
            Card(
                modifier = Modifier.size(160.dp),
                shape = RoundedCornerShape(80.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ima_login),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Tarjeta de Formulario
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isLoginMode) "¡Bienvenido!" else "Crear Cuenta",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MainText
                    )
                    Text(
                        text = if (isLoginMode) "Inicia sesión para continuar" else "Únete a nuestra comunidad",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    AuthTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Correo electrónico",
                        icon = Icons.Default.Email
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AuthTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Contraseña",
                        icon = Icons.Default.Lock,
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            if (email.isNotBlank() && password.isNotBlank()) {
                                if (isLoginMode) viewModel.login(email, password)
                                else viewModel.register(email, password)
                            } else {
                                Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BlueMain),
                        enabled = state !is AuthViewModel.AuthState.Loading
                    ) {
                        if (state is AuthViewModel.AuthState.Loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                text = if (isLoginMode) "INGRESAR" else "REGISTRARME",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Cambio de modo
            Row(
                modifier = Modifier.clickable { isLoginMode = !isLoginMode }.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isLoginMode) "¿No tienes cuenta? " else "¿Ya tienes cuenta? ",
                    color = MainText
                )
                Text(
                    text = if (isLoginMode) "Regístrate aquí" else "Inicia sesión",
                    color = BlueMain,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = BlueMain) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BlueMain,
            focusedLabelColor = BlueMain
        ),
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
        fontFamily = FontFamily.SansSerif,
        style = style
    )
}
