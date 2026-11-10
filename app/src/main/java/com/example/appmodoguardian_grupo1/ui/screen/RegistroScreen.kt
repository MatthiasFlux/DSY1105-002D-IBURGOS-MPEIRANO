package com.example.appmodoguardian_grupo1.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appmodoguardian_grupo1.viewmodel.UsuarioViewModel

private val DarkBg = Color(0xFF0B1220)
private val CardBg = Color(0xFF131D31)
private val InputBg = Color(0xFF1A263D)
private val PrimaryBlue = Color(0xFF2563EB)
private val AccentBorder = Color(0xFF2B3C5A)
private val TextMuted = Color(0xFF94A3B8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()

    var mostrarClave by remember { mutableStateOf(false) }
    var mostrarConfirmarClave by remember { mutableStateOf(false) }
    var confirmarClave by remember { mutableStateOf("") }
    var errorConfirmarClave by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Encabezado de Marca
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(PrimaryBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Escudo",
                        tint = Color.White
                    )
                }
                Column {
                    Text(
                        text = "MODO GUARDIÁN",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "SEGURIDAD INTEGRAL",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Insignia "Registro Seguro"
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFF1E293B))
                    .border(1.dp, Color(0xFF334155), CircleShape)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Verificado",
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Registro Seguro",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Título
            Text(
                text = "Crear Cuenta",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Ingresa tus datos para registrarte en el sistema",
                color = TextMuted,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo: Nombre Completo
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("NOMBRE COMPLETO", fontSize = 11.sp, color = TextMuted) },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null, tint = TextMuted)
                },
                isError = estado.errores.nombre != null,
                supportingText = {
                    estado.errores.nombre?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputBg,
                    unfocusedContainerColor = InputBg,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = AccentBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo: Correo Electrónico
            OutlinedTextField(
                value = estado.correo,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("CORREO ELECTRÓNICO", fontSize = 11.sp, color = TextMuted) },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null, tint = TextMuted)
                },
                isError = estado.errores.correo != null,
                supportingText = {
                    estado.errores.correo?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputBg,
                    unfocusedContainerColor = InputBg,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = AccentBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo: Contraseña
            OutlinedTextField(
                value = estado.clave,
                onValueChange = viewModel::onClaveChange,
                label = { Text("CONTRASEÑA", fontSize = 11.sp, color = TextMuted) },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = TextMuted)
                },
                trailingIcon = {
                    IconButton(onClick = { mostrarClave = !mostrarClave }) {
                        EyeIcon(visible = mostrarClave)
                    }
                },
                visualTransformation = if (mostrarClave) VisualTransformation.None else PasswordVisualTransformation(),
                isError = estado.errores.clave != null,
                supportingText = {
                    estado.errores.clave?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputBg,
                    unfocusedContainerColor = InputBg,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = AccentBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo: Confirmar Contraseña
            OutlinedTextField(
                value = confirmarClave,
                onValueChange = {
                    confirmarClave = it
                    if (errorConfirmarClave.isNotEmpty()) errorConfirmarClave = ""
                },
                label = { Text("CONFIRMAR CONTRASEÑA", fontSize = 11.sp, color = TextMuted) },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = TextMuted)
                },
                trailingIcon = {
                    IconButton(onClick = { mostrarConfirmarClave = !mostrarConfirmarClave }) {
                        EyeIcon(visible = mostrarConfirmarClave)
                    }
                },
                visualTransformation = if (mostrarConfirmarClave) VisualTransformation.None else PasswordVisualTransformation(),
                isError = errorConfirmarClave.isNotEmpty(),
                supportingText = {
                    if (errorConfirmarClave.isNotEmpty()) {
                        Text(text = errorConfirmarClave, color = MaterialTheme.colorScheme.error)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputBg,
                    unfocusedContainerColor = InputBg,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = AccentBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo: Dirección
            OutlinedTextField(
                value = estado.direccion,
                onValueChange = viewModel::onDireccionChange,
                label = { Text("DIRECCIÓN DE DOMICILIO", fontSize = 11.sp, color = TextMuted) },
                leadingIcon = {
                    Icon(Icons.Default.Home, contentDescription = null, tint = TextMuted)
                },
                isError = estado.errores.direccion != null,
                supportingText = {
                    estado.errores.direccion?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputBg,
                    unfocusedContainerColor = InputBg,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = AccentBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Checkbox Términos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = estado.aceptaTerminos,
                    onCheckedChange = viewModel::onAceptaTerminosChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = PrimaryBlue,
                        uncheckedColor = TextMuted
                    )
                )
                Text(
                    text = "Acepto los términos y condiciones de servicio",
                    color = Color.White,
                    fontSize = 13.sp
                )
            }

            // Botón de Registro con validación
            Button(
                onClick = {
                    val esFormularioValido = viewModel.validarFormulario()
                    val coincidenClaves = estado.clave == confirmarClave

                    if (!coincidenClaves) {
                        errorConfirmarClave = "Las contraseñas no coinciden"
                    } else {
                        errorConfirmarClave = ""
                    }

                    if (esFormularioValido && coincidenClaves) {
                        navController.navigate("resumen")
                    }
                },
                enabled = estado.aceptaTerminos,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    disabledContainerColor = Color(0xFF1E293B)
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Registrarme en el sistema",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Arrow",
                        tint = Color.White
                    )
                }
            }

            // Botón Volver
            OutlinedButton(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(14.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(AccentBorder)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Volver al inicio", color = TextMuted)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Banner inferior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CardBg)
                    .border(1.dp, AccentBorder, RoundedCornerShape(20.dp))
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Security",
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Protegido con encriptación de extremo a extremo",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}