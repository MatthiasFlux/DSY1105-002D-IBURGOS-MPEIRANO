package com.example.appmodoguardian_grupo1.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.appmodoguardian_grupo1.viewmodel.UsuarioViewModel

private val DarkBg = Color(0xFF0B1220)
private val CardBg = Color(0xFF131D31)
private val CardInnerBg = Color(0xFF19253D)
private val PrimaryBlue = Color(0xFF2563EB)
private val LightBlue = Color(0xFF60A5FA)
private val AccentBorder = Color(0xFF2B3C5A)
private val TextMuted = Color(0xFF94A3B8)
private val StatusGreen = Color(0xFF10B981)
private val StatusRed = Color(0xFFEF4444)

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val context = LocalContext.current
    val estado by viewModel.estado.collectAsState()
    var fotoBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Estados visuales para los 3 switches esenciales
    var verificacionDosPasos by remember { mutableStateOf(true) }
    var notificacionesPush by remember { mutableStateOf(true) }
    var alertasCriticas by remember { mutableStateOf(true) }

    val nombreMostrar = estado.nombre.ifBlank { "Guardián" }
    val correoMostrar = estado.correo.ifBlank { "guardian@modoguardian.cl" }
    val iniciales = nombreMostrar
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
        .ifBlank { "MG" }

    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            fotoBitmap = bitmap
        }
    }

    val launcherPermisoCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            launcherCamara.launch(null)
        } else {
            Toast.makeText(
                context,
                "Debes permitir el acceso a la cámara para tomar una foto",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun abrirCamaraPerfil() {
        val permisoConcedido = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (permisoConcedido) {
            launcherCamara.launch(null)
        } else {
            launcherPermisoCamara.launch(Manifest.permission.CAMERA)
        }
    }

    Scaffold(
        containerColor = DarkBg,
        bottomBar = {
            GuardianBottomBar(
                navController = navController,
                rutaActual = "perfil"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBg)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // 1. Encabezado superior
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Mi Perfil",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Cuenta y preferencias del turno",
                        color = TextMuted,
                        fontSize = 13.sp
                    )
                }

                // Botón rápido para tomar/actualizar foto de perfil
                Surface(
                    onClick = { abrirCamaraPerfil() },
                    shape = CircleShape,
                    color = CardBg,
                    border = BorderStroke(1.dp, AccentBorder),
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Tomar foto de perfil",
                            tint = LightBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // 2. Tarjeta Principal de Usuario
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, AccentBorder)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Avatar circular (tocar abre la cámara)
                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier.clickable { abrirCamaraPerfil() }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryBlue.copy(alpha = 0.25f))
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
                                    Text(
                                        text = iniciales,
                                        color = Color.White,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Mini icono de cámara sobre el avatar
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryBlue)
                                    .border(2.dp, CardBg, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "$nombreMostrar — Guardián",
                                color = Color.White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = correoMostrar,
                                color = TextMuted,
                                fontSize = 13.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Badge Correo verificado
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(StatusGreen.copy(alpha = 0.14f))
                                    .border(1.dp, StatusGreen.copy(alpha = 0.35f), RoundedCornerShape(50))
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = StatusGreen,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Correo verificado",
                                    color = StatusGreen,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    // Pills inferiores: Zona Sur y Turno Activo
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(PrimaryBlue)
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Guardián • Zona Sur",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(StatusGreen.copy(alpha = 0.14f))
                                .border(1.dp, StatusGreen.copy(alpha = 0.35f), RoundedCornerShape(50))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(StatusGreen)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Turno Activo",
                                color = StatusGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // 3. Tarjeta de Métricas del Turno (3 columnas)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, AccentBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ColumnaMetricaPerfil(
                        modifier = Modifier.weight(1f),
                        icono = Icons.Outlined.Schedule,
                        valor = "14:00 – 22:00",
                        etiqueta = "Turno"
                    )
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(46.dp)
                            .background(AccentBorder)
                    )
                    ColumnaMetricaPerfil(
                        modifier = Modifier.weight(1f),
                        icono = Icons.Outlined.Videocam,
                        valor = "12 asignadas",
                        etiqueta = "Cámaras"
                    )
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(46.dp)
                            .background(AccentBorder)
                    )
                    ColumnaMetricaPerfil(
                        modifier = Modifier.weight(1f),
                        icono = Icons.Outlined.WarningAmber,
                        valor = "3 resueltas",
                        etiqueta = "Alertas hoy"
                    )
                }
            }

            // 4. Sección MI CUENTA (Sin dirección)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "MI CUENTA",
                    color = TextMuted,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardBg),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, AccentBorder)
                ) {
                    Column {
                        FilaOpcionCuenta(
                            icono = Icons.Outlined.Videocam,
                            titulo = "Mis cámaras asignadas",
                            subtitulo = null,
                            badgeTexto = "12",
                            onClick = { navController.navegarPestanaGuardian("camaras") }
                        )
                        HorizontalDivider(color = AccentBorder.copy(alpha = 0.6f))
                        FilaOpcionCuenta(
                            icono = Icons.Outlined.VerifiedUser,
                            titulo = "Certificado de guardia / Credencial",
                            subtitulo = "Documento vigente",
                            badgeTexto = null,
                            onClick = { /* Solo muestra visual */ }
                        )
                    }
                }
            }

            // 5. Sección SEGURIDAD (Solo los 3 switches esenciales)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "SEGURIDAD Y AVISOS",
                    color = TextMuted,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardBg),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, AccentBorder)
                ) {
                    Column {
                        FilaSwitchSeguridad(
                            icono = Icons.Outlined.Shield,
                            titulo = "Verificación en 2 pasos",
                            subtitulo = "Protección adicional activada",
                            checked = verificacionDosPasos,
                            onCheckedChange = { verificacionDosPasos = it }
                        )
                        HorizontalDivider(color = AccentBorder.copy(alpha = 0.6f))
                        FilaSwitchSeguridad(
                            icono = Icons.Outlined.Notifications,
                            titulo = "Notificaciones Push",
                            subtitulo = "Avisos activados",
                            checked = notificacionesPush,
                            onCheckedChange = { notificacionesPush = it }
                        )
                        HorizontalDivider(color = AccentBorder.copy(alpha = 0.6f))
                        FilaSwitchSeguridad(
                            icono = Icons.Outlined.NotificationsActive,
                            titulo = "Alertas críticas",
                            subtitulo = "Siempre visibles y sonoras",
                            checked = alertasCriticas,
                            onCheckedChange = { alertasCriticas = it }
                        )
                    }
                }
            }

            // 6. Botón Cerrar Sesión funcional
            OutlinedButton(
                onClick = {
                    viewModel.cerrarSesion()
                    navController.navigate("log") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, StatusRed.copy(alpha = 0.45f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = StatusRed.copy(alpha = 0.12f),
                    contentColor = StatusRed
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.PowerSettingsNew,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cerrar Sesión",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Pie de versión
            Text(
                text = "Modo Guardián v1.2.0 • Protegido",
                color = TextMuted,
                fontSize = 11.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ColumnaMetricaPerfil(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    valor: String,
    etiqueta: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = LightBlue,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = valor,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = etiqueta,
            color = TextMuted,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun FilaOpcionCuenta(
    icono: ImageVector,
    titulo: String,
    subtitulo: String?,
    badgeTexto: String?,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CardInnerBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = titulo,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            if (subtitulo != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitulo,
                    color = TextMuted,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (badgeTexto != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(PrimaryBlue.copy(alpha = 0.22f))
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Text(
                    text = badgeTexto,
                    color = LightBlue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun FilaSwitchSeguridad(
    icono: ImageVector,
    titulo: String,
    subtitulo: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CardInnerBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = titulo,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitulo,
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryBlue,
                uncheckedThumbColor = TextMuted,
                uncheckedTrackColor = CardInnerBg
            )
        )
    }
}