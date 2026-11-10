package com.example.appmodoguardian_grupo1.ui.screen

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appmodoguardian_grupo1.viewmodel.UsuarioViewModel

private val DarkBg = Color(0xFF0B1220)
private val CardBg = Color(0xFF131D31)
private val PrimaryBlue = Color(0xFF2563EB)
private val AccentBorder = Color(0xFF2B3C5A)
private val TextMuted = Color(0xFF94A3B8)

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()
    var fotoBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            fotoBitmap = bitmap
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = CardBg,
                tonalElevation = 8.dp
            ) {
                // Botón Casa (Navega de vuelta a Home)
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("home") {
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = PrimaryBlue,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    )
                )

                // Botón Persona (Activo en pantalla de perfil)
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Ya estamos en Perfil */ },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = PrimaryBlue,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBg)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Encabezado
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
                            text = "CONFIGURACIÓN DE PERFIL",
                            color = TextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Insignia Perfil Activo
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
                            text = "Perfil Activo",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Perfil de Usuario",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                // Avatar / Previsualizador de la foto
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(CardBg)
                        .border(2.dp, PrimaryBlue, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (fotoBitmap != null) {
                        Image(
                            bitmap = fotoBitmap!!.asImageBitmap(),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = TextMuted,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                }

                // Botón Abrir Cámara
                Button(
                    onClick = { launcherCamara.launch(null) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Cámara",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tomar Foto de Perfil",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Tarjeta de Datos Registrados
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardBg),
                    shape = RoundedCornerShape(16.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(AccentBorder)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "DATOS REGISTRADOS",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        HorizontalDivider(color = AccentBorder)

                        ItemDato(label = "Nombre:", valor = estado.nombre.ifBlank { "No especificado" })
                        ItemDato(label = "Correo:", valor = estado.correo.ifBlank { "No especificado" })
                        ItemDato(label = "Dirección:", valor = estado.direccion.ifBlank { "No especificada" })
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemDato(label: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = TextMuted,
            fontSize = 14.sp
        )
        Text(
            text = valor,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}